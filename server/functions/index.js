const { functions: functions } = require('./nutricionista/firebase');
const _                        = require('lodash');
const { User: User }           = require('./nutricionista/model');
const api                      = require('./nutricionista/api');

exports.onUserCreate = functions.auth.user().onCreate((user) => {
  return User.create(_.pick(user, 'uid', 'email', 'displayName'));
});

// Expose the API as a function
exports.api = functions.https.onRequest(api);
