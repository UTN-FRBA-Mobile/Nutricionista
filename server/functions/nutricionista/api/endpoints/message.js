const Message     = require('../../model/message');
const { success } = require('../helpers/response');

module.exports = (app) => {
  app.get('/messages', async (req, res) => {
    const messages = await Message.get(req.currentUser.uid);

    success(res, messages);
  });

  app.post('/messages', async (req, res) => {
    const data = req.body;
    data.uid = req.currentUser.uid;

    await Message.create(data);

    return success(res);
  });
};
