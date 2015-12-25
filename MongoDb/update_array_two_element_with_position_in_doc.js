db.getCollection('groups').update({ _id:ObjectId('564c2b4e5a532ee217c64bfe') },
{ $push: { members: { $each: [{
            "userId" : ObjectId("55d306f323d524ff420e84d5"),
            "name" : "Akash Gupta",
            "gender" : "male",
            "birthday" : ISODate("1989-08-07T18:30:00.000Z"),
            "photo" : "http://hngre.groups.test.s3.amazonaws.com/t_1447678224492.jpg"
        }, 
        {
            "name" : "Rohit Kumar",
            "inviteId" : ObjectId("564c2b4e5a532ee217c64bfa"),
            "email" : "rohit@hngre.com",
            "deepLink" : "https://bnc.lt/l/8qHfYu-AKc"
        }],
        $position:4,
        $slice:13 
        } }})