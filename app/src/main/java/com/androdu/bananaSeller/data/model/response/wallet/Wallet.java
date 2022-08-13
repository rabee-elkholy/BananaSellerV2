
package com.androdu.bananaSeller.data.model.response.wallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wallet {

    @SerializedName("wallet")
    @Expose
    private Double wallet;
    @SerializedName("bindingWallet")
    @Expose
    private Double bindingWallet;
    @SerializedName("_id")
    @Expose
    private String id;

    public Double getWallet() {
        return wallet;
    }

    public void setWallet(Double wallet) {
        this.wallet = wallet;
    }

    public Double getBindingWallet() {
        return bindingWallet;
    }

    public void setBindingWallet(Double bindingWallet) {
        this.bindingWallet = bindingWallet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
