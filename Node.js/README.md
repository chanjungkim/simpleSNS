# introduction

`app.js` - The main file for its server.
`bin/www` or `bin/simpleSNS` - A file for execution.
`db.js` - Database setting file.
`packing.json` - The description of the app. It also contains the information of modules. You can install the described modules with a command `npm install`.

`routes` - For `routers`.
`view` - For a web app.

`public` - For static web page.

```javascript
var express = require('express');
var db = require('../db');
var router = express.Router();

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
			res.json({message:"Login Succesful", code:100, result:true, jwt:"TEST_JWT"});
		} else {
			console.log(username+"/"+password+" <= User doesn't exists.");
			res.json({message:"User doesn't exists.",code:400,result:false});;
		}
	});
});

module.exports = router;
```
