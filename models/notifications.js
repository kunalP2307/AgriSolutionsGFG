const mongoose = require("mongoose");

const notificationSchema = mongoose.Schema(
    {
        userId: {
            type: String,
            required: true
        },
        all_notifications: []
    },
    {
        timestamps: true,
    }
);

const notification = mongoose.models.notification || mongoose.model('notification', notificationSchema);
module.exports = { notification };
