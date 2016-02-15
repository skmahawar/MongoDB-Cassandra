db.homeSales.aggregate([{ $group: { 
    _id: { $year: "$date" }, 
    highestPrice: { $max: "$amount" },
    lowestPrice: { $min: "$amount" }, 
    averagePrice: { $avg: "$amount" },
    priceStdDev: { $stdDevPop: "$amount" }
  } 
},{
    $sort: { _id:1 }
},{
    $project: { 
        _id: 1,
        highestPrice:1,
        lowestPrice:1,
        averagePrice: { $trunc: "$averagePrice" },
        priceStdDev: { $trunc: "$priceStdDev" }
    }
},{
    $out: "annualHomePrices"
}])