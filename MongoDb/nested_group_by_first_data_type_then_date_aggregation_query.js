db.actions.aggregate([
    {
      "$project": {
        "created_at": {
          "$dateToString": {
            "format": "%Y-%m-%d",
            "date": "$created"
          }
        },
        "id": true
      }
    },
    {
      "$group": {
        "_id": {
          "data_type": "$id",
          "created_at": "$created_at"
        },
        "count": {
          "$sum": 1
        }
      }
    },
    {
      "$group": {
        "_id": {
          "data_type": "$_id.data_type"
        },
        "data":{ "$addToSet" : { count: "$count", date: "$_id.created_at" } }
      }
    }
  ])