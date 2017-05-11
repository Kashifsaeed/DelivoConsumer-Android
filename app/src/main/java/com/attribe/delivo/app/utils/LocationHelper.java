package com.attribe.delivo.app.utils;

import android.content.Context;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Sabih Ahmed on 13-Jul-16.
 */
public class LocationHelper {


    private static LocationRequest locationRequest;
    private static FusedLocationProviderApi fusedLocationProviderApi;
    private GoogleApiClient mGoogleApiClient;

    public static LocationRequest getLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(30000);
        locationRequest.setFastestInterval(10000);


        return locationRequest;
    }

    public static FusedLocationProviderApi getFusedLocationApi() {
        fusedLocationProviderApi = LocationServices.FusedLocationApi;

    return fusedLocationProviderApi;
    }



}
