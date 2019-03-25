var express = require ('express');
var db = require('../db');
var router = express.Router();

// feed 
router.post('/',function (req, res, next) {
	res.json({message:"success", code:100, result:"url"});
});
