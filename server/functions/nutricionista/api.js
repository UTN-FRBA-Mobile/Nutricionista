const app = require('./api/app');
const _ = require('lodash');

app.get('/user', async (req, res) => {
  res.status(200).json(_.omit(req.currentUser, 'uid'));
});

app.put('/user', async (req, res) => {
  await req.currentUser.update(req.body);
  res.status(200);
});

module.exports = app;
