db.merchants.aggregate([
    { "$match": { "url": { "$regex": "p" } } },
    { "$group": { 
        "_id": {
            "$cond": {
                "if": { "$eq": [ "$status", "completed" ] },
                "then": "success",
                "else": "fail"
            }
        },
        "count": { "$sum": 1 },
        "docs":{ "$push" : "$$ROOT" }
    }}
])