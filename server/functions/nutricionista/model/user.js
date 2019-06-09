const { db: db } = require('../firebase')
const _ = require('lodash');

function User(doc) {
  _.assign(this, doc.data());
  this.dbRef = doc.ref; 
}

User.prototype = {
  update: function (updates) {
    _.assign(this, updates);
    return this.dbRef.update(updates);
  }
};

User.create = async function (data) {
  const ref = await db.collection('usuarios').add(data);
  return new User({ref: ref, data: () => data});
}

module.exports = User;
