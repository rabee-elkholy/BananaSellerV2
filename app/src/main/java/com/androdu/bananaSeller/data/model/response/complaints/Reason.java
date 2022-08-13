
package com.androdu.bananaSeller.data.model.response.complaints;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reason {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("reason_ar")
    @Expose
    private String reasonAr;
    @SerializedName("reason_en")
    @Expose
    private String reasonEn;
    @SerializedName("reason_urdu")
    @Expose
    private String reasonUr;
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

    public String getReasonAr() {
        return reasonAr;
    }

    public void setReasonAr(String reasonAr) {
        this.reasonAr = reasonAr;
    }

    public String getReasonEn() {
        return reasonEn;
    }

    public void setReasonEn(String reasonEn) {
        this.reasonEn = reasonEn;
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

    public String getReasonUr() {
        return reasonUr;
    }

    public void setReasonUr(String reasonUr) {
        this.reasonUr = reasonUr;
    }
}
