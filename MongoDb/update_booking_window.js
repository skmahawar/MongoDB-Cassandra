db.studios.find({'booking_window':{ $type: 1} }).forEach(function(x){
    x.booking_window = new NumberInt(x.booking_window);
    db.studios.save(x);
    })