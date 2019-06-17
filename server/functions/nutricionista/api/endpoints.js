const dietEndpoints     = require('./endpoints/diet');
const messagesEndpoints = require('./endpoints/messages');
const userEndpoints     = require('./endpoints/user');

module.exports = (app) => {
  dietEndpoints(app);
  messagesEndpoints(app);
  userEndpoints(app);
}
