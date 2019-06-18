const Diet            = require('../../model/diet');
const ValidationError = require('../../errors/validationError');
const { success }     = require('../helpers/response');

module.exports = (app) => {
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
      if (e instanceof ValidationError) return res.status(409).send(e.message);
      throw e;
    }

    return success(res);
  });
};
