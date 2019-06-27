const dietEndpoints     = require('./endpoints/diet');
const messagesEndpoints = require('./endpoints/message');
const userEndpoints     = require('./endpoints/user');
const weightEndpoints   = require('./endpoints/weight');

module.exports = (app) => {
  dietEndpoints(app);
  messagesEndpoints(app);
  userEndpoints(app);
  weightEndpoints(app);
}
