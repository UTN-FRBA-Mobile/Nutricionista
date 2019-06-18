const { User }      = require('../model');
const { admin, db } = require('../firebase');

const bodyParser = require('body-parser').json();

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
    const snap = await db.collection('usuarios').where('uid', '==', req.decodedIdToken.uid).get();
    const userData = snap.docs[0];

    if(!userData) return res.status(404).send('Usuario no encontrado');

    req.currentUser = new User(userData);
  } catch(e) {
    return res.status(500).send(`Something went wrong: ${e}` );
  }
  return next();
};

module.exports = [bodyParser, authenticate, setCurrentUser];
