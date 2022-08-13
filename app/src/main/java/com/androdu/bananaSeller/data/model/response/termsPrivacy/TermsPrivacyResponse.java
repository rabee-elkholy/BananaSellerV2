
package com.androdu.bananaSeller.data.model.response.termsPrivacy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TermsPrivacyResponse {

    @SerializedName("state")
    @Expose
    private Integer state;
    @SerializedName("data")
    @Expose
    private TermsPrivacy termsPrivacy;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public TermsPrivacy getTermsPrivacy() {
        return termsPrivacy;
    }

    public void setTermsPrivacy(TermsPrivacy termsPrivacy) {
        this.termsPrivacy = termsPrivacy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
