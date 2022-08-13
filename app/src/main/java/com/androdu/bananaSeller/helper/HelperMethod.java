package com.androdu.bananaSeller.helper;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.view.activity.SplashActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class HelperMethod {

    private static SweetAlertDialog pDialog;

    public static void replaceFragment(FragmentManager fragmentManager, int id, Fragment fragment, boolean backStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (backStack) {
            transaction.addToBackStack(null);
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        }
        transaction.replace(id, fragment);
        transaction.commit();
    }


    public static String loadJSONFromAsset(Activity activity) {
        String json = null;
        try {
            InputStream is = activity.getAssets().open("countries_data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public static void showProgressDialog(Activity activity) {
        try {
            pDialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(activity.getResources().getColor(R.color.colorPrimary));
            pDialog.setContentText(activity.getString(R.string.wait));
            pDialog.setCancelable(false);
            pDialog.show();

        } catch (Exception e) {

        }
    }

    public static void showSuccessDialog(final Activity activity, String message) {
        try {

            SweetAlertDialog dialog = new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE);
            dialog.setContentText(message);
            dialog.setConfirmText(activity.getString(R.string.ok));
            dialog.show();

        } catch (Exception e) {

        }
    }

    public static void disableView(View... views) {
        for (View v : views) {
            v.setEnabled(false);
        }
    }

    public static void enableView(View... views) {
        for (View v : views) {
            v.setEnabled(true);
        }
    }

    public static void showView(View... views) {
        for (View v : views) {
            v.setVisibility(View.VISIBLE);
        }
    }

    public static void hideView(View... views) {
        for (View v : views) {
            v.setVisibility(View.GONE);
        }
    }


//
//    public static void showLanguageDialog(final Activity activity) {
//        try {
//
//            new SweetAlertDialog(activity, SweetAlertDialog.NORMAL_TYPE)
//                    .setContentText(activity.getResources().getString(R.string.choose_the_language))
//                    .setNeutralButton(R.string.english, sweetAlertDialog -> setLanguageEnglish(activity))
//                    .setConfirmButton(R.string.arabic, sweetAlertDialog -> setLanguageArabic(activity))
//                    .show();
//
//        } catch (Exception e) {
//
//        }
//    }

    private static void setLanguageArabic(Activity activity) {
        Intent intent = new Intent(activity, SplashActivity.class);
        LanguageManager.setNewLocale(activity, LanguageManager.LANGUAGE_KEY_ARABIC);
        activity.startActivity(intent);
        activity.finish();
    }

    private static void setLanguageEnglish(Activity activity) {
        Intent intent = new Intent(activity, SplashActivity.class);
        LanguageManager.setNewLocale(activity, LanguageManager.LANGUAGE_KEY_ENGLISH);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void showSuccessDialogCloseActivity(final Activity activity, String message) {
        try {

            SweetAlertDialog dialog = new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE);
            dialog.setContentText(message);
            dialog.setConfirmText(activity.getString(R.string.ok));
            dialog.setConfirmClickListener(sweetAlertDialog -> activity.finish());
            dialog.show();

        } catch (Exception e) {

        }
    }

    public static void showSuccessDialogCloseFragment(final Activity activity, String message) {
        try {
            SweetAlertDialog dialog = new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE);
            dialog.setContentText(message);
            dialog.setConfirmText(activity.getString(R.string.ok));
            dialog.setConfirmClickListener(sweetAlertDialog -> {
                activity.onBackPressed();
                sweetAlertDialog.dismissWithAnimation();
            });
            dialog.setCancelable(false);
            dialog.show();

        } catch (Exception e) {

        }
    }

    public static void showErrorDialog(Activity activity, String message) {
        try {

            SweetAlertDialog dialog = new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE);
            dialog.setContentText(message);
            dialog.setConfirmText(activity.getString(R.string.ok));
            dialog.show();

        } catch (Exception e) {

        }
    }

    public static void showErrorDialog(Activity activity, String title, String message) {
        try {

            SweetAlertDialog dialog = new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE);
            dialog.setTitleText(title);
            dialog.setContentText(message);
            dialog.setConfirmText(activity.getString(R.string.ok));

            dialog.show();

        } catch (Exception e) {

        }
    }

    public static void showErrorDialogCloseActivity(final Activity activity, String message) {
        try {

            SweetAlertDialog dialog = new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE);
            dialog.setContentText(message);
            dialog.setConfirmText(activity.getString(R.string.ok));
            dialog.setConfirmClickListener(sweetAlertDialog -> activity.finish());
            dialog.setCancelable(false);
            dialog.show();

        } catch (Exception e) {

        }
    }

    public static void showErrorDialogCloseFragment(final Activity activity, String title, String message) {
        try {

            final SweetAlertDialog dialog = new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE);
            dialog.setTitleText(title);
            dialog.setContentText(message);
            dialog.setConfirmText(activity.getString(R.string.ok));
            dialog.setConfirmClickListener(sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                activity.onBackPressed();
            });
            dialog.setCancelable(false);
            dialog.show();

        } catch (Exception e) {

        }
    }


    public static void dismissProgressDialog() {
        try {

            pDialog.dismiss();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void showDialogList(Activity activity, String title, String[] list, DialogInterface.OnClickListener onClickListener) {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);

        builder.setItems(list, onClickListener);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


//    public static boolean loginValidation(Activity activity, TextInputLayout... textInputLayouts) {
//
//        if (textInputLayouts[0].getEditText().getText().toString().isEmpty()
//                || !textInputLayouts[0].getEditText().getText().toString().contains("@")) {
//            textInputLayouts[0].setHelperTextEnabled(true);
//            textInputLayouts[0].setHelperText(activity.getString(R.string.make_sure_you_enter_your_email));
//            textInputLayouts[0].requestFocus();
//            return false;
//        }
//        textInputLayouts[0].setHelperTextEnabled(false);
//
//        if (textInputLayouts[1].getEditText().getText().toString().isEmpty()
//                || textInputLayouts[1].getEditText().getText().toString().length() < 6) {
//            textInputLayouts[1].setHelperText(activity.getString(R.string.make_sure_you_enter_your_password));
//            textInputLayouts[1].requestFocus();
//            return false;
//        }
//        textInputLayouts[1].setHelperTextEnabled(false);
//
//        return true;
//    }


    public static void disappearKeypad(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static RequestBody convertToRequestBody(String part) {
        try {
            if (!part.equals("")) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), part);
                return requestBody;
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.d("error", "convertToRequestBody: ");

            return null;
        }
    }

    public static RequestBody convertToRequestBody2(byte part) {
        byte[] bytes = {part};
        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), bytes);
            return requestBody;

        } catch (Exception e) {
            Log.d("error", "convertToRequestBody: ");

            return null;
        }
    }

    public static String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }


    public static MultipartBody.Part convertFileToMultipart(String pathImageFile, String Key) {
        if (pathImageFile != null) {
            File file = new File(pathImageFile);
            RequestBody reqFileselect = RequestBody.create(MediaType.parse("image/jpeg"), file);
            MultipartBody.Part Imagebody = MultipartBody.Part.createFormData(Key, "image.jpeg", reqFileselect);
            return Imagebody;
        } else {
            Log.d("error", "convertFileToMultipart: ");
            return null;
        }
    }

    public static MultipartBody.Part convertFileToMultipartVideo(String pathImageFile, String Key) {
        if (pathImageFile != null) {
            File file = new File(pathImageFile);
            RequestBody reqFileselect = RequestBody.create(MediaType.parse("video/mp4"), file);
            MultipartBody.Part Imagebody = MultipartBody.Part.createFormData(Key, "video.mp4", reqFileselect);
            return Imagebody;
        } else {
            Log.d("error", "convertFileToMultipart: ");
            return null;
        }
    }


    public static void showSettingsDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.dialog_permission_title));
        builder.setMessage(activity.getString(R.string.dialog_permission_message));
        builder.setPositiveButton(activity.getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings(activity);
            }
        });
        builder.setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    // navigating user to app settings
    public static void openSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, 101);
    }


    ////////////////////////////////////////////////////


    /**
     * Method for return file path of Gallery image
     *
     * @param context
     * @param uri
     * @return path of the selected image file from gallery
     */

    public static String getPath(final Context context, final Uri uri) {
        //check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }

            //DownloadsProvider
//            else if (isDownloadsDocument(uri)) {
//
//                final String id = DocumentsContract.getDocumentId(uri);
//                final Uri contentUri = ContentUris.withAppendedId(
//                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
//
//                return getDataColumn(context, contentUri, null, null);
//            }

            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static String getFileName(Activity activity, Uri uri) {
        String displayName = null;
        File myFile = new File(uri.toString());
        if (uri.toString().startsWith("content://")) {
            Cursor cursor = null;
            try {
                cursor = activity.getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        } else if (uri.toString().startsWith("file://")) {
            displayName = myFile.getName();
        }
        return displayName;
    }
}
