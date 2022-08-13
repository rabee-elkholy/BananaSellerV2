package com.androdu.bananaSeller.data.model.requestBody.addOffer;

import java.util.List;

public class AddOfferRequestBody {
    private String orderId;
    private Double price;
    private boolean banana_delivery;
    private List<OfferItem> amountArray;

    public AddOfferRequestBody(String orderId, Double price, boolean banana_delivery, List<OfferItem> amountArray) {
        this.orderId = orderId;
        this.price = price;
        this.banana_delivery = banana_delivery;
        this.amountArray = amountArray;
    }
}
