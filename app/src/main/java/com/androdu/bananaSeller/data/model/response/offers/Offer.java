
package com.androdu.bananaSeller.data.model.response.offers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Offer {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("order")
    @Expose
    private Order order;
    @SerializedName("seller")
    @Expose
    private String seller;
    @SerializedName("banana_delivery")
    @Expose
    private Boolean bananaDelivery;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("offerProducts")
    @Expose
    private List<OfferProduct> offerProducts = null;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public Boolean getBananaDelivery() {
        return bananaDelivery;
    }

    public void setBananaDelivery(Boolean bananaDelivery) {
        this.bananaDelivery = bananaDelivery;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<OfferProduct> getOfferProducts() {
        return offerProducts;
    }

    public void setOfferProducts(List<OfferProduct> offerProducts) {
        this.offerProducts = offerProducts;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
