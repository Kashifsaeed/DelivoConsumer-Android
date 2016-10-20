package com.attribe.delivo.app;

import adapters.DrawerListAdapter;
import adapters.NavDrawerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.*;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import models.response.PlaceDetailsResponse;
import utils.CustomMapView;
import utils.LocationBAL;
import utils.LocationReceiveListener;
import utils.ReverseGeoLocationTask;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class CustomPickLocation extends BaseActivity implements OnMapReadyCallback,
        CustomMapView.MapTouchListener {
    private static final String TAG = "DemoActivity";
    private static final String MotionTag = "MotionDetect";


    private static final int PERMISSION_REQUEST_CODE = 400;
    private CustomMapView mapView;
    private GoogleMap mMap;
    private TextView transparentview, picklocationname;
    private Button mButton;
    private RelativeLayout mainlayout;
    private FrameLayout dragframe;
    private Button pickDelivoBtn;
    View mapcover;
    //private com.sothree.slidinguppanel.SlidingUpPanelLayout mLayout;
    private LinearLayout dragView;
    //private Toolbar toolbar;
    Geocoder geocoder;
    List<Address> addresses;
    private CameraPosition cp;
    private GestureDetector detectorCompat;
    private LatLng cameraPickLocation;
    private boolean mMapIsTouched;
    private ProgressBar mBar;
    private ImageView searchClick;
    public static int PickLocation_RequestCode = 200;
    private LatLng GEOCDES_NY = new LatLng(40.711555, -74.008257);
    public DrawerLayout drawerLayout;
    public ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    DrawerListAdapter navDrawerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_custom_pick_location);
        // setContentView(R.layout.pick_location_layout);
//        setContentView(R.layout.picklocation_with_drawer);
        getLayoutInflater().inflate(R.layout.pick_location_layout, frameLayout);

        initMap(savedInstanceState);
        initViews();

    }


    //============================================Helper Methods ============================================================//

    private void initViews() {//initialize views
        this.overridePendingTransition(R.anim.transition_left_in, R.anim.transition_left_out);
        // toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        inittoolbar(toolbar);
        pickDelivoBtn = (Button) findViewById(R.id.confirm_location_btn);
        picklocationname = (TextView) findViewById(R.id.picklocname);
        mainlayout = (RelativeLayout) findViewById(R.id.mainlayout);

        dragView = (LinearLayout) findViewById(R.id.dragView);
        searchClick = (ImageView) findViewById(R.id.searchPlaces);
        searchClick.setOnClickListener(new FindNearPlaces());
        pickDelivoBtn.setOnClickListener(new ProceedDelivoListner());


    }


    private void inittoolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        toolbar.setTitle("Pick Location");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        //toolbar.setNavigationIcon(R.drawable.ic_action_menuicons);

    }


    private void initMap(Bundle savedInstance) {
        onPastPermissionCheck();

        mapView = (CustomMapView) findViewById(R.id.mymapview);


        mapView.setMapTouchListener(CustomPickLocation.this);


        mapView.onCreate(savedInstance);
        mapView.getMapAsync(this);



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


    private void zoomlocation(LatLng mlocation) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mlocation.latitude, mlocation.longitude), 16));


    }

    private void getLocation() {

        LocationBAL location = new LocationBAL(getApplicationContext());
        location.getLocation(getApplicationContext(), new LocationReceiveListener() {
            @Override
            public void onLocationReceive(LatLng geocodes) {

                zoomlocation(geocodes);

            }
        });
    }



    private void expandPanel() {

        //  mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }

    private void mapGestureEnabled(boolean gestureEnabled) {
        mMap.getUiSettings().setAllGesturesEnabled(gestureEnabled);
    }


    //=========================================Activity callbacks ==================================================//
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getLocation();

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


        // currentLocationCameraZoom(this);

        mMap.setOnCameraChangeListener(new MyCameraPosition());
        mMap.setOnCameraIdleListener(new MyCameraStop());


    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("", "Touched");
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PickLocation_RequestCode && resultCode == RESULT_OK && data != null) {

            PlaceDetailsResponse.Result place = (PlaceDetailsResponse.Result) data.getSerializableExtra("SearchPlace");
            LatLng mlatlng = new LatLng(place.getGeometry().getLocation().getLat(), place.getGeometry().getLocation().getLng());
            zoomlocation(mlatlng);

            picklocationname.setText("" + place.getFormatted_address().toString());

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case PERMISSION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the  ,related task you need to do.

                    // mMap.setMyLocationEnabled(true);

                    Toast.makeText(CustomPickLocation.this, "Permission Granted !", Toast.LENGTH_SHORT).show();


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(CustomPickLocation.this, "Permission not Granted !", Toast.LENGTH_SHORT).show();
                }


                return;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
       // this.overridePendingTransition(R.anim.left_to_right, R.anim.stable);

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


    @Override
    public void OnActionDown(float pressure) {


    }

    @Override
    public void OnActionDown() {

    }

    @Override
    public void OnActionUp() {


    }

    @Override
    public void OnActionMOve() {


    }

    @Override
    public void onScroll() {

    }


    //==============================================Local Views Listners ====================================================//

    private class MyCameraPosition implements GoogleMap.OnCameraChangeListener {
        @Override
        public void onCameraChange(CameraPosition cameraPosition) {


            cameraPickLocation = new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);

        }
    }
    private class ProceedDelivoListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            if (!picklocationname.getText().toString().equals("")) {
                Intent intent = new Intent(CustomPickLocation.this, CustomDropLocation.class);
                intent.putExtra("pickLocation", picklocationname.getText().toString());
                intent.putExtra("pickLatitude", cameraPickLocation.latitude);
                intent.putExtra("pickLongitude", cameraPickLocation.longitude);
                startActivity(intent);
            }

        }
    }
    private class FindNearPlaces implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CustomPickLocation.this, PlaceSearch.class);
            intent.putExtra("Query", picklocationname.getText().toString());
            intent.putExtra("PickFlag", true);
            startActivityForResult(intent, PickLocation_RequestCode);
            //startActivity(intent);

        }
    }
    private class MyCameraStop implements GoogleMap.OnCameraIdleListener {
        @Override
        public void onCameraIdle() {

            //get geo address when camera stop
            try {
                String pick_location= new ReverseGeoLocationTask(getBaseContext()).execute(cameraPickLocation.latitude, cameraPickLocation.longitude).get();
                picklocationname.setText(""+pick_location);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

             //new ReverseGeocodingTask(getBaseContext()).execute(cameraPickLocation.latitude, cameraPickLocation.longitude);

        }
    }




    //===================================== inner Classes ====================================================//

    private class ReverseGeocodingTask extends AsyncTask<Double, Void, String> {
        Context mContext;
        ProgressBar bar;


        public ReverseGeocodingTask(Context context) {
            super();
            mContext = context;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected String doInBackground(Double... params) {
            Geocoder geocoder = new Geocoder(mContext);
            double latitude = params[0].doubleValue();
            double longitude = params[1].doubleValue();

            List<Address> addresses = null;
            String addressText = "";

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);

                addressText = address.getAddressLine(0) + ", "
                        + address.getLocality() + ", "
                        + address.getAdminArea();

            } else {
                addressText = "Not Found...";
            }

            return addressText;
        }

        @Override
        protected void onPostExecute(String addressText) {
            // Setting address of the touched Position
            picklocationname.setText("" + addressText);
        }
    }

}
