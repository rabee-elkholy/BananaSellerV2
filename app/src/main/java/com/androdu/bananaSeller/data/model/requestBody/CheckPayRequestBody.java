package com.androdu.bananaSeller.data.model.requestBody;

public class CheckPayRequestBody {

    String checkoutId, offerId, name, mobile, adressString;
    long arriveIn;

    public CheckPayRequestBody(String checkoutId, String offerId, String name, String mobile, String adressString, long arriveIn) {
        this.checkoutId = checkoutId;
        this.offerId = offerId;
        this.name = name;
        this.mobile = mobile;
        this.adressString = adressString;
        this.arriveIn = arriveIn;
    }
}
