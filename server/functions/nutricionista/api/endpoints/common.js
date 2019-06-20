const { success, withErrors } = require('../helpers/response');

module.exports = (model) => (app) => {
  function endpoint(path = '') {
    return `/${model.name.toLowerCase()}${path}`;
  }

  app.get(endpoint(), withErrors(async (req, res) => {
    const results = await model.index(req.currentUser.uid);

    return success(res, results);
  }));

  app.get(endpoint('/:id'), withErrors(async (req, res) => {
    const result = await model.get(req.params.id);

    return success(res, result);
  }));

  app.post(endpoint(), withErrors(async (req, res) => {
    const data = req.body;
    data.uid = req.currentUser.uid;

    await model.create(data);

    return success(res);
  }));

  app.put(endpoint('/:id'), withErrors(async (req, res) => {
    const model = await model.get(req.params.id)

    await model.update(req.body)

    return success(res);
  }));
};
