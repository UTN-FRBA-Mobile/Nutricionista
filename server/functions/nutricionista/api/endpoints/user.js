const _                    = require('lodash');
const { success: success } = require('../helpers/response');

module.exports = (app) => {
  app.get('/user', async (req, res) => {
    success(res, _.omit(req.currentUser, 'uid'));
  });

  app.put('/user', async (req, res) => {
    await req.currentUser.update(req.body);
    success(res);
  });
};
