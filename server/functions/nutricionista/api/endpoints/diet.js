const Diet                    = require('../../model/diet');
const { success, withErrors } = require('../helpers/response');

module.exports = (app) => {
  app.get('/diet', withErrors(async (req, res) => {
    const diets = await Diet.index(req.currentUser.uid);

    success(res, diets);
  }));

  app.get('/diet/:id', withErrors(async (req, res) => {
    const diet = await Diet.get(req.currentUser.uid, req.params.id);

    success(res, diet);
  }));

  app.post('/diet', withErrors(async (req, res) => {
    const data = req.body;
    data.uid = req.currentUser.uid;

    await Diet.create(data);

    return success(res);
  }));

  app.put('/diet/:id', withErrors(async (req, res) => {
    const diet = await Diet.get(req.currentUser.uid, req.params.id)

    await diet.update(req.body)

    return success(res);
  }));
};
