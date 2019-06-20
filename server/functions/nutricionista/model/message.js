const Resource = require('./resource');

class Message extends Resource {
  static get collection() {
    return 'mensajes';
  }
}

module.exports = Message;
