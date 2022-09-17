package com.androdu.bananaSeller.data.model.requestBody;

import java.util.List;

public class SignUpRequestBody {
    private String name, mobile, password, comfirmPassword, FCM, lang;
    private List<String> category;

    public SignUpRequestBody(String name, String mobile, String password, String comfirmPassword, String FCM, String lang, List<String> category) {
        this.name = name;
        this.mobile = mobile;
        this.password = password;
        this.comfirmPassword = comfirmPassword;
        this.FCM = FCM;
        this.lang = lang;
        this.category = category;
    }
}
