package com.androdu.bananaSeller.data.model.requestBody;

import okhttp3.MultipartBody;

public class ComplaintRequestBody {
    String orderId,reason,demands;
    MultipartBody.Part[] parts;

    public ComplaintRequestBody(String orderId, String reason, String demands, MultipartBody.Part[] parts) {
        this.orderId = orderId;
        this.reason = reason;
        this.demands = demands;
        this.parts = parts;
    }
}
