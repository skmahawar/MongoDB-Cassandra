db.studios.find().forEach(function(studio){
        studio.classes.forEach(function(class_){
                class_.category = [class_.category];
                return class_;
            });
            db.studios.save(studio);
    })