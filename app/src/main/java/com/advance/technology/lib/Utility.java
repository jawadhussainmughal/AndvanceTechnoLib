package com.advance.technology.lib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Calendar;

/**
 * Created by 6520 on 2/2/2016.
 */
public class Utility {
    static String TAG = "urdupoetry";

    public static void logCatMsg(String msg)
    {
        Log.d(TAG, msg);
    }
    public static void Toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static String getCurrentTime() {
        String AM_PM;
        Calendar calendar = Calendar.getInstance();
        AM_PM = calendar.get(Calendar.AM_PM)==0?"am":"pm";
        return calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE)+" "+AM_PM;
    }

    public static String getTime(String time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(time));
        String AM_PM;
        AM_PM = calendar.get(Calendar.AM_PM)==0?"am":"pm";
        return calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE)+" "+AM_PM;
    }


    public static void showChangedToast(Context context, String message)
    {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    public  static void generateHashKey(Context context){
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA1");
                md.update(signature.toByteArray());
                Utility.logCatMsg("HashKey: " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
    // convert from byte array to bitmap
    public static Bitmap getPhoto(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    public static String getCurrentDate() {
        final Calendar c = Calendar.getInstance();
      int  mYear = c.get(Calendar.YEAR);
      int  mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        return mYear + "-" + (mMonth + 1) + "-" + mDay;
    }
    public static Bitmap getImage(String imgName){
        String dirName = "ZEDDelivery";
        File direct = new File(Environment.getExternalStorageDirectory(),dirName);
        if (direct.exists()) {
            File file = new File(new File("/sdcard/" + dirName + "/"), imgName);
            Utility.logCatMsg("File Path "+file.getPath());
            // Get the dimensions of the bitmap
            if(file.exists()) {
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);
                int photoW = bmOptions.outWidth;
                int photoH = bmOptions.outHeight;

                // Determine how much to scale down the image
                int scaleFactor = Math.min(photoW / 150, photoH / 150);

                // Decode the image file into a Bitmap sized to fill the View
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inSampleSize = scaleFactor;
                bmOptions.inPurgeable = true;

                return BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);
            }else {
                return null;
            }
        }
        else {
            Utility.logCatMsg("Dir not found..");
        }
        return null;
    }
    public  static void HideKeyBoard(View v,Context context){
        if (v != null) {
            InputMethodManager inm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        }
    }
    public  static void ShowKeyBoard(View v,Context context){
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }
}
