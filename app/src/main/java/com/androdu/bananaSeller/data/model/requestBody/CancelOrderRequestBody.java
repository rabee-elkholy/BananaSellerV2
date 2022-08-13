package com.androdu.bananaSeller.data.model.requestBody;

public class CancelOrderRequestBody {
    String orderId;

    public CancelOrderRequestBody(String orderId) {
        this.orderId = orderId;
    }
}
