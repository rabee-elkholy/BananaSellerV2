
package com.androdu.bananaSeller.data.model.response.notificationSettings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationSetting {

    @SerializedName("sendNotfication")
    @Expose
    private SendNotfication sendNotfication;
    @SerializedName("_id")
    @Expose
    private String id;

    public SendNotfication getSendNotfication() {
        return sendNotfication;
    }

    public void setSendNotfication(SendNotfication sendNotfication) {
        this.sendNotfication = sendNotfication;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
