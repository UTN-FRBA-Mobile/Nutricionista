class NotFoundError extends Error {
  constructor(resource) {
    super(`${resource} not found`);
  }

  get status() {
    return 404;
  }
}

module.exports = NotFoundError;
