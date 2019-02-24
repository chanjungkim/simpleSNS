var express = require('express');
var db = require('../db');
var router = express.Router();

//member/:
router.post('/', function(req, res, next) {
	console.log('/member');
	console.log(JSON.stringify(req.body, null, 2));
	var email = req.body.email;
	var username = req.body.username;
	var password = req.body.password;

	console.log("email: "+email+", username: "+username+", password: "+password);

	var sql_select = "SELECT * FROM member WHERE username=?";
	var input = [username];
	
	db.get().query(sql_select, input, function(err, result){
		if(err){
			console.log("err: "+JSON.stringify(err,null,2));
			res.json({message:"DB error", code:403, result:false});
		}
		if(result.length > 0){
			console.log("Username exists already");
			res.json({message:"Someone is using that username. Try other usernames.", code:401, result:false});
		}else{
			console.log("new username is signing up...");
			var sql_insert = "INSERT INTO member(email, username, password) VALUES(?,?,?)";
			var input = [email, username, password];

			db.get().query(sql_insert, input, function(err, result){
  		  if(err){
 				  console.log("err: "+JSON.stringify(err, null, 2));
   				res.json({message:"error",code:401,result:false});
 				}else {
   				console.log("result: "+JSON.stringify(result, null, 2,));
   				res.json({message:"Signed up successflly.", code:100, result:true});
 				}
  		});
		}
	});
});

router.post('/login', function(req, res, next){
	console.log("/login");
	var username = req.body.username;
	var password = req.body.password;

	console.log("username: "+username+", password: "+password);
	var sql = "SELECT * FROM member WHERE username = ? AND password = ? LIMIT 1";
	var input = [username, password];

	console.log("sql : " + sql);
	db.get().query(sql, input, function(err, result) {
		if(err){
			console.log("err: "+JSON.stringify(err,null,2));
			res.json({message:"DB error", code:403, result:false});
		}
		console.log("result : "  + JSON.stringify(result, null, 2));
		if ( result.length > 0) {
			console.log(username+"/"+password+" => Login Successful");
			// how can I get JWT here???
			res.json({message:"Login Succesful", code:100, result:true, jwt:"TEST_JWT"});
		} else {
			console.log(username+"/"+password+" <= User doesn't exists.");
			res.json({message:"User doesn't exists.",code:400,result:false});;
		}
	});
});

module.exports = router;
