
package com.androdu.bananaSeller.data.model.response.myOrders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationDetails {

    @SerializedName("stringAdress")
    @Expose
    private String stringAdress;

    public String getStringAdress() {
        return stringAdress;
    }

    public void setStringAdress(String stringAdress) {
        this.stringAdress = stringAdress;
    }

}
