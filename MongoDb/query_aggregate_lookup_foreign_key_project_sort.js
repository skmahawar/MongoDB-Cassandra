db.homeSales.aggregate([{ $match : { amount: { $gte: 3000000 } } },
{ $lookup : { from: "postcodes", localField: "address.postcode", foreignField: "postcode", as : "postcode" } },
{ $project: { _id: 0, salesDate: "$date", price: "$amount", location: "$postcode.location" }},
{ $sort: { price: -1 }}])