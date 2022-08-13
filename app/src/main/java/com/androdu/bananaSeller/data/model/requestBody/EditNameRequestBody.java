package com.androdu.bananaSeller.data.model.requestBody;

public class EditNameRequestBody {
    String name;
    int imagePath;

    public EditNameRequestBody(String name, int imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }
}
