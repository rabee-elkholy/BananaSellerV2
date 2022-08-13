
package com.androdu.bananaSeller.data.model.response.clientInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("adress")
    @Expose
    private String adress;
    @SerializedName("payMathod")
    @Expose
    private String payMathod;
    @SerializedName("accountMobile")
    @Expose
    private String accountMobile;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("date")
    @Expose
    private Long date;
    @SerializedName("image")
    @Expose
    private int image;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPayMathod() {
        return payMathod;
    }

    public void setPayMathod(String payMathod) {
        this.payMathod = payMathod;
    }

    public String getAccountMobile() {
        return accountMobile;
    }

    public void setAccountMobile(String accountMobile) {
        this.accountMobile = accountMobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
