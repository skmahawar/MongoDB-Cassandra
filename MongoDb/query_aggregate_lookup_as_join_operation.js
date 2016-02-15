db.homeSales.aggregate([{ $match:  { amount: { $gte: 300000 } } },
                        { $lookup: { from:"postcodes", localField:"address.postcode", foreignField: "postcode",
                                     as : "postcode_docs" } 
                         }])