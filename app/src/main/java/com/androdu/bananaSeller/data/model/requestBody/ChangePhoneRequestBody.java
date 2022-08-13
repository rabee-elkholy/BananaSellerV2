package com.androdu.bananaSeller.data.model.requestBody;

public class ChangePhoneRequestBody {
    String mobile, code;

    public ChangePhoneRequestBody(String mobile, String code) {
        this.mobile = mobile;
        this.code = code;
    }
}
