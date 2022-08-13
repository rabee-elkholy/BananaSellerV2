
package com.androdu.bananaSeller.data.model.response.order;

import com.androdu.bananaSeller.data.model.response.orders.Order;
import com.androdu.bananaSeller.data.model.response.orders.OrderDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResponse {

    @SerializedName("state")
    @Expose
    private Integer state;
    @SerializedName("data")
    @Expose
    private OrderDetails order = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public OrderDetails getOrder() {
        return order;
    }

    public void setOrder(OrderDetails order) {
        this.order = order;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
