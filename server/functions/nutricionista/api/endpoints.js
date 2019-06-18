const dietEndpoints     = require('./endpoints/diet');
const messagesEndpoints = require('./endpoints/message');
const userEndpoints     = require('./endpoints/user');

module.exports = (app) => {
  dietEndpoints(app);
  messagesEndpoints(app);
  userEndpoints(app);
}
