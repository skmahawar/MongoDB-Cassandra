db.contacts.aggregate([
{ $group: { _id: "$user", root: { $first : "$$ROOT" } } },
{ $project: { _id: 1, contact: "$root.contacts" } },
{ $unwind: "$contact" },
{ $project: { phone: "$contact.phones" } },
{ $unwind: "$phone" },
{ $group : { _id: "$phone"}}
]);