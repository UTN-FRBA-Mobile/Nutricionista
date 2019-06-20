const Message                 = require('../../model/message');
const { success, withErrors } = require('../helpers/response');

module.exports = (app) => {
  app.get('/messages', withErrors(async (req, res) => {
    const messages = await Message.index(req.currentUser.uid);

    success(res, messages);
  }));

  app.post('/messages', withErrors(async (req, res) => {
    const data = req.body;
    data.uid = req.currentUser.uid;

    await Message.create(data);

    success(res);
  }));
};
