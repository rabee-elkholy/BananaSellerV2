
package com.androdu.bananaSeller.data.model.response.clientInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientInfoResponse {

    @SerializedName("state")
    @Expose
    private Integer state;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
