
package com.androdu.bananaSeller.data.model.response.complaints;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Complaint {

    @SerializedName("imageUrl")
    @Expose
    private List<String> imageUrl = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("reason")
    @Expose
    private Reason reason;
    @SerializedName("demands")
    @Expose
    private String demands;

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public String getDemands() {
        return demands;
    }

    public void setDemands(String demands) {
        this.demands = demands;
    }

}
