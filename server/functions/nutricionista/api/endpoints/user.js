const { success, withErrors } = require('../helpers/response');

module.exports = (app) => {
  app.get('/user', withErrors(async (req, res) => {
    success(res, req.currentUser);
  }));

  app.put('/user', withErrors(async (req, res) => {
    await req.currentUser.update(req.body);
    success(res);
  }));
};
