class UnimplementedError extends Error {
  constructor(method) {
    super(`Unimplemented method: ${method}`);
  }
}

module.exports = UnimplementedError;
