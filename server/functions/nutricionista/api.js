const ValidationError = require('./errors/validationError')
const app = require('./api/app');
const _ = require('lodash');
const { db: db } = require('./firebase')
const { Diet: Diet } = require('./model')

function success(res, body = 'success'){
  res.status(200).json(body)
}

app.get('/user', async (req, res) => {
  success(res, _.omit(req.currentUser, 'uid'));
});

app.put('/user', async (req, res) => {
  await req.currentUser.update(req.body);
  success(res);
});

app.get('/diet', async (req, res) => {
  const diets = await Diet.get(req.currentUser.uid);

  success(res, diets);
});

app.post('/diet', async (req, res) => {
  const data = req.body;
  data.uid = req.currentUser.uid;

  try {
    await Diet.create(data);
  } catch (e) {
    console.log(e);

    if (e instanceof ValidationError) return res.status(409).send(e.message);
    throw e;
  }

  return success(res);
});

app.get('/messages', async (req, res) => {
  const messages = await db.collection(`usuarios/${req.currentUser.uid}/mensajes`).get();
  success(res, messages);
});

module.exports = app;
