const { NotFoundError, UnimplementedError } = require('../errors');
const _                                     = require('lodash');

class Resource {
  constructor(doc) {
    _.assign(this, doc.data());
    this.dbRef = doc.ref;
    this.id = doc.ref.id;
  }

  static async get(id) {
    const doc = await this.collectionRef.doc(id).get();

    if(!doc.exists) throw new NotFoundError(this.name);

    return new this(doc);
  }

  static get collectionRef() {
    throw new UnimplementedError('collectionRef');
  }

  update(updates) {
    _.assign(this, updates);
    return this.dbRef.update(updates);
  }
}

module.exports = Resource;
