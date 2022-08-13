package com.androdu.bananaSeller.data.model.requestBody;

public class RemoveFromCartRequestBody {
    String cartItemId;

    public RemoveFromCartRequestBody(String cartItemId) {
        this.cartItemId = cartItemId;
    }
}
