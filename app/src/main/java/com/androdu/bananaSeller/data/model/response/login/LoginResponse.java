
package com.androdu.bananaSeller.data.model.response.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("state")
    @Expose
    private Integer state;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private SellerData sellerData;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SellerData getSellerData() {
        return sellerData;
    }

    public void setSellerData(SellerData sellerData) {
        this.sellerData = sellerData;
    }

}
