package com.androdu.bananaSeller.data.model.requestBody;

public class NotificationSettingsRequestBody {

    boolean all, nearOrders, issues, orderStatus, update;

    public NotificationSettingsRequestBody(boolean all, boolean nearOrders, boolean issues, boolean orderStatus, boolean update) {
        this.all = all;
        this.nearOrders = nearOrders;
        this.issues = issues;
        this.orderStatus = orderStatus;
        this.update = update;
    }
}
