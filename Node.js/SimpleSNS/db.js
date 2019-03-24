const mysql = require('mysql');

var pool;

exports.connect = function(done){
	console.log("Trying to connect DB...");
	pool = mysql.createPool({
		host: 'localhost',
		user: 'root',
		password: 'simple1234',
		database:"SimpleSNS",
		connectionLimit: 100 // Why 5 ???
	});
	console.log("DB Connected.");
/*
	pool.getConnection()
		.then(conn => {
   		console.log("DB connected. id: " + conn.threadId);
      conn.end(); //release to pool
    }).catch(err => {
      console.log("DB failed connection: " + err);
    });
*/
}

/*
function makeToken(){
	console.log("makeToken()");
  var text = "";
  var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
  for(var i=0;i<32;i++){
      text+=possible.charAt(Math.floor(Math.random()*possible.length));
  }
  return text;
}

function dbHelper() {
	console.log("dbHelper()");

  this.emailRequest = async function(email,num){
	console.log("emailRequest");
    pool.getConnection().then(conn => {
      conn.query("INSERT INTO email_verification(email, code) VALUES(?,?)",[email,num]);
      conn.end(); //release to pool
    })
    .catch(err => {
      console.log("not connected due to error: " + err);
    });
  }

  //return value가 필요해서 await을 사용해 결과가 나올때까지 기다리면서 처리한다. 
  this.verify = async function(email,num){
	console.log("verify");
    let conn;
    var result = false;
    try {
      conn = await pool.getConnection();
      // within 3minutes
      const rows = await conn.query("SELECT count(*) FROM email_verificaiton WHERE email=? AND code=? AND req_time >= NOW() - INTERVAL 3 MINUTE",[email,num]);
      if(rows[0]["count(*)"]>0){
        result = true;
      }
    } catch (err) {
      throw err;
    } finally {
      if (conn) conn.end();
    }
      return result;
  }

  this.verifyUpdate = function(email,num){
	console.log("verifyUpdate()= email: "+email+", num: "+num);
    pool.getConnection()
    .then(conn => {
      conn.query("UPDATE email_verification SET status = 1 WHERE email=? AND code=?",[email,num]);
      conn.end(); //release to pool
    })
    .catch(err => {
      console.log("not connected due to error: " + err);
    });
  }

  //return value가 필요해서 await을 사용해 결과가 나올때까지 기다리면서 처리한다. 
  this.emailRegister = async function(email,pass,nick,devid){
		console.log("emailRegister()= email: "+email+", pass:"+pass+",nick: "+nick+", devid:"+devid);
    let conn;
    var result;
    try {
      conn = await pool.getConnection();
			var sql = "SELECT count(*) FROM email_verification WHERE email=? AND status = 1";
			var input = [email];
      var rows = await conn.query(sql, input);

			// If there are already verified emails
      if(rows[0]["count(*)"]>0){
				var sql = "SELECT count(*) FROM member WHERE email=?";
				var input = [email];
        rows = await conn.query("SELECT count(*) FROM member WHERE email=?",[email]);

				// If there is no member with the email to verify
        if(rows[0]["count(*)"]==0){
          var token = makeToken();
					var sql = "INSERT INTO member (email,password,username,device_id,login_method,token) VALUES(?,?,?,?,0,?)";
					var input = [email,pass,nick,devid,token];
          rows = await conn.query(sql, input);

					// If the member signed up correctly.
          if(rows["affectedRows"]>0){
            result = {result:true, code:200, message: "Sign Up Success",data:[{email:email,token:token}]};
          } else{
            result = {result:false,code:401, message:"DB error"};
          }

				// Already registered...
        }else {
          result = {result:false,code:402, message:"already registered id"};
        }

			// The email hasn't been verified yet.
      } else {
        result = {result:false,code:403, meesage:"email not verified"};
      }
    } catch (err) {
			console.log(err);
      throw err;
    } finally {
      if (conn) conn.end();
    }
    return result;
  }

  //return value가 필요해서 await을 사용해 결과가 나올때까지 기다리면서 처리한다. 
  this.emailLogin = async function(email,pass,devid){
	console.log("emailLogin()= email: "+email+", pass: "+pass+", devid: "+devid);
		let conn;
		var result;
		try {
			conn = await pool.getConnection();
			var sql = "SELECT * FROM member WHERE email=?";
			var input = [email];
			rows = await conn.query(sql,input);
			if(rows.length==1){
				if(rows[0]["password"]==pass){
					var token = makeToken();
					var sql = "UPDATE member SET device_id = ?, token = ? WHERE email=?";
					var input = [devid, token, email];
          rows = await conn.query(sql, input);
          console.log(rows)
          if(rows["affectedRows"]>0){
            result = {result:true,message:"Sign Up Success.", code:200, data:[{email:email,token:token}]};
          } else{
            result = {result:false,message:"DB error",code:401};
          }
        } else {
          result = {result:false,message:"Wrong password",code:402};
        }
      }else {
        result = {result:false,message:"not registered id",code:403};
      }
    } catch (err) {
      throw err;
    } finally {
      if (conn) conn.end();
    }
    return result;
  }
}
*/
exports.get = function(){
//	pool.dbHelper = new dbHelper();
  return pool;
}
