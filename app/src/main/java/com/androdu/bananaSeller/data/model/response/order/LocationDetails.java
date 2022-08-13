
package com.androdu.bananaSeller.data.model.response.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationDetails {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("stringAdress")
    @Expose
    private String stringAdress;
    @SerializedName("mobile2")
    @Expose
    private String mobile2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStringAdress() {
        return stringAdress;
    }

    public void setStringAdress(String stringAdress) {
        this.stringAdress = stringAdress;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

}
