# introduction

`app.js` - The main file for its server. It's the highest file among all the file. It contains `modules`, `db.js`, `routers`.

`bin/www` or `bin/simpleSNS` - A file for execution. You can run the server by `pm2 start bin/simpleSNS` and then you can see the list of running applications with `pm2 list` and you will see `simpleSNS` in the list.

`db.js` - Database setting file. It contains database account(id, password), database name, etc.

`packing.json` - The description of the app. It also contains the information of modules. You can install the described modules with a command `npm install`. So, when you push node.js project, you can exclude `node_modules` because `package.json` knows what to install. It will make your repository less heavier. `package.json` also can contain about app information such as app name, version, script for execute some commands.

`routes` - For `routers`. The backend developer mostly work on this folder. You write your business logic here.

`view` - For a web app. You don't need this part if you are a mobile app developer.

`public` - For static web page such as image files or html.

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

```javascript
var express = require('express'); // module doesn't need to specify its path.
var db = require('../db'); // if it's not a module, then you need to specify the correct path.
var router = express.Router();

router.post('/login', function(req, res, next){ // If the client sends a request with POST method, then you need to use `router.post`.
	var a = req.body.a; // If the client sends a request in @Body or @Field, you need to get the data with req.body.item_name;
	var b = req.body.b;

	// TODO:...
});

module.exports = router; // all routers must end with this.
```

# db.get().query(...)

```javascript
db.get().query(sql, input, function(err, result) {
	if(err){
		console.log("err: " + JSON.stringify(err,null,2));
		res.json({message:"SQL error", code:403, request:false}); // get the json data. The client would need `Gson`.
	}
	console.log("result: " + JSON.stringify(result,null,2));

	if(result.length > 0){ // If there are some affectnesses after the query.
		res.json({message:"Success!", code:100, request:true});
	}else{ // If there is any affectnesses after the query.
		res.json({message:"No User!", code:403, request:false});
	}
});
```
