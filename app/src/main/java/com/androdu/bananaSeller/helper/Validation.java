package com.androdu.bananaSeller.helper;

import android.app.Activity;
import android.widget.EditText;

import com.androdu.bananaSeller.R;
import com.google.android.material.textfield.TextInputLayout;

import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;

public class Validation {


    public static boolean signUp(Activity activity, boolean isValidNum, boolean isTermsChecked, TextInputLayout... layouts) {
        if (layouts[0].getEditText().getText().toString().trim().isEmpty()) {
            layouts[0].setError(activity.getString(R.string.enter_name));
            layouts[0].requestFocus();
            return false;
        }
        layouts[0].setErrorEnabled(false);

        if (layouts[1].getEditText().getText().toString().trim().isEmpty()) {
            layouts[1].setError(activity.getString(R.string.enter_email));
            layouts[1].requestFocus();
            return false;
        }
        layouts[1].setErrorEnabled(false);


        if (layouts[2].getEditText().getText().toString().trim().isEmpty()) {
            layouts[2].setError(" ");
            layouts[2].requestFocus();
            return false;
        }
        layouts[2].setErrorEnabled(false);

        if (layouts[3].getEditText().getText().toString().trim().isEmpty()) {
            layouts[3].setError(activity.getString(R.string.enter_phone));
            layouts[3].requestFocus();
            return false;
        }
        if (!isValidNum) {
            layouts[3].setError(activity.getString(R.string.invalid_phone_number));
            layouts[3].requestFocus();
            return false;
        }
        layouts[3].setErrorEnabled(false);

        if (layouts[4].getEditText().getText().toString().trim().isEmpty()) {
            layouts[4].setError(activity.getString(R.string.enter_password));
            layouts[4].requestFocus();
            return false;
        }
        if (layouts[4].getEditText().getText().toString().trim().length() < 8) {
            layouts[4].setError(activity.getString(R.string.password_must_greater_than_7));
            layouts[4].requestFocus();
            return false;
        }
        layouts[4].setErrorEnabled(false);

        if (layouts[5].getEditText().getText().toString().trim().isEmpty()) {
            layouts[5].setError(activity.getString(R.string.enter_confirm_password));
            layouts[5].requestFocus();
            return false;
        }
        if (!layouts[5].getEditText().getText().toString().trim()
                .equals(layouts[4].getEditText().getText().toString().trim())) {
            layouts[5].setError(activity.getString(R.string.password_does_not_match));
            layouts[5].requestFocus();
            return false;
        }
        layouts[5].setErrorEnabled(false);

        if (!isTermsChecked) {
            showErrorDialog(activity, activity.getString(R.string.terms_error));
            return false;
        }

        return true;
    }

    public static boolean login(Activity activity, TextInputLayout... layouts) {
        if (layouts[0].getEditText().getText().toString().trim().isEmpty()) {
            layouts[0].setError(activity.getString(R.string.enter_phone));
            layouts[0].requestFocus();
            return false;
        }
        layouts[0].setErrorEnabled(false);

        if (layouts[1].getEditText().getText().toString().trim().isEmpty()) {
            layouts[1].setError(activity.getString(R.string.enter_password));
            layouts[1].requestFocus();
            return false;
        }
        layouts[1].setErrorEnabled(false);


        return true;
    }

    public static boolean addOffer(Activity activity, boolean isEmptyOffer, TextInputLayout... layouts) {
        if (isEmptyOffer) {
            showErrorDialog(activity, activity.getString(R.string.empty_offer));
            return false;
        }

        if (layouts[0].getEditText().getText().toString().trim().isEmpty()) {
            layouts[0].setError(activity.getString(R.string.enter_total_price));
            layouts[0].requestFocus();
            return false;
        }
        if (Integer.parseInt(layouts[0].getEditText().getText().toString()) < 15) {
            layouts[0].setError(activity.getString(R.string.must_be_greater_than_15));
            layouts[0].requestFocus();
            return false;
        }
        layouts[0].setErrorEnabled(false);

        return true;
    }

    public static boolean newPassword(Activity activity, EditText... editTexts) {

        if (editTexts[0].getText().toString().trim().isEmpty()) {
            editTexts[0].setError(activity.getString(R.string.enter_password));
            editTexts[0].requestFocus();
            return false;
        }
        if (editTexts[0].getText().toString().trim().length() < 8) {
            editTexts[0].setError(activity.getString(R.string.password_must_greater_than_7));
            editTexts[0].requestFocus();
            return false;
        }
        editTexts[0].setError(null);

        if (editTexts[1].getText().toString().trim().isEmpty()) {
            editTexts[1].setError(activity.getString(R.string.enter_confirm_password));
            editTexts[1].requestFocus();
            return false;
        }
        if (!editTexts[1].getText().toString().trim()
                .equals(editTexts[0].getText().toString().trim())) {
            editTexts[1].setError(activity.getString(R.string.password_does_not_match));
            editTexts[1].requestFocus();
            return false;
        }
        editTexts[1].setError(null);

        return true;
    }

