const _ = require('lodash');

class Resource {
  constructor(doc) {
    _.assign(this, doc.data());
    this.dbRef = doc.ref;
    this.id = doc.ref.id;
  }

  update(updates) {
    _.assign(this, updates);
    return this.dbRef.update(updates);
  }
}

module.exports = Resource;
