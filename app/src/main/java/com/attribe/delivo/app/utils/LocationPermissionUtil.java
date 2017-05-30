package com.attribe.delivo.app.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.attribe.delivo.app.Extras.AppConstants;
import com.attribe.delivo.app.interfaces.OnLocationPermmisionListner;

/**
 * Author: Uzair Qureshi
 * Date:  5/29/17.
 * Description:
 */

public class LocationPermissionUtil {
    public static void askForPermission( Context context, OnLocationPermmisionListner locationPermmisionListner) {
        if (ContextCompat.checkSelfPermission(context, AppConstants.FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, AppConstants.FINE_LOCATION))
            {
                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                locationPermmisionListner.onPermissionBeforeDenied();

            } else
            {

                locationPermmisionListner.onPermisionRequested();
            }
        } else
        {

            Toast.makeText(context, "" + "Location Permission"+ " is already granted.", Toast.LENGTH_SHORT).show();
            locationPermmisionListner.onPermissionAlreadyGranted();

        }
    }
    public static  boolean checkPermission(Context context)
    {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED)
        {

            return true;

        }
        else
        {

            return false;

        }
    }
    public static void showdialougeRequestpermission(Context context,int request_code)
    {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            final String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions((Activity) context, permissions, request_code);
        }
    }
}
