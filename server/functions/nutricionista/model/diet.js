const Resource                       = require('./resource');
const { ResourceAlreadyExistsError } = require('../errors');

class Diet extends Resource {
  static async validate(data) {
    const result = await this.collectionRef.where('fecha', '==', data.fecha)
                                           .where('uid', '==', data.uid)
                                           .get();

    if(!result.empty) throw new ResourceAlreadyExistsError();
  }

  static get collection() {
    return 'dietas';
  }
}

module.exports = Diet;
