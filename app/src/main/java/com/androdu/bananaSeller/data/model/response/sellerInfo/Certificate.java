
package com.androdu.bananaSeller.data.model.response.sellerInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Certificate {

    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("avilable")
    @Expose
    private Avilable avilable;
    @SerializedName("StringAdress")
    @Expose
    private String stringAdress;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Avilable getAvilable() {
        return avilable;
    }

    public void setAvilable(Avilable avilable) {
        this.avilable = avilable;
    }

    public String getStringAdress() {
        return stringAdress;
    }

    public void setStringAdress(String stringAdress) {
        this.stringAdress = stringAdress;
    }

}
