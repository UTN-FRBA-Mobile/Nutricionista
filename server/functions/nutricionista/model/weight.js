const Resource = require('./resource');

class Weight extends Resource {
   static async validate(data) {
    const result = await this.collectionRef.where('fecha', '==', data.fecha)
                                           .where('uid', '==', data.uid)
                                           .get();

    if(!result.empty) throw new ResourceAlreadyExistsError();
  }
  static get collection() {
    return 'pesos';
  }
}

module.exports = Weight;
