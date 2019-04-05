var express = require ('express');
var db = require('../db');
var router = express.Router();

// feed 
router.get('/', function(req, res, next){
	console.log("/feed");
	var username = req.query.username;
	var lastFeedNum = req.query.lastFeedNum;

	// Pating - No followings... 
	var sql = "SELECT f.fid, u.username, u.photo_url, f.reg_time, f.description, f.mod_time, m.url, m.width, m.height"
		+" FROM feed f, member u, media m"
		+" WHERE f.uid = u.uid AND f.fid = m.fid LIMIT 5;"

	var input = [];

	db.get().query(sql, input, function(err, result){
		if(err){
			console.log("err: "+JSON.stringify(err, null, 2));
			res.json({data:err, code:403, message:"DB ERROR", result:true});
		}
		console.log("result: "+JSON.stringify(result, null, 2));
		res.json({data:result, code:200, message:"Success", result:true});
	});
});

module.exports = router;
