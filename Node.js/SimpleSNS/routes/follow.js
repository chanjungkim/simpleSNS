var express = require ('express');
var db = require('../db');
var router = express.Router();

router.post('/', function(req, res, next){
	console.log("/follow");
	var my_username = req.body.my_username;
	var his_username = req.body.his_username;

	console.log("my_username: "+my_username+", his_username: "+his_username);

	const sql = "SELECT uid FROM member WHERE username=? LIMIT 1";

	db.get().query(sql, [my_username], function(err, result){
		if(err){
			console.log("ERR: "+ JSON.stringify(err, null, 2));
			resDBError(res,err);
		}else{
			console.log("RESULT: "+JSON.stringify(result, null, 2));

			const sql_following = "SELECT uid FROM member WHERE username=? LIMIT 1";
 
			db.get().query(sql_following, [his_username], function(err, result_following){
				if(err){
					resDBError(res,err);
				}else{
					console.log("RESULT: "+JSON.stringify(result_following, null, 2));

					const sql_select = "SELECT * FROM follow WHERE uid = ? AND following_uid = ?";
					const input_select = [result[0].uid, result_following[0].uid];

					db.get().query(sql_select, input_select, function(err, result_check){
						if(err){
							console.log("ERR: "+JSON.stringify(err, null, 2));
						}else if(result_check.length > 0){
							console.log("Already following");
							res.json({code:201, message:"Already following", result:true});
						}else{
							console.log("RESULT: "+JSON.stringify(result_check, null, 2));
							const sql_insert = "INSERT INTO follow(uid, following_uid) values(?,?)";
							const input_insert = [result[0].uid, result_following[0].uid];
		
							db.get().query(sql_insert, input_insert, function(err, result){
								if(err){
									resDBError(res,err);
								}else{
									res.json({code:200, result:true, message:"Followed!"});
								}
							});
						}
					});
				}
			});
		}
	});
});

router.get('/check', function(req, res, next){
  console.log("/follow/check");
  var my_username = req.query.my_username;
  var his_username = req.query.his_username;

  console.log("my_username: "+my_username+", his_username: "+his_username);

  const sql = "SELECT uid FROM member WHERE username=? LIMIT 1";

  db.get().query(sql, [my_username], function(err, result){
    if(err){
      console.log("ERR: "+ JSON.stringify(err, null, 2));
      resDBError(res,err);
    }else{
      console.log("RESULT: "+JSON.stringify(result, null, 2));

      const sql_following = "SELECT uid FROM member WHERE username=? LIMIT 1";

      db.get().query(sql_following, [his_username], function(err, result_following){
        if(err){
          resDBError(res,err);
        }else{
          console.log("RESULT: "+JSON.stringify(result_following, null, 2));

          const sql_select = "SELECT * FROM follow WHERE uid = ? AND following_uid = ?";
          const input_select = [result[0].uid, result_following[0].uid];

          db.get().query(sql_select, input_select, function(err, result_check){
						if(err){
							console.log("ERR: "+JSON.stringify(err, null, 2));
						}else if(result_check.length > 0){
							console.log("Already following");
							res.json({code:200, message:"Already following", result:true});
    				}else{
							console.log("Not following");

							const sql_select_him = "SELECT * FROM follow WHERE uid = ? AND following_uid = ?";
							const input_select_him = [result_following[0].uid, result[0].uid];

							db.get().query(sql_select_him, input_select_him, function(err, result_check_him){
								if(err){
									console.log("ERROR: "+JSON.stringify(err, null, 2));
								}else if(result_check_him.length > 0){
									res.json({code:202, message:"He is following me", result:true});
								}else{
									res.json({code:201, message:"Nobody following yet", result:true});
								}
							});
						}
					});
        }
      });
    }
  });
});

router.get('/activity', function(req, res, next){
	console.log("/follow/activity");
	let username = req.query.username;

	const sql_username = "SELECT uid FROM member WHERE username = ? LIMIT 1";
	db.get().query(sql_username, [username], function(err, result_username){
		if(err){
			resDBError(res,err);
		}else{
			const sql_activity = "SELECT u.uid, m.username, m.photo_url, u.content, u.reg_time, u.type, u.whom, u.fid FROM"
				+" (SELECT c.uid, c.com_text as content, c.reg_time, 'comment' as type, f.fid, mem.username as whom FROM comment c, feed f, member mem"
				+" WHERE c.fid = f.fid AND f.uid = mem.uid AND f.uid!=?"
				+" UNION SELECT r.uid, r.action as description, r.reg_time, 'reaction' as type, f.fid, mem.username as whom FROM reaction r, feed f, member mem"
				+" WHERE r.fid = f.fid AND f.uid = mem.uid AND f.uid!=?) u, member m"
				+" WHERE u.uid IN(SELECT following_uid FROM follow fl WHERE fl.uid=?) AND u.uid = m.uid ORDER BY reg_time DESC";
			const uid = result_username[0].uid;
			const input = [uid, uid, uid];

			db.get().query(sql_activity, input, function(err, result){
				if(err){
					resDBError(res, err);
				}else if(result.length > 0){
					res.json({code:200, result:true, message:"Success", data:result});
				}else{
					// no data
					res.json({code:201, result:true, message:"no data"});
				}
			});
		}
	});
});

router.get('/mypost', function(req, res, next){
  console.log("/follow/mypost");
  let username = req.query.username;

  const sql_username = "SELECT uid FROM member WHERE username = ? LIMIT 1";
  db.get().query(sql_username, [username], function(err, result_username){
    if(err){
      resDBError(res,err);
    }else{
      const sql_activity = "SELECT u.uid, m.username, m.photo_url, u.content, u.reg_time, u.type, u.whom, u.fid FROM"
        +" (SELECT c.uid, c.com_text as content, c.reg_time, 'comment' as type, f.fid, mem.username as whom FROM comment c, feed f, member mem"
        +" WHERE c.fid = f.fid AND f.uid = mem.uid AND f.uid=?"
        +" UNION SELECT r.uid, r.action as description, r.reg_time, 'reaction' as type, f.fid, mem.username as whom FROM reaction r, feed f, member mem"
        +" WHERE r.fid = f.fid AND f.uid = mem.uid AND f.uid=?) u, member m"
        +" WHERE u.uid IN(SELECT following_uid FROM follow fl WHERE fl.uid=?) AND u.uid = m.uid ORDER BY reg_time DESC";
      const uid = result_username[0].uid;
      const input = [uid, uid, uid];

      db.get().query(sql_activity, input, function(err, result){
        if(err){
          resDBError(res, err);
        }else if(result.length > 0){
          res.json({code:200, result:true, message:"Success", data:result});
        }else{
          // no data
          res.json({code:201, result:true, message:"no data"});
        }
      });
    }
  });
});


function resDBError(res, err){
	console.log("ERR: "+JSON.stringify(err,null,2));
	res.json({code:403, result:false, message:"DB ERROR"});
}
module.exports = router;
