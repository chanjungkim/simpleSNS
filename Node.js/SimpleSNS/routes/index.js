var express = require('express');
var router = express.Router();
const dbHelper = require('../database/dbHelper');

router.post('/', function(req, res, next) {
  res.json({result:"success"});
});

module.exports = router;
