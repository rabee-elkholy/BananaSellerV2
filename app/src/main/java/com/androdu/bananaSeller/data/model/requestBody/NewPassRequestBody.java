package com.androdu.bananaSeller.data.model.requestBody;

public class NewPassRequestBody {
    String mobile,VerCode,password,comfirmPassword;

    public NewPassRequestBody(String mobile, String verCode, String password, String comfirmPassword) {
        this.mobile = mobile;
        VerCode = verCode;
        this.password = password;
        this.comfirmPassword = comfirmPassword;
    }
}
