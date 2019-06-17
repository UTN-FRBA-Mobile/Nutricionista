const ResourceAlreadyExistsError = require('../errors/resourceAlreadyExistsError');
const Resource = require('./resource');
const { db: db } = require('../firebase');
const _ = require('lodash');

class Diet extends Resource {
  static async create(data) {
    await this.validate(data);
    const ref = await db.collection(`usuarios/${data.uid}/dieta`).add(data);
    return new Diet({ ref: ref, data: () => data });
  }

  static async validate(data) {
    const result = await db.collection(`usuarios/${data.uid}/dieta`).where('fecha', '==', data.fecha).get();

    if(!result.empty) throw new ResourceAlreadyExistsError();
  }

  static async get(uid) {
    const dieta = [];
    const results = await db.collection(`usuarios/${uid}/dieta`).get()

    results.forEach(data => {
      dieta.push(new Diet(data));
    });

    return dieta;
  }
}

module.exports = Diet;
