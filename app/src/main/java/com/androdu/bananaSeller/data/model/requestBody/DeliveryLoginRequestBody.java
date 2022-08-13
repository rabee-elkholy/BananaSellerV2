package com.androdu.bananaSeller.data.model.requestBody;

public class DeliveryLoginRequestBody {
    private String mobile, password;

    public DeliveryLoginRequestBody(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }
}
