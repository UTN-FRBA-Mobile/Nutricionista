const Resource = require('./resource');
const { db }   = require('../firebase');

class UserOwned extends Resource {
  static async create(data) {
    await this.validate(data);

    const ref = await this.collectionRef(data.uid).add(data);
    return new this({ ref: ref, data: () => data });
  }

  static async get(uid) {
    const results = await this.collectionRef(uid).get();

    return results.docs.map((data) => new this(data));
  }

  static collectionRef(uid) {
    return db.collection(`usuarios/${uid}/${this.path}`);
  }

  static async validate(_data) {}

  static get path() {
    throw new Error('Unimplemented method: path');
  }
}

module.exports = UserOwned;
