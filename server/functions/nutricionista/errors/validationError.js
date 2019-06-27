class ValidationError extends Error {
  get status() {
    return 409;
  }
}

module.exports = ValidationError;
