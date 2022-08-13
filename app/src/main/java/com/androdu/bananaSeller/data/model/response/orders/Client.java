
package com.androdu.bananaSeller.data.model.response.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Client {

    @SerializedName("total_client_orders")
    @Expose
    private Integer totalClientOrders;
    @SerializedName("ended_client_orders")
    @Expose
    private Integer endedClientOrders;

    public Integer getTotalClientOrders() {
        return totalClientOrders;
    }

    public void setTotalClientOrders(Integer totalClientOrders) {
        this.totalClientOrders = totalClientOrders;
    }

    public Integer getEndedClientOrders() {
        return endedClientOrders;
    }

    public void setEndedClientOrders(Integer endedClientOrders) {
        this.endedClientOrders = endedClientOrders;
    }

}
