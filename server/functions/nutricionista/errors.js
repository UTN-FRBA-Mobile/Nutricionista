const NotFoundError              = require('./errors/notFoundError');
const ResourceAlreadyExistsError = require('./errors/resourceAlreadyExistsError');
const UnauthorizedError          = require('./errors/unauthorizedError');
const UnimplementedError         = require('./errors/unimplementedError');
const ValidationError            = require('./errors/validationError');

module.exports = {
  NotFoundError,
  ResourceAlreadyExistsError,
  UnauthorizedError,
  UnimplementedError,
  ValidationError
}
