
package com.androdu.bananaSeller.data.model.response.myOrders;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("locationDetails")
    @Expose
    private LocationDetails locationDetails;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("arriveDate")
    @Expose
    private Long arriveDate;

    public LocationDetails getLocationDetails() {
        return locationDetails;
    }

    public void setLocationDetails(LocationDetails locationDetails) {
        this.locationDetails = locationDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Long arriveDate) {
        this.arriveDate = arriveDate;
    }

}
