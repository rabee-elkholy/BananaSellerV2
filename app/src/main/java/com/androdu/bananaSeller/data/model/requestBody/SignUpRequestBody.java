package com.androdu.bananaSeller.data.model.requestBody;

import java.util.List;

public class SignUpRequestBody {
    private String name, email, mobile, code, password, comfirmPassword, FCM, lang;
    private List<String> category;

    public SignUpRequestBody(String name, String email, String mobile, String code, String password, String comfirmPassword, String FCM, String lang, List<String> category) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.code = code;
        this.password = password;
        this.comfirmPassword = comfirmPassword;
        this.FCM = FCM;
        this.lang = lang;
        this.category = category;
    }
}
