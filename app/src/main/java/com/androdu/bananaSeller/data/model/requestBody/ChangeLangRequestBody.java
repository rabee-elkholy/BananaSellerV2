package com.androdu.bananaSeller.data.model.requestBody;

public class ChangeLangRequestBody {

    String FCM, lang;

    public ChangeLangRequestBody(String FCM, String lang) {
        this.FCM = FCM;
        this.lang = lang;
    }
}
