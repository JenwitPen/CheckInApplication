package com.checkin.maceducation.checkinapplication.Utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.ContextThemeWrapper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jenwit on 16/9/2558.
 */
public class Utility {
    ProgressDialog progressDialog ;
    Context context;
    public Utility(Context context)
    {
        this.context=context;
    }
    public void showDialogProgres(String Message)
    {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                progressDialog = new ProgressDialog(new ContextThemeWrapper(context, android.R.style.Theme_Holo_Light_Dialog));
            } else {
                progressDialog = new ProgressDialog(context);
            }

            progressDialog.setMessage(Message);
            progressDialog.show();


    }
    public void CloseDialogProgres()
    {
   progressDialog.dismiss();
    }
    public  boolean isNetworkAvailable () {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
    public  Boolean isGpsAvailabel()
    {
        LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        return !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
    public void setMobileDataEnabled(Context context, boolean enabled) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        final ConnectivityManager conman = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final Class conmanClass = Class.forName(conman.getClass().getName());
        final Field connectivityManagerField = conmanClass.getDeclaredField("mService");
        connectivityManagerField.setAccessible(true);
        final Object connectivityManager = connectivityManagerField.get(conman);
        final Class connectivityManagerClass =  Class.forName(connectivityManager.getClass().getName());
        final Method setMobileDataEnabledMethod = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
        setMobileDataEnabledMethod.setAccessible(true);

        setMobileDataEnabledMethod.invoke(connectivityManager, enabled);
    }
    public Bitmap CropCenterImage(Bitmap bm,int IMAGE_SIZE) {



        Bitmap croppedBitmap = null;
        float scale_factor;
        try {
            boolean landscape = bm.getWidth() > bm.getHeight();
            Matrix matrix = new Matrix();

            if (landscape) {
                scale_factor = (float) IMAGE_SIZE / bm.getHeight();
            } else {
                scale_factor = (float) IMAGE_SIZE / bm.getWidth();

                matrix.postScale(scale_factor, scale_factor);
            }


            if (landscape) {
                int start = (bm.getWidth() - bm.getHeight()) / 2;
                croppedBitmap = Bitmap.createBitmap(bm, start, 0, bm.getHeight(), bm.getHeight(), matrix, true);
            } else {
                int start = (bm.getHeight() - bm.getWidth()) / 2;
                croppedBitmap = Bitmap.createBitmap(bm, 0, start, bm.getWidth(), bm.getWidth(), matrix, true);
            }

        } catch (Exception ex) {
            croppedBitmap = null;

        }
        return croppedBitmap;
    }
}
