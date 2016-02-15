db.annualHomePrices.aggregate([
{ $project: {
    Year: "$year",
    highToLowPriceGap: {
        $subtract: ["$highestPrice","$lowestPrice"]
         },
   _id: 0
  } 
}])