const Diet            = require('../../model/diet');
const ValidationError = require('../../errors/validationError');
const { success }     = require('../helpers/response');
const { db }               = require('../../firebase')

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

  app.put('/diet', async (req, res) => {
    const data = req.body;
    const snap = await db.collection(`usuarios/${req.currentUser.uid}/dieta`).where('fecha', '==', data.fecha).get();
    
    if(snap.empty) return res.status(404).send('Not Found');

    snap.docs[0].ref.update(data);
    return success(res);
  });
};
