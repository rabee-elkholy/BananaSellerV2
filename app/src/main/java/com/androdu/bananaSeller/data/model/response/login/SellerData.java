
package com.androdu.bananaSeller.data.model.response.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SellerData {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("sellerName")
    @Expose
    private String userName;
    @SerializedName("sellerMobile")
    @Expose
    private String userMobile;
    @SerializedName("sellerId")
    @Expose
    private String userId;
    @SerializedName("sellerImage")
    @Expose
    private Integer sellerImage;
    @SerializedName("sellerCatigory")
    @Expose
    private List<String> sellerCatigory;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getSellerImage() {
        return sellerImage;
    }

    public void setSellerImage(Integer sellerImage) {
        this.sellerImage = sellerImage;
    }

    public List<String> getSellerCatigory() {
        return sellerCatigory;
    }

    public void setSellerCatigory(List<String> sellerCatigory) {
        this.sellerCatigory = sellerCatigory;
    }
}
