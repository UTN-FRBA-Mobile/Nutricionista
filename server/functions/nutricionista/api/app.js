const app = require('express')();

const middleware = require('./middleware');

app.use(middleware);

module.exports = app;
