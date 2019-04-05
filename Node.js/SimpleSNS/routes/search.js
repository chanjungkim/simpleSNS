var express = require ('express');
var db = require('../db');
var router = express.Router();

// feed 
router.get('/', function(req, res, next){
	console.log("/feed");
	var username = req.query.username;
	var lastFeedNum = req.query.lastFeedNum;

	// Pating - No followings... 
	var sql = "SELECT f.fid, u.username, m.url, m.width, m.height"
		+" FROM feed f, media m, member u"
		+" WHERE f.fid = m.fid AND f.uid = u.uid LIMIT 12;"

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

router.get('/feed', function(req,res,next){
	console.log("/feed");
	var fid = req.query.fid;
	var username = req.query.username;

	console.log("fid: "+fid);
	var sql = "SELECT f.fid, u.username, u.photo_url, f.reg_time, f.description, m.url, m.width, m.height"
		+" FROM feed f, media m, member u"
		+" WHERE f.fid >= ? AND u.username = ? AND f.fid = m.fid ORDER BY f.fid ASC"
		+" LIMIT 5";
	var input = [fid, username];

	db.get().query(sql, input, function(err, result){
		if(err){
			console.log("err: "+JSON.stringify(err, null, 2));
			res.json({data:err, result:false, message:"DB ERROR", code:403});
		}

		console.log("result: "+JSON.stringify(result, null, 2));

		res.json({data:result, result:true, message:"Success", code:200});
	});
});

module.exports = router;
