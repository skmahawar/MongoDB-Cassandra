db.homeSales.aggregate([{ $group: { 
    _id: { $year: "$date" }, 
    highestPrice: { $max: "$amount" },
    lowestPrice: { $min: "$amount" }, 
    averagePrice: { $avg: "$amount" },
    priceStdDev: { $stdDevPop: "$amount" }
  } 
},{
    $sort: { _id:1 }
    }])