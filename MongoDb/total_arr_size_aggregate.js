var sum = 0;
var arr = db.contacts.aggregate([
{ $group: { _id: "$user", root: { $first : "$$ROOT" } } },
{ $project: { _id: 1, google: { $size: "$root.googles" }, facebook: { $size : "$root.facebooks" }, contact: { $size : "$root.contacts"} } }
]);
arr.result.forEach(function(res){ 
    sum += (res.google + res.facebook + res.contact);
    })
    print(sum);