    public static boolean editMobileNumber(Activity activity, boolean validOldNum, boolean validNewNum, EditText... editTexts) {
        if (editTexts[0].getText().toString().trim().isEmpty()) {
            editTexts[0].setError(activity.getString(R.string.enter_phone));
            editTexts[0].requestFocus();
            return false;
        }
        if (!validOldNum) {
            editTexts[0].setError(activity.getString(R.string.invalid_phone_number));
            editTexts[0].requestFocus();
            return false;
        }
        editTexts[0].setError(null);

        if (editTexts[1].getText().toString().trim().isEmpty()) {
            editTexts[1].setError(activity.getString(R.string.enter_phone));
            editTexts[1].requestFocus();
            return false;
        }
        if (!validNewNum) {
            editTexts[1].setError(activity.getString(R.string.invalid_phone_number));
            editTexts[1].requestFocus();
            return false;
        }
        editTexts[1].setError(null);


        return true;
    }

    public static boolean contactUs(Activity activity, EditText... editTexts) {
        if (editTexts[0].getText().toString().trim().isEmpty()) {
            editTexts[0].setError(activity.getString(R.string.enter_name));
            editTexts[0].requestFocus();
            return false;
        }
        editTexts[0].setError(null);

        if (editTexts[1].getText().toString().trim().isEmpty()) {
            editTexts[1].setError(activity.getString(R.string.enter_email));
            editTexts[1].requestFocus();
            return false;
        }
        editTexts[1].setError(null);

        if (editTexts[2].getText().toString().trim().isEmpty()) {
            editTexts[2].setError(activity.getString(R.string.enter_your_message));
            editTexts[2].requestFocus();
            return false;
        }
        editTexts[2].setError(null);


        return true;
    }

    public static boolean sendComplaint(Activity activity, int imgSize, EditText... editTexts) {
        if (editTexts[0].getText().toString().trim().isEmpty()) {
            editTexts[0].setError(activity.getString(R.string.enter_order_id));
            editTexts[0].requestFocus();
            return false;
        }
        editTexts[0].setError(null);

        if (editTexts[1].getText().toString().trim().isEmpty()) {
            editTexts[1].setError(activity.getString(R.string.enter_cause_of_conflict));
            editTexts[1].requestFocus();
            return false;
        }
        editTexts[1].setError(null);

        if (editTexts[2].getText().toString().trim().isEmpty()) {
            editTexts[2].setError(activity.getString(R.string.enter_your_requests));
            editTexts[2].requestFocus();
            return false;
        }
        editTexts[2].setError(null);

        if (imgSize == 0) {
            showErrorDialog(activity, activity.getString(R.string.add_your_evidence));
            return false;
        }


        return true;
    }

    public static boolean sellerInfo(Activity activity, int imgSize, String startTime, String endTime, TextInputLayout... editTexts) {
        if (imgSize == 0) {
            showErrorDialog(activity, activity.getString(R.string.add_your_certificate));
            return false;
        }

        if (editTexts[0].getEditText().getText().toString().trim().isEmpty()) {
            editTexts[0].setError(activity.getString(R.string.enter_date));
            editTexts[0].requestFocus();
            return false;
        }
        editTexts[0].setError(null);

        if (editTexts[1].getEditText().getText().toString().trim().isEmpty()) {
            editTexts[1].setError(activity.getString(R.string.enter_address_details));
            editTexts[1].requestFocus();
            return false;
        }
        editTexts[1].setError(null);

        if (editTexts[2].getEditText().getText().toString().trim().isEmpty()) {
            editTexts[2].setError(activity.getString(R.string.add_location));
            editTexts[2].requestFocus();
            return false;
        }
        editTexts[2].setError(null);

        if (startTime.isEmpty() || endTime.isEmpty()) {
            editTexts[3].setError(activity.getString(R.string.enter_time));
            editTexts[3].requestFocus();
            return false;
        }
        editTexts[3].setError(null);
        return true;
    }

