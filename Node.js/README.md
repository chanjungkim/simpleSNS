# Introduction

`app.js` - The main file for its server. It's the highest file among all the file. It contains `modules`, `db.js`, `routers`.

`bin/www` or `bin/simpleSNS` - A file for execution. You can run the server by `pm2 start bin/simpleSNS` and then you can see the list of running applications with `pm2 list` and you will see `simpleSNS` in the list.

`db.js` - Database setting file. It contains database account(id, password), database name, etc.

`packing.json` - The description of the app. It also contains the information of modules. You can install the described modules with a command `npm install`. So, when you push node.js project, you can exclude `node_modules` because `package.json` knows what to install. It will make your repository less heavier. `package.json` also can contain about app information such as app name, version, script for execute some commands.

`routes` - For `routers`. The backend developer mostly work on this folder. You write your business logic here.

`view` - For a web app. You don't need this part if you are a mobile app developer.

`public` - For static web page such as image files or html.

### simple view

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

<div id="method-list"> </div>

# Method Types and examples

| No | Client(Retrofit)         | Parameters in RemoteService(interface)          | Nodejs(method)            | Nodejs(get data)       |
| -- | ------------------------ |-------------------------------------------------| --------------------------| -----------------------|
| 1  | @GET("/member/{xxx}")    | @Path("xxx") String abc                         | router.get('/:xxx', f)    | req.params.xxx         |
| 2  | @GET("/member/xyz")      | @Query("xxx") String abc                        | router.get('/xyz', f)     | req.query.xxx          |
| 3  | @POST("/member/{xxx}")   | @Path("xxx") String abc                         | router.post('/:xxx', f)   | req.params.xxx         |
| 4  | @POST("/member")         | @Body("member") MemberItem abc                  | router.post('/', f)       | req.body.member.xxx    |
|    |            -             |                      -                          |            -              | req.body.member.yyy    |
| 5  | @FormUrlEncoded          |                      -                          |            -              |           -            |
|    | @POST("/member")         | @Field("xxx") String a, @Field("yyy") String b  | router.post('/', f)       | req.body.xxx           |
|    |            -             |                      -                          |            -              | req.body.yyy           |
| 6  | @PUT("/member/{xxx}")    | @Path("xxx") String abc                         | router.put('/:xxx', f)    | req.params.xxx         |
| 7  | @Multipart               |                      -                          |            -              |           -            |
|    | @PUT("/member/xxx")      | @Part("image") Img img, @Part("info") Info info | router.put('/xxx', f)     | [multer](https://github.com/expressjs/multer), [Multiparty Library](https://github.com/pillarjs/multiparty)               |
| 8  | @PATCH("/member/{xxx}")  | @Path("xxx") String abc                         | router.patch('/:xxx', f)  | req.params.xxx         |
| 9  | @PATCH("/member")        | @Body("member") MemberItem abc                  | router.patch('/', f)      | req.body.member.xxx    |
|    |            -             |                      -                          |            -              | req.body.member.yyy    |     
| 10 | @DELETE("/member/{xxx}") | @Path("xxx") String abc                         | router.delete('/:xxx', f) | req.params.xxx         |
| 11 | @DELETE("/member")       | @Query("xxx") String abc                        | router.delete('/', f)     | req.query.xxx          |

NOTE: You may need to encode and decode when you use `query`. For example, `Koreran`.

For more information, see [Retrofit Document](http://devflow.github.io/retrofit-kr).

### How router looks like

##### router overview

One router can have multi-methods. And one method looks something like this:
```
router.METHODNAME('/route', function(req,res,next){
    // TODO
});
```

```javascript
var express = require('express'); // module doesn't need to specify its path.
var db = require('../db'); // if it's not a module, then you need to specify the correct path.
var router = express.Router();

// After app.js fine the right router then this file runs if it matches and even in this router, There are many routes like below:

router.post('/', function(req, res, next){ // If the client sends a request with POST method, then you need to use `router.post`.
	// TODO:...
});

router.post('/login', function(req, res, next){
	var a = req.body.a; // If the client sends a request in @Body or @Field, you need to get the data with req.body.item_name;
	var b = req.body.b;

	// TODO:...
});

router.get('/user/id', function(req, res, next){ // If the client sends a request with GET method, then you need to use `router.get`.
	var a = req.query.a; // If the client sends a request in @Query, req.query.item_name or in @Path, you need req.params.item_name

	// TODO:...
});

module.exports = router; // all routers must end with this.
```

#### db process

If you need to access db(mysql module is used here). You will need to implement something like this:

```
db.get().query(sql, input, function(err, result){
    // TODO
});
```

```
db.get().query(sql, input, function(err, result){
    if(err) console.log(err); // it shows sql error message.
    res.json({code:100, message:"Sign-up Success!""}); // it responses to the client with the result.
});
```

```javascript
db.get().query(sql, input, function(err, result) {
	if(err){
		console.log("err: " + JSON.stringify(err,null,2));
		res.json({message:"SQL error", code:403, request:false}); // get the json data. The client would need `Gson`.
	}
	console.log("result: " + JSON.stringify(result,null,2));

	if(result.length > 0){ // If there are some affectnesses after the query, it shows how many rows has been affected by length.
		res.json({message:"Success!", code:100, request:true});
	}else{ // If there is any affectnesses after the query.
		res.json({message:"No User!", code:403, request:false});
	}
});
```

# Interface

### Type of Method.

| method | SQL	    | description         |
|:------:|:--------:|:--------------------|
| POST	 | INSERT   | 등록할 때 사용합니다. |
| GET	 | SELECT   | 얻어올 때 사용합니다. |
| PUT	 | UPDATE   | 수정 시 사용합니다.   |
| DELETE | DELETE   | 삭제 시 사용합니다.   |

### APIs in this project.

| num | method | router                  | name                    | description                                                       |
|:---:|:-------|:------------------------|:------------------------|:------------------------------------------------------------------|
| 1   | POST   | /member                 | 회원가입                 | 본인 팔로잉(팔로워, 팔로잉 리스트엔 미적용), 가입 후 /login 호출      |
| 2   | POST   | /member/login           | 로그인                   |                                                                  |
| 3   | GET    | /member                 | 유저 아이디 리턴          | 토큰 가져와서 로컬에 jwt, myId의 SharedPreference 저장.            |
|     |        |                         |                          |                                                                 |
| 4   | POST   | /feed                   | 게시글 작성               | 사진 포함(필요시 POST, /image 구성)                               | 
| 5   | PUT    | /feed                   | 게시글 수정	        | (사진수정X)	                                               |
| 6   | DELETE | /feed                   | 게시물 삭제               |                                                                 |
| 7   | POST   | /feed/like              | 좋아요 누르기              |                                                                 |
| 8   | GET    | /feed/like              | 좋아요 한 사람들 조회       |                                                                |
| 9   | GET    | /feed                   | 팔로잉한 사람들 글 불러오기  | (jwt, lastPostNum) 처음이면 5개, 처음 아니면 3개씩               |
| 10  | POST   | /comment                | 댓글 달기                   |                                                               |
| 11  | GET    | /comment                | 댓글 조회                   |                                                               |
|     |        |                         |                            |                                                                |
| 12  | POST   | /follow	         | 팔로우 추가		  |                                                                |
| 13  | GET    | /follow/follower        | 팔로워 리스트               |                                                                |
| 14  | GET    | /follow/following       | 팔로잉 리스트     	         |                                                                |
| 15  | GET    | /follow/history         | 팔로우한 사람들의 흔적보기   | (backend - like, comment 에 시간 넣기)                          |
|     |        | 		         | 	                      |                                                                |
| 16  | GET    | /profile                | 프로필 조회	  	  |                                                                |
| 17  | PUT    | /profile                | 프로필 수정		  | (아이디 수정 O)					              |	
| 18  | GET    | /profile/:username      | 유저 정보		   |                                                               |
| 19  | GET    | /profile/feed/:username | 유저 게시물 피드	        |                                                               |
|     |        | 			 |                            |                                                               |
| 20  | POST   | /email/send             | 메일 주소 인증 이메일 보내기 | 메일함에서 4자리 인증 코드만 확인하는 방법                       |
| 21  | POST   | /email/verify           | 인증 코드 체크              | 클라이언트 단에서 4자리 인증 코드를 입력하면 체크함               |
