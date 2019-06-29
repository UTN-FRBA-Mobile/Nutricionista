const { User }              = require('../model');
const { admin }             = require('../firebase');
const { withErrors }        = require('./helpers/response');
const { UnauthorizedError } = require('../errors');

const bodyParser = require('body-parser').json();

const authenticate = withErrors(async (req, _res, next) => {
  if (!req.headers.authorization || !req.headers.authorization.startsWith('Bearer ')) {
    throw new UnauthorizedError();
  }
  const idToken = req.headers.authorization.split('Bearer ')[1];
  try {
    req.decodedIdToken = await admin.auth().verifyIdToken(idToken);
  } catch(e) {
    throw new UnauthorizedError();
  }
  return next();
});

const setCurrentUser = withErrors(async (req, _res, next) => {
  req.currentUser = await User.get(req.decodedIdToken.uid);
  
  return next();
});

const errorHandling = (err, _req, res, _next) => {
  if(err.status) return res.status(err.status).send(err.message);
  return res.status(500).send(`Something went wrong: ${err}` );
};

module.exports = {
  before: [bodyParser, authenticate, setCurrentUser],
  after:  [errorHandling]
};
