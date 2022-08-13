package com.androdu.bananaSeller.data.model.requestBody;

public class AddRemoveFromFavRequestBody {
    String productId, listId;

    public AddRemoveFromFavRequestBody(String productId, String listId) {
        this.productId = productId;
        this.listId = listId;
    }
}
