package com.androdu.bananaSeller.data.model.requestBody;

import java.util.List;

public class ProductsRequestBody {
    List<String> filter;

    public ProductsRequestBody(List<String> filter) {
        this.filter = filter;
    }
}
