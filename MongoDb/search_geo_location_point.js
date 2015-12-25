db.predictions.find({
  "score": {
    "$gte": 90
  },
  "location": {
        "$near": {
          "$geometry": {
            "type": "Point",
            "coordinates": [
              40.780361,
              -73.968204
            ]
          },
          "$minDistance": 100
        }
      },
  "userId": {
    "$in": [
      "566028a5b26360b76da875f7"
    ]
  }
})