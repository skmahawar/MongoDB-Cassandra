db.homeSales.aggregate([
    {
      $sort: {amount: -1}
    },
    {
      $group:
      {
        _id: {$year: "$date"},
        priciestPostCode: {$first: "$address.postcode"}
      }
    },
    {
      $lookup:
      {
        from: "postcodes",
        localField: "priciestPostCode",
        foreignField: "postcode",
        as: "locationData"
      }
    },
    {
      $sort: {_id: -1}
    },
    {
      $project:
      {
        _id: 0,
        Year: "$_id",
        PostCode: "$priciestPostCode",
        Location: "$locationData.location"
      }
    }
  ])