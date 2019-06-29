const { db }                                = require('../firebase');
const { NotFoundError, UnimplementedError } = require('../errors');
const _                                     = require('lodash');

class Resource {
  constructor(doc) {
    _.assign(this, doc.data());
    this.id = doc.ref.id;
  }

  static async index(uid, query) {
    const filters = Object.entries(query);
    filters.push(['uid', uid]);

    const dbQuery = filters.reduce((accQuery, [key, value]) => 
      accQuery.where(key, '==', value), this.collectionRef);
    const results = await dbQuery.get();

    return results.docs.map((data) => new this(data));
  }

  static async get(id) {
    const doc = await this.collectionRef.doc(id).get();

    if(!doc.exists) throw new NotFoundError(this.name);

    return new this(doc);
  }

  static async create(data) {
    await this.validate(data);

    const ref = await this.collectionRef.add(data);
    return new this({ ref: ref, data: () => data });
  }

  update(updates) {
    _.assign(this, updates);
    return this.dbRef.update(updates);
  }

  delete(data) {
    return this.dbRef.delete();
  }

  static async validate(_data) {} // for overriding purposes

  get dbRef() {
    return this.constructor.collectionRef.doc(this.id);
  }

  static get collectionRef() {
    return db.collection(this.collection);
  }

  static get collection() {
    throw new UnimplementedError('collection');
  }
}

module.exports = Resource;
