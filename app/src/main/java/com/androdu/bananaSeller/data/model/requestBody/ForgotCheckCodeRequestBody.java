package com.androdu.bananaSeller.data.model.requestBody;

public class ForgotCheckCodeRequestBody {
    String mobile, VerCode;

    public ForgotCheckCodeRequestBody(String mobile, String VerCode) {
        this.mobile = mobile;
        this.VerCode = VerCode;
    }
}
