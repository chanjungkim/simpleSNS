var express = require('express');
var db = require('../db');
var formidable = require('formidable');
var router = express.Router();
var fs = require('fs');

//profile
router.put('/:username', function(req, res, next){
	console.log("/profile/:username");
	var username = req.body.username;
	console.log("username : "+username);
	var newUsername = req.body.newUsername;
	console.log("newUsername : "+newUsername);
	var introduction = req.body.introduction;
	console.log("introduction : " +  introduction);
	var photo_url = req.body.photo_url;
	console.log("photo_url : " +  photo_url);

	var sql = "UPDATE member SET introduction=?, username=? WHERE username=?";
	var sql2 = "UPDATE member SET photo_url=?  WHERE username=?";

    var input = [introduction, newUsername, username];
	var input2 = ['/img/profile/' + photo_url, username];

	console.log(photo_url);

	if (!photo_url){
		console.log(photo_url + '공란');
		try{
			db.get().query(sql, input, function(err,result){
				console.log("result : "+result);
				if(err) {
					console.log("err: "+JSON.stringify(err, null, 2));
					res.json({code:401, message:"DB error", result:"Failure"});
				}
				console.log(result);
				res.json({code:200, message:"Success", result:"OK"});
			});
		}catch(e){
			console.log(e);
		}
	}else{
		console.log(photo_url + '공란아님');
		try{
			db.get().query(sql2, input2, function(err,result){
				console.log("result : "+result);
				if(err) {
					console.log("err: "+JSON.stringify(err, null, 2));
					res.json({code:401, message:"DB error", result:"Failure"});
				}
				console.log(result);
				res.json({code:200, message:"Success", result:"OK"});
			});
		}catch(e){
			console.log(e);
		}
	}

});

//profile/:username
router.get('/:username', function(req, res, next){
	console.log("/profile/:username");
	var username = req.params.username;

	console.log("username: "+username);

	var sql = "SELECT * FROM member WHERE username=?";

	var input = [username];
	try{
		db.get().query(sql, input, function(err,result){
			if(err) {
				console.log("err: "+JSON.stringify(err, null, 2));
				res.json({code:401, message:"DB error", result:"Failure"});
			}
			if(result.length>0){
				res.json({data: result[0], code:200, message:"Success", result:"OK"});
			}
		});
	}catch(e){
		console.log(e);
	}
});

//profile/:newUsername
router.get('/newuser/:newUsername', function(req, res, next){
	console.log("/profile/newuser/:newUsername");
	var newUsername = req.params.newUsername;
	console.log("newUsername: "+newUsername);

	var sql = "SELECT * FROM member WHERE username=?";

	var input = [newUsername];
	try{
		db.get().query(sql, input, function(err,result){
			if(err) {
				console.log("err: "+JSON.stringify(err, null, 2));
				res.json({code:401, message:"DB error", result:"Failure"});
			}
			if(result.length>0){
				res.json({code:200, message:"Success", result:true});
			} else {
				res.json({code:200, message:"Success", result:false});
			}
		});
	} catch(e){
		console.log(e);
	}
});

router.post('/photo', function (req, res, next) {

	console.log("/photo");
	var form = new formidable.IncomingForm();
	form.multiples = true;

	form.parse(req);

	form.on('fileBegin', function (name, file){
		console.log("fileBegin");
		file.path = './public/img/profile/' + file.name;
		console.log("fileBegin: file.path: " + file.path);
	});

	form.on('file', function(name, file){
		console.log("file");
		console.log('file: Uploaded ' + file.name);
	});
	res.status(200).send("1");

});

module.exports = router;

