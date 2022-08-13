package com.androdu.bananaSeller.data.model.requestBody;

public class AddProductToCartRequestBody {
    String name, unit;
    int amount;

    public AddProductToCartRequestBody(String name, String unit, int amount) {
        this.name = name;
        this.unit = unit;
        this.amount = amount;
    }
}
