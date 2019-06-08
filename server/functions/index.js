const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();
const express = require('express');
const _ = require('lodash');
const app = express();
const User = require('./model/user');
const bodyParser = require('body-parser');

const db = admin.firestore();
// Express middleware that validates Firebase ID Tokens passed in the Authorization HTTP header.
// The Firebase ID token needs to be passed as a Bearer token in the Authorization HTTP header like this:
// `Authorization: Bearer <Firebase ID Token>`.
// when decoded successfully, the ID Token content will be added as `req.user`.
const authenticate = async (req, res, next) => {
  if (!req.headers.authorization || !req.headers.authorization.startsWith('Bearer ')) {
    return res.status(403).send('Unauthorized');
  }
  const idToken = req.headers.authorization.split('Bearer ')[1];
  try {
    req.decodedIdToken = await admin.auth().verifyIdToken(idToken);
  } catch(e) {
    return res.status(403).send('Unauthorized');
  }
  return next();
};

const setCurrentUser = async (req, res, next) => {
  try {
    snap = await db.collection('usuarios').where('uid', '==', req.decodedIdToken.uid).get();
    const userData = snap.docs[0];

    if(!userData) return res.status(404).send('Usuario no encontrado');

    req.currentUser = new User(userData);
  } catch(e) {
    return res.status(500).send(`Something went wrong: ${e}` );
  }
  return next();
};

app.use(authenticate);
app.use(setCurrentUser);
app.use(bodyParser.json());


app.get('/user', async (req, res) => {
  res.status(200).json(_.omit(req.currentUser, 'uid'));
});

app.put('/user', async (req, res) => {
  await req.currentUser.update(req.body);
  res.status(200);
});

exports.onUserCreate = functions.auth.user().onCreate((user) => {
  User.create(_.pick(user, 'uid', 'email', 'displayName', 'metadata'));
});

// Expose the API as a function
exports.api = functions.https.onRequest(app);
