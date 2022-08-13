
package com.androdu.bananaSeller.data.model.response.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderProduct {

    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("product")
    @Expose
    private OrderProductDetails orderProductDetails;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("unit")
    @Expose
    private String unit;

    private Integer count;
    private String unitStr;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderProductDetails getOrderProductDetails() {
        return orderProductDetails;
    }

    public void setOrderProductDetails(OrderProductDetails orderProductDetails) {
        this.orderProductDetails = orderProductDetails;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getUnitStr() {
        return unitStr;
    }

    public void setUnitStr(String unitStr) {
        this.unitStr = unitStr;
    }
}
