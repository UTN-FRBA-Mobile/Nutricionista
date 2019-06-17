function success(res, body = 'success') {
  res.status(200).json(body)
}

module.exports = {
  success
};
