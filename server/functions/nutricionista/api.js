const app               = require('express')();
const endpoints         = require('./api/endpoints');
const { before, after } = require('./api/middleware');

app.use(before);
endpoints(app);
app.use(after);

module.exports = app;
