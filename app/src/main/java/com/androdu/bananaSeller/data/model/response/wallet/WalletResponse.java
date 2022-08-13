
package com.androdu.bananaSeller.data.model.response.wallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.ref.WeakReference;
import java.util.List;

public class WalletResponse {

    @SerializedName("state")
    @Expose
    private Integer state;
    @SerializedName("data")
    @Expose
    private List<WalletTransaction> data = null;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("wallet")
    @Expose
    private Wallet wallet;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<WalletTransaction> getData() {
        return data;
    }

    public void setData(List<WalletTransaction> data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
