// const mariadb = require('mariadb');
// const pool = mariadb.createPool({host: 'localhost', user: 'root', connectionLimit: 5});
 
// async function asyncFunction() {
//   let conn;
//   try {
//     conn = await pool.getConnection();
//     const rows = await conn.query("SELECT 1 as val");
//     console.log(rows); //[ {val: 1}, meta: ... ]
//     const res = await conn.query("INSERT INTO myTable value (?, ?)", [1, "mariadb"]);
//     console.log(res); // { affectedRows: 1, insertId: 1, warningStatus: 0 }
 
//   } catch (err) {
//     throw err;
//   } finally {
//     if (conn) return conn.end();
//   }
// }

const mariadb = require('mariadb');
const pool = mariadb.createPool({ host: 'localhost', user: 'root', password: 'simple123SNS', database:"SimpleSNS", connectionLimit: 5 });
pool.getConnection()
    .then(conn => {
      console.log("connected ! connection id is " + conn.threadId);
      conn.end(); //release to pool
    })
    .catch(err => {
      console.log("not connected due to error: " + err);
    });
function makeToken(){
  var text = "";
  var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
  for(var i=0;i<32;i++){
      text+=possible.charAt(Math.floor(Math.random()*possible.length));
  }
  return text;
}
function dbHelper() {
  this.emailRequest = function(email,num){
    pool.getConnection()
    .then(conn => {
      conn.query("INSERT INTO email_verify (e_mail ,verify_number ,request_time) VALUES(?,?,now())",[email,num]);
      conn.end(); //release to pool
    })
    .catch(err => {
      console.log("not connected due to error: " + err);
    });
  }
  //return value가 필요해서 await을 사용해 결과가 나올때까지 기다리면서 처리한다. 
  this.verify = async function(email,num){
    let conn;
    var result = false;
    try {
      conn = await pool.getConnection();
      const rows = await conn.query("SELECT count(*) FROM email_verify WHERE e_mail=? AND verify_number=? AND request_time >= NOW() - INTERVAL 3 MINUTE",[email,num]);
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
    pool.getConnection()
    .then(conn => {
      conn.query("UPDATE email_verify SET verified_status = 1 WHERE e_mail=? AND verify_number=?",[email,num]);
      conn.end(); //release to pool
    })
    .catch(err => {
      console.log("not connected due to error: " + err);
    });
  }
    //return value가 필요해서 await을 사용해 결과가 나올때까지 기다리면서 처리한다. 
  this.emailRegister = async function(email,pass,nick,devid){
    let conn;
    var result;
    try {
      conn = await pool.getConnection();
      var rows = await conn.query("SELECT count(*) FROM email_verify WHERE e_mail=? AND verified_status = 1",[email]);
      if(rows[0]["count(*)"]>0){
        rows = await conn.query("SELECT count(*) FROM member WHERE e_mail=?",[email]);
        if(rows[0]["count(*)"]==0){
          var token = makeToken();
          rows = await conn.query("INSERT INTO member (e_mail,password,nickname,device_id,login_method,token,recent_date) VALUES(?,?,?,?,0,?,now())",[email,pass,nick,devid,token]);
          if(rows["affectedRows"]>0){
            result = {result:"success",email:email,token:token};
          } else{
            result = {result:"failed",error:"db error"};
          }
          
        }else {
          result = {result:"failed",error:"already registered id"};
        }
      } else {
        result = {result:"failed",error:"email not verified"};
      }
    } catch (err) {
      throw err;
    } finally {
      if (conn) conn.end();
    }
    return result;
  }
  //return value가 필요해서 await을 사용해 결과가 나올때까지 기다리면서 처리한다. 
  this.emailLogin = async function(email,pass,devid){
    let conn;
    var result;
    try {
      conn = await pool.getConnection();
      rows = await conn.query("SELECT * FROM member WHERE e_mail=?",[email]);
      if(rows.length==1){
        if(rows[0]["password"]==pass){
          var token = makeToken();
          rows = await conn.query("UPDATE member SET device_id = ?, token = ? WHERE e_mail=?",[devid,token,email]);
          console.log(rows)
          if(rows["affectedRows"]>0){
            result = {result:"success",email:email,token:token};
          } else{
            result = {result:"failed",error:"db error"};
          }
        } else {
          result = {result:"failed",error:"wrong password"};
        }
      }else {
        result = {result:"failed",error:"not registered id"};
      }
    } catch (err) {
      throw err;
    } finally {
      if (conn) conn.end();
    }
    return result;
  }
}

module.exports = new dbHelper;