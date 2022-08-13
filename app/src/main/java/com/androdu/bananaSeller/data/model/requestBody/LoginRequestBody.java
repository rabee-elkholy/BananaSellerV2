package com.androdu.bananaSeller.data.model.requestBody;

public class LoginRequestBody {
    private String mobile, password, FCM, lang;

    public LoginRequestBody(String mobile, String password, String FCM, String lang) {
        this.mobile = mobile;
        this.password = password;
        this.FCM = FCM;
        this.lang = lang;
    }
}
