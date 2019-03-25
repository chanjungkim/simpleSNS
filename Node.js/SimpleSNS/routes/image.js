var express = require ('express');
var router = express.Router();
var formidable = require('formidable');
var fs = require('fs');

router.post('/', function (req, res, next) {
	var form = new formidable.IncomingForm();
	form.encoding = "utf-8";
	form.keepExtensions = true; // keep ext like jpeg
	//form.multiples = true;	// todo if files array
	form.parse(req);
	form.on('fileBegin', function (name, file) {
		console.log('fileBegin');
		file.path = './public/img/'+file.name;
	});
	form.on('file', function(name, file) {
		console.log(file);
	});

	console.log("/image : result success");
	res.json({message:'image upload success',code:100, result:'image url' }); // korinee test 

});

module.exports = router;
