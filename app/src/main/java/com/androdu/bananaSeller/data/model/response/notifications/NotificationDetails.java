
package com.androdu.bananaSeller.data.model.response.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationDetails {

    @SerializedName("title_ar")
    @Expose
    private String titleAr;
    @SerializedName("body_ar")
    @Expose
    private String bodyAr;
    @SerializedName("title_en")
    @Expose
    private String titleEn;
    @SerializedName("body_en")
    @Expose
    private String bodyEn;
    @SerializedName("title_urdu")
    @Expose
    private String titleUr;
    @SerializedName("body_urdu")
    @Expose
    private String bodyUr;

    public String getTitleAr() {
        return titleAr;
    }

    public void setTitleAr(String titleAr) {
        this.titleAr = titleAr;
    }

    public String getBodyAr() {
        return bodyAr;
    }

    public void setBodyAr(String bodyAr) {
        this.bodyAr = bodyAr;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getBodyEn() {
        return bodyEn;
    }

    public void setBodyEn(String bodyEn) {
        this.bodyEn = bodyEn;
    }

    public String getTitleUr() {
        return titleUr;
    }

    public void setTitleUr(String titleUr) {
        this.titleUr = titleUr;
    }

    public String getBodyUr() {
        return bodyUr;
    }

    public void setBodyUr(String bodyUr) {
        this.bodyUr = bodyUr;
    }
}
