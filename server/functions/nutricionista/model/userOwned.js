const { NotFoundError, UnimplementedError } = require('../errors');
const Resource                              = require('./resource');
const { db }                                = require('../firebase');

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

  static async get(uid, id) {                                 // TODO: I hate how I have to pass current user id too to be able to access resource. 
    const doc = await this.collectionRef(uid).doc(id).get();  // Fixing this will require moving nested collections in database to root.

    if(!doc.exists) throw new NotFoundError(this);

    return new this(doc);
  }

  static collectionRef(uid) {
    return db.collection(`usuarios/${uid}/${this.path}`);
  }

  static async validate(_data) {}

  static get path() {
    throw new UnimplementedError('Unimplemented method: path');
  }
}

module.exports = UserOwned;
