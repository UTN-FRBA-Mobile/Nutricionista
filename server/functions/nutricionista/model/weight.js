const Resource = require('./resource');

class Weight extends Resource {
  static get collection() {
    return 'pesos';
  }
}

module.exports = Weight;
