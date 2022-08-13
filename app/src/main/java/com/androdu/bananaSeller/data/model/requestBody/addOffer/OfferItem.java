package com.androdu.bananaSeller.data.model.requestBody.addOffer;

public class OfferItem {
    private String cartItem;
    private Integer amount;

    public OfferItem(String cartItem, Integer amount) {
        this.cartItem = cartItem;
        this.amount = amount;
    }
}
