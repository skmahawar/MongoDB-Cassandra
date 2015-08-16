db.campaigns.aggregate([{
    $match:{ user:ObjectId("5558ee440b60e9c01448e1ec"),status:'active'}},
    {$unwind:"$publishers"},
    { $group:{"_id":"$publishers", Publishs:{$addToSet:"$publishers"}}},
    {$unwind:"$Publishs"}, 
    {$project:{ status:1 }},
{$group:{ _id:"$status", count:{$sum:1}}}])