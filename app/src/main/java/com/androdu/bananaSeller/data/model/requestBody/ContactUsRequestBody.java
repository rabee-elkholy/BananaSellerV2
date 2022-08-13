package com.androdu.bananaSeller.data.model.requestBody;

public class ContactUsRequestBody {
    String name, email, message;

    public ContactUsRequestBody(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }
}
