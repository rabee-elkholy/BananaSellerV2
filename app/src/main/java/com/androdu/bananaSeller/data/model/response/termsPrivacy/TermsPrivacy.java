
package com.androdu.bananaSeller.data.model.response.termsPrivacy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TermsPrivacy {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("EN")
    @Expose
    private String eN;
    @SerializedName("AR")
    @Expose
    private String aR;
    @SerializedName("urdu")
    @Expose
    private String ur;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEN() {
        return eN;
    }

    public void setEN(String eN) {
        this.eN = eN;
    }

    public String getAR() {
        return aR;
    }

    public void setAR(String aR) {
        this.aR = aR;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getUr() {
        return ur;
    }

    public void setUr(String ur) {
        this.ur = ur;
    }
}
