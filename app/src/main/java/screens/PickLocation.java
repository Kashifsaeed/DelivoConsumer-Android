package screens;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.attribe.delivo.app.NavigationUtils;
import com.attribe.delivo.app.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.*;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import utils.CustomEditText;
import utils.DevicePreferences;
import utils.LocationHelper;
import utils.Progress;

import java.util.List;
import java.util.Locale;

public class PickLocation extends BaseActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener,
        OnMapReadyCallback{

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 100;
    public static double LAT_KARACHI = 24.8934;
    public static double LNG_KARACHI = 67.0281;
    private CustomEditText pickAddress;
    private GoogleMap map;
    private Progress progress;
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private Button showDropLoc;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;
    private FusedLocationProviderApi fusedLocationProviderApi;
    private LatLng userLocation = new LatLng(LAT_KARACHI,LNG_KARACHI);
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;


//    public PickLocation() {
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pick_location);
        super.initToolBar(this.getWindow().getDecorView().findViewById(android.R.id.content));
        initView();
        super.getLocationPermission(this);
        initDrawer();

    }

    private void initDrawer() {
        String[] mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ListView mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTitle = mDrawerTitle = getTitle();

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
            }
        };



//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setHomeButtonEnabled(true);


    }

    @Override
    protected void onToolBarInit(Toolbar toolbar) {
        this.toolbar = toolbar;
        toolbar.setTitleTextColor(getResources().getColor(R.color.text_color_button));
        toolbar.setTitle("Pick Location");

        setSupportActionBar(toolbar);
    }

    @Override
    protected void onLocationRequirePermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
    }

    @Override
    protected void onLocationPermittedInPast() {

        getLocation();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case MY_PERMISSIONS_REQUEST_ACCESS_LOCATION:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    getLocation();


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }


                return;


        }
    }

    private void getLocation() {
        locationRequest = LocationHelper.getLocationRequest();
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(30000);
        locationRequest.setFastestInterval(10000);

        fusedLocationProviderApi = LocationServices.FusedLocationApi;

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            mGoogleApiClient.connect();
        }
    }

    //========================GoogleApiClient.ConnectionCallbacks

    @Override
    public void onConnected(Bundle bundle) {

        fusedLocationProviderApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    //================================================================

    //=======================GoogleApiClient.OnConnectionFailedListener
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    //==================================================================

    //===========================com.google.android.gms.location.LocationListener

    @Override
    public void onLocationChanged(Location location) {

        if (location != null) {

            userLocation = new LatLng(location.getLatitude(), location.getLongitude());
            initMap(new LatLng(location.getLatitude(), location.getLongitude()));
            stopLocationUpdates();
        }


    }

    //=========================================================================

    private void initView() {
        pickAddress = (CustomEditText) findViewById(R.id.pickAddress);
        showDropLoc = (Button) findViewById(R.id.showDropLoc);
        showDropLoc.setOnClickListener(new showDropScreen());
        //pickAddress.setOnClickListener(new SearchFieldClickListener());
        pickAddress.setDrawableClickListener(new SearchIconClickListener());

    }


    private void initMap(LatLng geocodes) {
        map = ((MapFragment) /*getChildFragmentManager().*/getFragmentManager().findFragmentById(R.id.mapFragment))
                .getMap();

        final MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(geocodes);

        final Marker pickLocationMarker = map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(geocodes, 15));
        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);


        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                try {
                    progress = NavigationUtils.getProgress(false);
                    progress.show(getFragmentManager(), "");

                    Geocoder geo = new Geocoder(/*getActivity()*/PickLocation.this, Locale.getDefault());
                    List<Address> addresses = geo.getFromLocation(latLng.latitude, latLng.longitude, 1);

                    if (addresses.isEmpty()) {

                        pickAddress.setText("Waiting for Location");
                    } else {

                        if (addresses.size() > 0) {

                            progress.dismiss();
                            if (pickLocationMarker != null) {
                                pickLocationMarker.remove();
                            }
                            map.clear();
                            markerOptions.position(latLng);

                            Marker pickLocationMarker = map.addMarker(markerOptions);

                            map.addMarker(markerOptions);
                            map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                            String location = addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " +
                                    addresses.get(0).getAdminArea() + ", " +
                                    addresses.get(0).getCountryName();

                            pickAddress.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace(); // getFromLocation() may sometimes fail
                }

            }
        });


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                DevicePreferences.getInstance().setPickLocation(place.getAddress().toString());
                initMap(place.getLatLng());
                pickAddress.setText(place.getAddress().toString());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.


            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }





    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }



    private void showSearchField() {
        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS)
                    .build();

            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .setBoundsBias(new LatLngBounds(new LatLng(24.751980, 66.660957),
                                    new LatLng(25.270311, 67.468452)))
                            .setFilter(typeFilter)
                            .build(PickLocation.this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }

    }

    @Override
    protected void onStart() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

        super.onStart();
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }

        super.onStop();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    private class SearchFieldClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            showSearchField();
        }

    }

    private class SearchIconClickListener implements CustomEditText.DrawableClickListener {
        @Override
        public void onClick(DrawablePosition target) {
            switch (target) {

                case RIGHT:

                    showSearchField();

                    break;

            }

        }

    }



    private class showDropScreen implements View.OnClickListener {
            @Override
            public void onClick(View view) {

                if(!pickAddress.getText().toString().isEmpty()){
                    NavigationUtils.showDropLocationScreen(PickLocation.this,userLocation);
                }

                else{
                    Toast.makeText(PickLocation.this,getResources().getString(R.string.pick_address_empty),
                            Toast.LENGTH_SHORT).show();
                }

            }
        }


}



