package com.attribe.delivo.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.MapboxAccountManager;

import static com.attribe.delivo.app.R.id.map;

public class MapBox extends AppCompatActivity  {
    private MapView mapView;
    private static String mapbox_accestoken="pk.eyJ1IjoidXphaXIiLCJhIjoiY2lzNXJtdWhiMDAxeTJ1bmYybXFuMW8yOSJ9.Gb6cKFUhkRjHQMMvbU3W-w";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapboxAccountManager.start(this, mapbox_accestoken);
        setContentView(R.layout.activity_map_box);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
              LatLng latLng=new  LatLng(24.857697, 67.045983);

                mapboxMap.addMarker(new MarkerOptions()
                        .position(latLng)
//
                        .title("Hello World!")
                        .snippet("Welcome to my marker."));
               // map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
               // mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(latLng.));

//                Marker marker = mapView.addMarker(new MarkerOptions()
//                        .position(new LatLng(37.7750, 122.4183))
//                        .title("San Francisco")
//                        .snippet("Population: 776733"));

            }
        });

    }


    // Add the mapView lifecycle to the activity's lifecycle methods
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}
