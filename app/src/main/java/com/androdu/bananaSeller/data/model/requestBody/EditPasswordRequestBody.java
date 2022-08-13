package com.androdu.bananaSeller.data.model.requestBody;

public class EditPasswordRequestBody {
    String oldPassword, password, comfirmPassword;
    boolean logout;

    public EditPasswordRequestBody(String oldPassword, String password, String comfirmPassword, boolean logout) {
        this.oldPassword = oldPassword;
        this.password = password;
        this.comfirmPassword = comfirmPassword;
        this.logout = logout;
    }
}
