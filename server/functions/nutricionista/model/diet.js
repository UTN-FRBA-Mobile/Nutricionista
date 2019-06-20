const UserOwned                      = require('./userOwned');
const { ResourceAlreadyExistsError } = require('../errors');

class Diet extends UserOwned {
  static async validate(data) {
    const result = await this.collectionRef(data.uid).where('fecha', '==', data.fecha).get();

    if(!result.empty) throw new ResourceAlreadyExistsError();
  }

  static get path() {
    return 'dieta';
  }
}

module.exports = Diet;
