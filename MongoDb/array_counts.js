db.studios.aggregate([{ $project :{
        class_counts : { $size: "$classes" }
    }}])