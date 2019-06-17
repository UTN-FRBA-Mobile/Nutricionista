const Resource = require('./resource')
const { db: db } = require('../firebase')
const _ = require('lodash');

class User extends Resource {
  static async create(data) {
    const ref = await db.collection('usuarios').doc(data.uid).set(data);
    return new User({ ref: ref, data: () => data });
  }
}

module.exports = User;
