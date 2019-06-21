function success(res, body = 'success') {
  res.status(200).json(body)
}

function withErrors(handler) {
  return (req, res, next) => {
    return handler(req, res, next).catch(next);
  };
}

module.exports = {
  success,
  withErrors
};
