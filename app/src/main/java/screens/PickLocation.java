package screens;

import adapters.NavDrawerAdapter;
import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;
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
import fragments.CallUs;
import fragments.MyOrders;
import fragments.Settings;
import models.DeleveryOrderItem;
import models.NewOrder;
import models.SourceLocation;
import models.response.ResponseNewOrder;
import network.bals.LoginBAL;
import network.bals.OrderBAL;
import network.interfaces.CreateOrderResponse;
import network.interfaces.LoginUserResponse;
import utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PickLocation extends BaseActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener,
        OnMapReadyCallback {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 100;
    public static double LAT_KARACHI = 24.8934;
    public static double LNG_KARACHI = 67.0281;
    public static final String STATUS_ORDER_DEFERRED = "222";

    private CustomEditText pickAddress;
    private GoogleMap map;
    private Progress progress;
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private Button showDropLoc;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;
    private FusedLocationProviderApi fusedLocationProviderApi;
    private LatLng userLocation = new LatLng(LAT_KARACHI, LNG_KARACHI);
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private CharSequence mDrawerTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence drawerTitle;
    private CharSequence mTitle;
    private String[] navMenuTitles;
    NavDrawerAdapter adapter;
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private ListView drawerList;
    private LinearLayout mapLayout;
    private LatLng GEOCODE_PIVOT_LOC = new LatLng(PickLocation.LAT_KARACHI, PickLocation.LNG_KARACHI);
    private Button delivoButton;
    private CustomEditText dropAddress;
    private String orderid;
    private static LocationTracker gps;

    int[] delivoImages = new int[]{
            R.drawable.home,
            R.drawable.order_d,
            R.drawable.setting_d,
            R.drawable.call_us,
            R.drawable.signout
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pick_location);
        super.initToolBar(this.getWindow().getDecorView().findViewById(android.R.id.content));
        initView();
        super.getLocationPermission(this);

        initializeDrawer();
        drawer_Toggle_Handling(savedInstanceState);    //  enabling action bar app icon and behaving it as toggle button
    }

    private void drawer_Toggle_Handling(Bundle savedInstanceState) {

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, null, R.string.app_name, R.string.app_name) {

            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();  // calling onPrepareOptionsMenu() to show action bar icons
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();  // calling onPrepareOptionsMenu() to hide action bar icons
            }
        };

        drawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            //displayView(0);
        }

    }

    public void initializeDrawer() {
        navMenuTitles = getResources().getStringArray(R.array.delivo_utilities);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_left);
        drawerList = (ListView) findViewById(R.id.list_slidermenu);

        mTitle = drawerTitle = getTitle();

        adapter = new NavDrawerAdapter(getApplicationContext(), delivoImages, navMenuTitles);
        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener(new SlideMenuClickListener());
    }

    public void setDraweropened() {
        drawerLayout.openDrawer(drawerList);
    }

    public void setDrawerclosed() {
        drawerLayout.closeDrawer(drawerList);
    }


    @Override
    protected void onToolBarInit(Toolbar toolbar) {
        this.toolbar = toolbar;
        toolbar.setTitleTextColor(getResources().getColor(R.color.text_color_button));
        toolbar.setTitle("Pick and Drop Locations");

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        pickAddress = (CustomEditText) findViewById(R.id.pickAddress);
        pickAddress.getText().clear();

        showDropLoc = (Button) findViewById(R.id.showDropLoc);
        showDropLoc.setOnClickListener(new showDropLocation_Panel());

        pickAddress.setOnClickListener(new SearchFieldListner());
        pickAddress.setDrawableClickListener(new SearchIconClickListener());
        mapLayout = (LinearLayout) findViewById(R.id.mapLayout);
    }


    private void initMap(LatLng geocodes) {
        map = ((MapFragment) /*getChildFragmentManager().*/getFragmentManager().findFragmentById(R.id.mapFragment))
                .getMap();

        map.setMyLocationEnabled(true);

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
                           if(pickAddress.getText().toString().isEmpty()) {
                               pickAddress.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                           }
                            else{
                               dropAddress.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                           }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace(); // getFromLocation() may sometimes fail
                }
            }
        });
    }

    private void initMap_dropLoc(LatLng geocodes){


        map = ((MapFragment) /*getChildFragmentManager().*/getFragmentManager().findFragmentById(R.id.mapFragment))
                .getMap();

        map.setMyLocationEnabled(true);

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

                            dropAddress.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());

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

                initMap(place.getLatLng());


                if(pickAddress.getText().toString().isEmpty()){
                    pickAddress.setText(place.getAddress().toString());
//                    DevicePreferences.getInstance().setPickLocation(place.getAddress().toString());
                    DevicePreferences.getInstance().setSourceLocationObject(userLocation.latitude,userLocation.longitude,place.getAddress().toString());
                }
                else {
                    dropAddress.setText(place.getAddress().toString());
//                    DevicePreferences.getInstance().setDropLocation(place.getAddress().toString());
                    DevicePreferences.getInstance().setDestinationLocationObject(userLocation.latitude,userLocation.longitude,place.getAddress().toString());
                }


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


    private class showDropLocation_Panel implements View.OnClickListener {
            @Override
            public void onClick(View view) {

                if(!pickAddress.getText().toString().isEmpty()){
//                    NavigationUtils.showDropLocationScreen(PickLocation.this,userLocation);
                    View pickLoc = findViewById(R.id.layout_pickLocation);
                    View dropLoc = findViewById(R.id.layout_dropLocation);

                    pickLoc.setVisibility(view.GONE);
                    dropLoc.setVisibility(view.VISIBLE);
                    lyout_dropLocation_implementation();
                }
                else{
                    Toast.makeText(PickLocation.this,getResources().getString(R.string.pick_address_empty),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }

    private void lyout_dropLocation_implementation() {

        GEOCODE_PIVOT_LOC = userLocation;
        initMap(GEOCODE_PIVOT_LOC);
      //  initMap_dropLoc(GEOCODE_PIVOT_LOC);
        delivoButton = (Button)findViewById(R.id.buttonDelivo);
        delivoButton.setOnClickListener(new DelivoButtonListener());
        dropAddress = (CustomEditText) findViewById(R.id.dropAddress);
        dropAddress.getText().clear();
        dropAddress.setDrawableClickListener(new DropAddressIconListener());
        dropAddress.setOnClickListener(new DropLocFieldListener());
    }

    private class SlideMenuClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent;
            SharedPreferences sp;
            FragmentManager fragmentManager;

            switch (position) {

                case 0:

                    sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

                    intent = new Intent(getApplicationContext(),PickLocation.class);

                    if(getApplicationContext() instanceof PickLocation ){
                        setDrawerclosed();
                    }
                    else{
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
//                    overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                    }
                    break;

                case 1:

                    MyOrders fragment_myOrders = new MyOrders();
                    fragmentManager = getFragmentManager();
                    mapLayout.setVisibility(LinearLayout.GONE);
                    fragmentManager.beginTransaction().replace(R.id.frame_container, fragment_myOrders).commit();
                    setDrawerclosed();
                    break;

                case 2:

                    Settings fragment_settings = new Settings();
                    fragmentManager = getFragmentManager();
                    mapLayout.setVisibility(LinearLayout.GONE);
                    fragmentManager.beginTransaction().replace(R.id.frame_container, fragment_settings).commit();
                    setDrawerclosed();
                    break;

                case 3:

                    CallUs fragment_callUs = new CallUs();
                    fragmentManager = getFragmentManager();
                    mapLayout.setVisibility(LinearLayout.GONE);
                    fragmentManager.beginTransaction().replace(R.id.frame_container, fragment_callUs).commit();
                    setDrawerclosed();
                    break;

                case 4:
                    DevicePreferences.getInstance().removeUser(PickLocation.this);
                    mapLayout.setVisibility(LinearLayout.GONE);
                    setDrawerclosed();
                    break;

                default:
                    Toast.makeText(getApplicationContext(), "Sorry.... wrong choice!", Toast.LENGTH_LONG).show();
                    break;
            }

        }
    }

    private class SearchFieldListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            showSearchField();
        }
    }

    private class DelivoButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            if(dropAddress.getText().toString().isEmpty()){
                Toast.makeText(PickLocation.this,getResources().getString(R.string.drop_address_empty),
                        Toast.LENGTH_SHORT).show();

            }

            else{
                List<DeleveryOrderItem> paramList = new ArrayList<DeleveryOrderItem>();

                DeleveryOrderItem param = new DeleveryOrderItem(
                        DevicePreferences.getInstance().getSourceLocationObject(),
                        DevicePreferences.getInstance().getDestinationLocationObject());

                paramList.add(param);

                NewOrder newOrder = new NewOrder("SUN2",
                        "lorem ipsum description",
                        "userId",paramList);

                if(DevicePreferences.getInstance().getUser()!=null){

                    OrderBAL.createOrder(newOrder, new CreateOrderResponse() {
                        @Override
                        public void orderCreatedSuccessfully(ResponseNewOrder body) {

                            Toast.makeText(PickLocation.this, "order created successfully", Toast.LENGTH_SHORT).show();

//                            NavigationUtils.showUserAuthScreen(PickLocation.this);
//                           showConfirmationScreen(body.getData().getOrderid());
                            login();

                        }

                        @Override
                        public void tokenExpired() {
                            Toast.makeText(PickLocation.this, "token expired", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void failure(String s) {

                        }
                    });

                }

                else{
                    DevicePreferences.getInstance().setOrder(newOrder);
//                    NavigationUtils.showRegistrationScreen(PickLocation.this);
                    NavigationUtils.showUserAuthScreen(PickLocation.this);
                }
            }
        }
    }

    private void login() {

        if(DevicePreferences.getInstance().getUser()!=null){

            //refresh token &
            //login the user

            LoginBAL.login(DevicePreferences.getInstance().getUser(), new LoginUserResponse() {
                @Override
                public void OnLoggedIn() {
                    progress.dismiss();
                    NavigationUtils.showConfirmationScreen(getFragmentManager(), STATUS_ORDER_DEFERRED);
                }

                @Override
                public void OnLoggedInFailed() {
                    NavigationUtils.showUserAuthScreen(PickLocation.this);
                }
            });
        }
    }

//    private void showLoginScreen() {
//
//        NavigationUtils.showUserAuthScreen(getApplicationContext());
//    }

//    private void showConfirmationScreen(String orderid) {
//        progress.show(getFragmentManager(),"");
//        this.orderid = orderid;
//        long TIME_OUT = 3000;
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                progress.dismiss();
//                NavigationUtils.showConfirmationScreen(PickLocation.this,getFragmentManager(),
//                        PickLocation.this.orderid);
//
//            }
//        }, TIME_OUT);
//    }

    private class DropAddressIconListener implements CustomEditText.DrawableClickListener {
        @Override
        public void onClick(DrawablePosition target) {
            showSearchField();
        }
    }

    private class DropLocFieldListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            showSearchField();
        }
    }
}



