package com.androdu.bananaSeller.data.model.requestBody;

public class PullBalanceRequestBody {
    int amount;
    String fullName, banckAccount, IBAN, banckName;

    public PullBalanceRequestBody(int amount, String fullName, String banckAccount, String IBAN, String banckName) {
        this.amount = amount;
        this.fullName = fullName;
        this.banckAccount = banckAccount;
        this.IBAN = IBAN;
        this.banckName = banckName;
    }
}
