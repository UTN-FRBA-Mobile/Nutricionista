const NotFoundError = require('../errors/notFoundError')
const Resource      = require('./resource');
const { db }        = require('../firebase');

class UserOwned extends Resource {
  static async create(data) {
    await this.validate(data);

    const ref = await this.collectionRef(data.uid).add(data);
    return new this({ ref: ref, data: () => data });
  }

  static async index(uid) {
    const results = await this.collectionRef(uid).get();

    return results.docs.map((data) => new this(data));
  }

  static async get(uid, id) {
    const doc = await this.collectionRef(uid).doc(id).get();

    if(!doc.exists) throw new NotFoundError(this);

    return new this(doc);
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
