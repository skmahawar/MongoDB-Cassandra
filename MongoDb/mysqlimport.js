var mysql = require('mysql'),
config = {
    host: '0.0.0.0',
    //user: 'newdbuser',
    //password: 'bp55dhL@H0aqmuqB',
    database: 'videobox'
},
pool  = mysql.createPool(config),
async = require('async');

var mongoose = require('mongoose');


// Connect to mongodb
var connect = function () {
  var options = { server: { socketOptions: { keepAlive: 1 } } };
  mongoose.connect('mongodb://localhost/ledgling', options);
};
connect();

mongoose.connection.on('error', console.log);
mongoose.connection.on('disconnected', connect);

/**
 * Module dependencies.
 */


var Schema = mongoose.Schema;

/**
 * Playlist Schema
 */

var VideoSchema = new Schema({
  type: {type : String, default : '', trim : true},
  title :  {type : String, default : '', trim : true},
  description :  {type : String, default : '', trim : true},
  duration :  {type : String, default : '', trim : true},
  thumbnail :  {type : String, default : '', trim : true},
  uploaded :  {type : String, default : '', trim : true},
  video_url :  {type : String, default : '', trim : true},
  category_id: {type : Number },
  views: {type : Number },
  likes: {type : Number },
  unlikes: {type : Number },
  playlist: {type : Schema.ObjectId, ref : 'Playlist'},
  featured :  {type : String, default : '', trim : true},
  published :  {type : String, default : '', trim : true},
  createdAt  : {type : Date, default : Date.now}
});
var Video = mongoose.model('Video', VideoSchema);


/**
 * Playlist Schema
 */

var PlaylistSchema = new Schema({
  title: {type : String, default : '', trim : true},
  description :  {type : String, default : '', trim : true},
  thumbnail :  {type : String, default : '', trim : true},
  videos: [{type : Schema.ObjectId, ref : 'Video'}],
  createdAt  : {type : Date, default : Date.now}
});

var Playlist = mongoose.model('Playlist', PlaylistSchema);

function mysql_real_escape_string (str) {
  if(str){
    str = str.toString();
      return str.replace(/[\0\x08\x09\x1a\n\r"'\\\%]/g, function (char) {
          switch (char) {
              case "\0":
                  return "\\0";
              case "\x08":
                  return "\\b";
              case "\x09":
                  return "\\t";
              case "\x1a":
                  return "\\z";
              case "\n":
                  return "\\n";
              case "\r":
                  return "\\r";
              case "\"":
              case "'":
              case "\\":
              case "%":
                  return "\\"+char; // prepends a backslash to backslash, percent,
                                    // and double/single quotes
          }
      });
  }else{
    return str
  }
}



// get a clean connection 
function getCleanConnection(ret){
  pool.getConnection(function(err, connection) {
    if (err) {
      pool  = mysql.createPool(config);
      pool.getConnection(function(err, connection) {
        if(err){
          ret( err, null);
        }else{
          ret(null,connection);
        }
    });
    }else{
      ret(null,connection);
    }
  });
}




function execQuery(query, callback){
  getCleanConnection(function(err,connection){
    if (!connection) {
      console.error('error connecting: ');
      callback({err:' Connection Error err:1000 ', message: ' Connection Error', id: 1000}, null)
    }else{
      connection.query(query, function (err, results) {
        //console.log(err, 'get')
        if (err) callback(err, null)
        if (results !== 'undefined' && results.length > 0) 
        {
          callback(null,results)
        } 
        else 
        {
          callback(null, null);
        }
        connection.release();
      });
    }
  });

}



execQuery( " SELECT * from CustomPlaylists " , function(err,playlists){
	if(err){
		return console.log(err)
	}else{
		var id=0;
		async.forEach(playlists, function(playlist){
			var mgoplaylist = new Playlist(playlist);
			execQuery( "SELECT * FROM videos where pID = " + mysql_real_escape_string(playlist.id) , function(err,videos){
				if(err){
					return console.log(err);
				}else{
					async.forEach(videos, function(video){
						video.playlist = mgoplaylist._id
						mgvideo = new Video(video);
						mgoplaylist.videos.push(mgvideo);
						mgvideo.save(function(err){
							console.log(err||id++);
							mgoplaylist.save();
						})
					});
				}
			});


		})
	}
})
