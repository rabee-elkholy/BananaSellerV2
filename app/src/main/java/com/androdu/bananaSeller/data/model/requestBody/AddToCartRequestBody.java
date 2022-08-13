package com.androdu.bananaSeller.data.model.requestBody;

public class AddToCartRequestBody {
    String productId;
    String unit;
    int amount;
    boolean newProduct;

    public AddToCartRequestBody(String productId, String unit, int amount, boolean newProduct) {
        this.productId = productId;
        this.unit = unit;
        this.amount = amount;
        this.newProduct = newProduct;
    }
}
