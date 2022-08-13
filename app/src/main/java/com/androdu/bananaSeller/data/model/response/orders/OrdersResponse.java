
package com.androdu.bananaSeller.data.model.response.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrdersResponse {

    @SerializedName("state")
    @Expose
    private Integer state;
    @SerializedName("data")
    @Expose
    private List<Order> orders = null;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("banana_delivery_price")
    @Expose
    private double bananaDeliveryPrice;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getBananaDeliveryPrice() {
        return bananaDeliveryPrice;
    }

    public void setBananaDeliveryPrice(double bananaDeliveryPrice) {
        this.bananaDeliveryPrice = bananaDeliveryPrice;
    }
}
