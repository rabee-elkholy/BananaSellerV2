
package com.androdu.bananaSeller.data.model.response.offers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("locationDetails")
    @Expose
    private LocationDetails locationDetails;
    @SerializedName("_id")
    @Expose
    private String id;
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

    public Long getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Long arriveDate) {
        this.arriveDate = arriveDate;
    }

}
