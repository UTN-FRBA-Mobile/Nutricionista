const _                       = require('lodash');
const { success, withErrors } = require('../helpers/response');

module.exports = (app) => {
  app.get('/user', withErrors(async (req, res) => {
    success(res, _.omit(req.currentUser, 'uid'));
  }));

  app.put('/user', withErrors(async (req, res) => {
    await req.currentUser.update(req.body);
    success(res);
  }));
};
