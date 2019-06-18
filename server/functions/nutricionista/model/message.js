const UserOwned = require('./userOwned');

class Message extends UserOwned {
  static get path() {
    return 'mensajes';
  }
}

module.exports = Message;