    public static boolean editPassword(Activity activity, String oldPassword, EditText... editTexts) {
        if (editTexts[0].getText().toString().trim().isEmpty()) {
            editTexts[0].setError(activity.getString(R.string.enter_password));
            editTexts[0].requestFocus();
            return false;
        }
        if (!editTexts[0].getText().toString().trim().equals(oldPassword)) {
            editTexts[0].setError(activity.getString(R.string.wrong_password));
            editTexts[0].requestFocus();
            return false;
        }
        editTexts[0].setError(null);

        if (editTexts[1].getText().toString().trim().isEmpty()) {
            editTexts[1].setError(activity.getString(R.string.enter_password));
            editTexts[1].requestFocus();
            return false;
        }
        if (editTexts[1].getText().toString().trim().length() < 8) {
            editTexts[1].setError(activity.getString(R.string.password_must_greater_than_7));
            editTexts[1].requestFocus();
            return false;
        }
        editTexts[1].setError(null);

        if (editTexts[2].getText().toString().trim().isEmpty()) {
            editTexts[2].setError(activity.getString(R.string.enter_confirm_password));
            editTexts[2].requestFocus();
            return false;
        }
        if (!editTexts[2].getText().toString().trim()
                .equals(editTexts[1].getText().toString().trim())) {
            editTexts[2].setError(activity.getString(R.string.password_does_not_match));
            editTexts[2].requestFocus();
            return false;
        }
        editTexts[2].setError(null);

        return true;
    }

    public static boolean addLocation(Activity activity, EditText... editTexts) {
        if (editTexts[0].getText().toString().trim().isEmpty()) {
            editTexts[0].setError(activity.getString(R.string.enter_address_details));
            editTexts[0].requestFocus();
            return false;
        }
        editTexts[0].setError(null);

        if (editTexts[1].getText().toString().trim().isEmpty()) {
            editTexts[1].setError(activity.getString(R.string.enter_address_name));
            editTexts[1].requestFocus();
            return false;
        }
        editTexts[1].setError(null);

        if (editTexts[2].getText().toString().trim().isEmpty()) {
            editTexts[2].setError(activity.getString(R.string.enter_phone));
            editTexts[2].requestFocus();
            return false;
        }
        editTexts[2].setError(null);


        return true;
    }

    public static boolean paymentData(Activity activity, EditText... editTexts) {
        if (editTexts[0].getText().toString().trim().isEmpty()) {
            editTexts[0].setError(activity.getString(R.string.enter_name));
            editTexts[0].requestFocus();
            return false;
        }
        editTexts[0].setError(null);

        if (editTexts[1].getText().toString().trim().isEmpty()) {
            editTexts[1].setError(activity.getString(R.string.enter_phone));
            editTexts[1].requestFocus();
            return false;
        }
        editTexts[1].setError(null);

        if (editTexts[2].getText().toString().trim().isEmpty()) {
            editTexts[2].setError(activity.getString(R.string.enter_address_details));
            editTexts[2].requestFocus();
            return false;
        }
        editTexts[2].setError(null);

        if (editTexts[3].getText().toString().trim().isEmpty()) {
            editTexts[3].setError(activity.getString(R.string.enter_date_time));
            editTexts[3].requestFocus();
            return false;
        }
        editTexts[3].setError(null);


        return true;
    }

    public static boolean pullBalance(Activity activity, EditText... editTexts) {
        if (editTexts[0].getText().toString().trim().isEmpty()) {
            editTexts[0].setError(activity.getString(R.string.enter_name));
            editTexts[0].requestFocus();
            return false;
        }
        editTexts[0].setError(null);

        if (editTexts[1].getText().toString().trim().isEmpty()) {
            editTexts[1].setError(activity.getString(R.string.enter_account_number));
            editTexts[1].requestFocus();
            return false;
        }
        editTexts[1].setError(null);

        if (editTexts[2].getText().toString().trim().isEmpty()) {
            editTexts[2].setError(activity.getString(R.string.enter_iban));
            editTexts[2].requestFocus();
            return false;
        }
        editTexts[2].setError(null);

        if (editTexts[3].getText().toString().trim().isEmpty()) {
            editTexts[3].setError(activity.getString(R.string.enter_bank_name));
            editTexts[3].requestFocus();
            return false;
        }
        editTexts[3].setError(null);

        if (editTexts[4].getText().toString().trim().isEmpty()) {
            editTexts[4].setError(activity.getString(R.string.enter_balance));
            editTexts[4].requestFocus();
            return false;
        }
        if (Integer.parseInt(editTexts[4].getText().toString().trim()) < 50) {
            editTexts[4].setError(activity.getString(R.string.the_minimum_balance_50));
            editTexts[4].requestFocus();
            return false;
        }

        editTexts[4].setError(null);


        return true;
    }
}
