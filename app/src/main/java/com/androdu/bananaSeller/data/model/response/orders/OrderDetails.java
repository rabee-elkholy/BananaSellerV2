
package com.androdu.bananaSeller.data.model.response.orders;

import java.util.List;

import com.androdu.bananaSeller.data.model.response.order.LocationDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetails {

    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("locationDetails")
    @Expose
    private LocationDetails locationDetails;
    @SerializedName("category")
    @Expose
    private List<String> category = null;
    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("arriveDate")
    @Expose
    private Long arriveDate;
    @SerializedName("products")
    @Expose
    private List<OrderProduct> products = null;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<OrderProduct> getProducts() {
        return products;
    }

    public void setProducts(List<OrderProduct> products) {
        this.products = products;
    }

    public LocationDetails getLocationDetails() {
        return locationDetails;
    }

    public void setLocationDetails(LocationDetails locationDetails) {
        this.locationDetails = locationDetails;
    }

    public Long getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Long arriveDate) {
        this.arriveDate = arriveDate;
    }


}
