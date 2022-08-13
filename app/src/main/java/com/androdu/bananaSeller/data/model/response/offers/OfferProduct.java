
package com.androdu.bananaSeller.data.model.response.offers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferProduct {

    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("cartItem")
    @Expose
    private String cartItem;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("equals")
    @Expose
    private Boolean equals;
    @SerializedName("product")
    @Expose
    private Product product;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCartItem() {
        return cartItem;
    }

    public void setCartItem(String cartItem) {
        this.cartItem = cartItem;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Boolean getEquals() {
        return equals;
    }

    public void setEquals(Boolean equals) {
        this.equals = equals;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
