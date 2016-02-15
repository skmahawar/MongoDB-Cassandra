db.actions.aggregate([{
    $match: {
        resolved: {
            $gte: ISODate("2015-01-01T00:00:00.000Z"),
            $lt: ISODate("2016-02-21T00:00:00.000Z")
        }
    }
}, {
    $project: {
        email: 1,
        phone: 1,
        deviceIds: 1,
        response: 1,
        id: 1
    }
}, {
    $group: {
        _id: {
            event: "$id"
        },
        emails: { $addToSet: { email: "$email", status: "$response.message" } },
        phones: { $addToSet: { phone: "$phone", status: "$response.status" } },
        devices: { $addToSet: { deviceIds: "$deviceIds", status: "$response.success" } }
    }
}, {
    $project: {
        event: "$_id.event",
        _id: 0,
        emails: {
            $map: {
                input: "$emails",
                as: "email",
                in : {
                    email: "$$email.email",
                    status: {
                        $cond: {
                            if: {
                                $eq: ["$$email.status", "Queued. Thank you."]
                            },
                            then: "success",
                            else: "failure"
                        }
                    }
                }
            }
        },
        phones: {
            $map: {
                input: "$phones",
                as: "phone",
                in : {
                    phone: "$$phone.phone",
                    status: {
                        $cond: {
                            if: {
                                $eq: ["$$phone.status", "queued"]
                            },
                            then: "success",
                            else: "failure"
                        }
                    }
                }
            }
        },
        devices: {
            $map: {
                input: "$devices",
                as: "device",
                in : {
                    device: "$$device.deviceIds",
                    status: {
                        $cond: {
                            if: {
                                $eq: ["$$device.status", 1]
                            },
                            then: "success",
                            else: "failure"
                        }
                    }
                }
            }
        }
    }
}])
