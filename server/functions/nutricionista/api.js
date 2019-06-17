const app       = require('./api/app');
const endpoints = require('./api/endpoints');

endpoints(app);

module.exports = app;
