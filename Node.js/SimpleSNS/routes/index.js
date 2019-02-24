var express = require('express');
var router = express.Router();
const db = require('../db');

router.post('/', function(req, res, next) {
  res.json({result:"success"});
});

module.exports = router;
