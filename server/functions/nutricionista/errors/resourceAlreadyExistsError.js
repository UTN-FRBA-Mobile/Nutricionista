const ValidationError = require('./validationError');

class ResourceAlreadyExistsError extends ValidationError {
  constructor() {
    super("Resource already exists");
  }
}

module.exports = ResourceAlreadyExistsError;
