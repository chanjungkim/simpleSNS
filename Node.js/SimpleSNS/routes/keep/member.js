var express = require('express');
var db = require('../db');
var router = express.Router();

//member/:
router.get('/:email, function(req, res, next) {
	var email = req.params.email;

	var sql = "select* " +
		  "from SimpleSNS_member " +
		  "where email = ? limit 1;";
	console.log("sql : " + sql);

	db.get().query(sql, email, function err, rows) {
		console.log("rows : "  + JSON.stringify(rows));
		console.log("row.length : " + rows.length);
		if ( rows.length > 0) {
			res.json(rows[0]);
		} else {
			res.sendStatus(400);
		}
	});
});

module.exports = router;
