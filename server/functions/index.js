const { functions } = require('./nutricionista/firebase');
const _             = require('lodash');
const { User }      = require('./nutricionista/model');
const api           = require('./nutricionista/api');

module.exports.onUserCreate = functions.auth.user().onCreate((user) => {
  return User.create(_.pick(user, 'uid', 'email', 'displayName'));
});

// Expose the API as a function
module.exports.api = functions.https.onRequest(api);
