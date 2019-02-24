# introduction

`app.js` - The main file for its server.

`bin/www` or `bin/simpleSNS` - A file for execution.

`db.js` - Database setting file.

`packing.json` - The description of the app. It also contains the information of modules. You can install the described modules with a command `npm install`.

`routes` - For `routers`.

`view` - For a web app.

`public` - For static web page.

`routes/member.js`

```javascript
var express = require('express');
var db = require('../db');
var router = express.Router();

router.post('/login', function(req, res, next){
	var a = req.body.a;
	var b = req.body.b;

	var sql = "SELECT ...";
	var input = [a, b];

	db.get().query(sql, input, function(err, result) {
		// TODO:
	});
});

module.exports = router;
```

`RemoteService.java` interface.

```java
    @FormUrlEncoded
    @POST("/member/login")
    Call<LoginResult> loginMember(@Field("a") String x, @Field("b") String y);
```

# Method Types

`@GET` -> @Path, @Query -> req.params, req.query

`@POST` -> @Body, @Field(@FormUrlEncoded사용) -> req.body

`@DELETE`

`@UPDATE`

# db.get().query(...)

```
db.get().query(sql, input, function(err, result) {
	if(err){
		console.log("err: " + JSON.stringify(err,null,2));
		res.json({message:"SQL error", code:403, request:false});
	}
	console.log("result: " + JSON.stringify(result,null,2));

	if(result.length > 0){
		res.json({message:"Success!", code:100, request:true});
	]else{
		res.json({message:"No User!", code:403, request:false});
	}
});
```
