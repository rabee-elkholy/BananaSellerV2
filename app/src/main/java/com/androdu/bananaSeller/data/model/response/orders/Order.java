
package com.androdu.bananaSeller.data.model.response.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("order")
    @Expose
    private OrderDetails orderDetails;
    @SerializedName("client")
    @Expose
    private Client client;
    @SerializedName("distance")
    @Expose
    private double distance;
    @SerializedName("sellerOffered")
    @Expose
    private boolean sellerOffered;

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean isSellerOffered() {
        return sellerOffered;
    }

    public void setSellerOffered(boolean sellerOffered) {
        this.sellerOffered = sellerOffered;
    }
}
