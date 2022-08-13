
package com.androdu.bananaSeller.data.model.response.notificationSettings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendNotfication {

    @SerializedName("all")
    @Expose
    private Boolean all;
    @SerializedName("nearOrders")
    @Expose
    private Boolean nearOrders;
    @SerializedName("issues")
    @Expose
    private Boolean issues;
    @SerializedName("orderStatus")
    @Expose
    private Boolean orderStatus;
    @SerializedName("update")
    @Expose
    private Boolean update;

    public Boolean getAll() {
        return all;
    }

    public void setAll(Boolean all) {
        this.all = all;
    }

    public Boolean getNearOrders() {
        return nearOrders;
    }

    public void setNearOrders(Boolean nearOrders) {
        this.nearOrders = nearOrders;
    }

    public Boolean getIssues() {
        return issues;
    }

    public void setIssues(Boolean issues) {
        this.issues = issues;
    }

    public Boolean getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Boolean orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Boolean getUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }

}
