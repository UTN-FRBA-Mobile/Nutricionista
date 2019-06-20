class UnauthorizedError extends Error {
  constructor() {
    super('Unauthorized');
  }

  get status() {
    return 403;
  }
}

module.exports = UnauthorizedError;
