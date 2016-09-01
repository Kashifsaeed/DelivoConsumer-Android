package com.attribe.delivo.app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.*;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.SimplePanelSlideListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.sothree.slidinguppanel.library.R.styleable.SlidingUpPanelLayout;

public class CustomPickLocation extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "DemoActivity";
    private static final int PERMISSION_REQUEST_CODE = 400;
    private MapView mapView;
    private GoogleMap mMap;
    private TextView transparentview,picklocationname;
    private RelativeLayout mainlayout;

    private com.sothree.slidinguppanel.SlidingUpPanelLayout mLayout;
    private LinearLayout dragView;
    private Toolbar toolbar;
    Geocoder geocoder;
    List<Address> addresses;
    private CameraPosition cp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_pick_location);
        initMap(savedInstanceState);
        initViews();

    }


    //============================================Helper Methods ============================================================//

    private void initViews() {//initialize views

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        picklocationname= (TextView) findViewById(R.id.picklocname);
        mainlayout= (RelativeLayout) findViewById(R.id.mainlayout);
        // toolbar.setCollapsible(true);
        setSupportActionBar(toolbar);
        dragView = (LinearLayout) findViewById(R.id.dragView);
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.addPanelSlideListener(new SlidePanelListner());
        mLayout.setDragView(dragView);//This is for Collaspe or expand sliding panel using click
        //mainlayout.setOnTouchListener(new MapTouchListner());


    }


    private void initMap(Bundle savedInstance) {
        onPastPermissionCheck();

        mapView = (MapView) findViewById(R.id.mymapview);
        mapView.onCreate(savedInstance);
        mapView.getMapAsync(this);
        mMap = mapView.getMap();


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        //mMap.a



        //currentLocationCameraZoom(this);

    }

    private boolean checkPastPermission() {
        int result = ContextCompat.checkSelfPermission(CustomPickLocation.this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) {

            return true;

        } else {

            return false;

        }
    }

    private void showdialougeRequestpermission() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            final String[] permissions = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        }
    }

    private void onPastPermissionCheck() {

        if (checkPastPermission() == true) {
            //googleMap.setMyLocationEnabled(true);
        } else {
            showdialougeRequestpermission();
        }
    }

    private void currentLocationCameraZoom(Context context) {
        try {


            LocationManager locationManager = (LocationManager) getSystemService(context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if (location != null) {
                // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));
                mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLatitude()) , 14.0f) );

            }
        } catch (Exception e) {
            e.toString();
        }
    }
    private String makeAddress(LatLng latLng) throws IOException {
        String location = null ;

        geocoder = new Geocoder(this, Locale.getDefault());
        addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        if (addresses.isEmpty()) {
            location="Waiting for Location";
        }
        else{
            if(addresses.size() > 0){
                location = addresses.get(0).getFeatureName() + ", "
                        + addresses.get(0).getLocality() + ", "
                        + addresses.get(0).getAdminArea() + ", "
                        + addresses.get(0).getCountryName();

            }
        }
        return location;
    }

    //=========================================Activity callbacks ==================================================//
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        currentLocationCameraZoom(this);
        mMap.setOnCameraChangeListener(new MyCameraPosition());

     //   mMap.set





    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case PERMISSION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the  ,related task you need to do.

                    // mMap.setMyLocationEnabled(true);

                    Toast.makeText(CustomPickLocation.this,"Permission Granted !",Toast.LENGTH_SHORT).show();


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(CustomPickLocation.this,"Permission not Granted !",Toast.LENGTH_SHORT).show();
                }


                return;

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        //currentLocationCameraZoom(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    //==============================================Local Views Listners ====================================================//

    private class SlidePanelListner implements com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener {


        @Override
        public void onPanelSlide(View panel, float slideOffset) {
            Log.i(TAG, "onPanelSlide, offset " + slideOffset);

        }

        @Override
        public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
            Log.i(TAG, "onPanelStateChanged " + newState);

        }


    }



    private class MyTouchListner implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if (mLayout.getPanelState() != com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.COLLAPSED)
            {

                //toolbar.setVisibility(View.GONE);
                return true;
            }

            return false;
        }
    }


    private class MyCameraPosition implements GoogleMap.OnCameraChangeListener {
        @Override
        public void onCameraChange(CameraPosition cameraPosition) {
            //Toast.makeText(getApplicationContext(),""+cameraPosition.target.latitude+cameraPosition.target.longitude,Toast.LENGTH_LONG).show();
            LatLng picklocation=new LatLng(cameraPosition.target.latitude,cameraPosition.target.longitude);
            try {
               String mLocation= makeAddress(picklocation);
                picklocationname.setText(mLocation.toString());
                mLayout.setPanelState(com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.EXPANDED);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
