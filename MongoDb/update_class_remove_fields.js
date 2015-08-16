db.studios.find().forEach(function(studio){
    
    studio.classes.forEach(function(_class){
            delete _class.date;
        delete _class.from;
        delete _class.to;
        delete _class.gender;
        delete _class.demand;
        delete _class.inventory;
        delete _class.teacher;
        
        return _class;
        });
        
        db.studios.save(studio);
        
    })