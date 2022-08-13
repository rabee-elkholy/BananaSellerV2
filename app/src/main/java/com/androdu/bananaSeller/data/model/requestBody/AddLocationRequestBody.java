package com.androdu.bananaSeller.data.model.requestBody;

public class AddLocationRequestBody {
    double lat1, long1;
    String stringAdress, name, mobile;

    public AddLocationRequestBody(double lat1, double long1, String stringAdress, String name, String mobile) {
        this.lat1 = lat1;
        this.long1 = long1;
        this.stringAdress = stringAdress;
        this.name = name;
        this.mobile = mobile;
    }
}
