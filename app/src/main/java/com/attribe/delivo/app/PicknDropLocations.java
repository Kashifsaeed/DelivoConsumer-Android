package com.attribe.delivo.app;

import adapters.NavDrawerAdapter;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.*;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.widget.Toolbar;


import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import fragments.CallUs;
import fragments.MyOrders;
import fragments.Settings;
import fragments.UserAuthentication;
import models.NewOrder;
import models.User;
import models.response.ResponseNewOrder;
import network.bals.OrderBAL;
import network.interfaces.CreateOrderResponse;
import utils.CustomEditText;
import utils.DevicePreferences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class PicknDropLocations extends AppCompatActivity implements OnMapReadyCallback {
public class PicknDropLocations extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private android.support.v7.widget.Toolbar toolbar;
    private CharSequence drawerTitle;
    private CharSequence mTitle;
    private String[] navMenuTitles;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    public static final String STATUS_ORDER_DEFERRED = "222";
    public static double LAT_KARACHI = 24.8934;
    public static double LNG_KARACHI = 67.0281;
    MapView mapView;
    GoogleMap map;
    final MarkerOptions markerOptions = new MarkerOptions();
    Geocoder geocoder;
    List<Address> addresses;
    private CustomEditText pickLocAddress;
    private CustomEditText dropLocAddress;
    private Button showDropLocBtn;
    private EditText colllaspe_expand;
    private View hiddenPanel;
   // private ImageView showDropLocBtn;

    private Button delivoBtn;
    private View pickLoc;
    private View dropLoc;
    private LinearLayout containerLayout;
    private LinearLayout mapLayout;
    private LatLng userLocation = new LatLng(LAT_KARACHI, LNG_KARACHI);
    NavDrawerAdapter adapter;
    private ProgressDialog progressDialog;

    int[] delivoImages = new int[]{
            R.mipmap.home,
            R.mipmap.orderlist,
            R.mipmap.call_us,
            R.mipmap.settings,
            R.mipmap.sign_out
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_pickn_drop_locations);
          setContentView(R.layout.pickaddress_activity);

        User.getInstance(DevicePreferences.getInstance().init(this).getUser());

        initViews(savedInstanceState);
        currentLocationCameraZoom(this);
        initializeDrawer();
        drawer_Toggle_Handling(savedInstanceState);

    }

    private void initViews(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
       // hiddenPanel=findViewById(R.id.hiddenlayout);
        colllaspe_expand= (EditText) findViewById(R.id.expandorcollaspe);
        expandOrCollapse(colllaspe_expand,"collapse");

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        containerLayout = (LinearLayout) findViewById(R.id.containerLayout);
        mapLayout = (LinearLayout) findViewById(R.id.mapLayout);

        containerLayout.setVisibility(View.GONE);

        pickLocAddress = (CustomEditText) findViewById(R.id.pickAddress);
        pickLocAddress.setText("");
        pickLocAddress.setOnClickListener(new PickSearchFieldListner());

        dropLocAddress = (CustomEditText) findViewById(R.id.dropAddress);
        dropLocAddress.requestFocus();
        dropLocAddress.setText("");
        dropLocAddress.setOnClickListener(new DropSearchFieldListner());

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        mapView.getMapAsync(this);


        map = mapView.getMap();


        map.setOnMarkerDragListener(this);

        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

                LatLng target = cameraPosition.target;

                try {
                    pickLocAddress.setText(makeAddress(target));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        onPastPermissionCheck(map);


        //map.setOnMapClickListener(new MapClickListner());

        showDropLocBtn = (Button) findViewById(R.id.showDropLoc);
        showDropLocBtn.setOnClickListener(new DropLocListner());

        pickLoc = findViewById(R.id.layout_pickLocation);
        dropLoc = findViewById(R.id.layout_dropLocation);

        delivoBtn = (Button) findViewById(R.id.buttonDelivo);
        delivoBtn.setOnClickListener(new DelivoListner());

    }
    public static void expand(final View v) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    private void currentLocationCameraZoom(Context context) {

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
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    private void initializeDrawer() {
        navMenuTitles = getResources().getStringArray(R.array.delivo_utilities);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_left);
        drawerList = (ListView) findViewById(R.id.list_slidermenu);

//        mTitle = drawerTitle = getSupportActionBar().getTitle();

        adapter = new NavDrawerAdapter(getApplicationContext(), delivoImages, navMenuTitles);
        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener(new SlideMenuClickListener());
    }


    private void drawer_Toggle_Handling(Bundle savedInstanceState) {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.Main_Screen_Title, R.string.Main_Screen_Title);
        drawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Spannable text = new SpannableString(getSupportActionBar().getTitle());
        text.setSpan(new ForegroundColorSpan(Color.WHITE), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(text);
        mDrawerToggle.syncState();
    }


    private void onPastPermissionCheck(GoogleMap googleMap) {

        if (checkPastPermission() == true) {
            map.setMyLocationEnabled(true);
        } else {
            showdialougeRequestpermission();
        }
    }

    private void showProgress(String message) {
        progressDialog = ProgressDialog.show(PicknDropLocations.this, "", message, false);
    }

    private void hideProgress() {
        progressDialog.dismiss();
    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
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
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        //map.setMyLocationEnabled(true);

//        // location and zoom of the MapView
//        LatLng sydney = new LatLng(24.870783, 67.089439);
//        map.addMarker(new MarkerOptions().position(sydney).title("Sharae-faisal "));
//        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case PERMISSION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
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
                    map.setMyLocationEnabled(true);
                    Toast.makeText(PicknDropLocations.this,"Permission Granted !",Toast.LENGTH_SHORT).show();


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(PicknDropLocations.this,"Permission not Granted !",Toast.LENGTH_SHORT).show();
                }


                return;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);

                if(pickLocAddress.getText().toString().isEmpty()){
                    map.clear();
                    markerOptions.position(place.getLatLng());
                    showProgress("Getting Location ...");
                    map.addMarker(markerOptions);
                    hideProgress();
                    pickLocAddress.setText("" + place.getAddress().toString());
                    makeZoom(place);
                    DevicePreferences.getInstance().setSourceLocationObject(place.getLatLng().latitude,place.getLatLng().longitude,place.getAddress().toString());
                }
                else{
                      if(dropLocAddress.getText().toString().isEmpty()) {

                          markerOptions.position(place.getLatLng());
                          showProgress("Getting Location ...");
                          final Marker dropLocationMarker = map.addMarker(markerOptions);
                          dropLocationMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                          hideProgress();
                          dropLocAddress.setText("" + place.getAddress().toString());
                          makeZoom(place);
                          DevicePreferences.getInstance().setDestinationLocationObject(place.getLatLng().latitude, place.getLatLng().longitude, place.getAddress().toString());
                      }
                      else{
                          Toast.makeText(PicknDropLocations.this,"Location already placed !",Toast.LENGTH_SHORT).show();
                      }
                }
            }
        }
    }

    private void makeZoom(Place place) {

        if (place.getLatLng() != null)
        {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(place.getLatLng().latitude , place.getLatLng().longitude ), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(place.getLatLng().latitude , place.getLatLng().longitude ))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    private void showdialougeRequestpermission(){

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            final String[] permissions = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkPastPermission(){
        int result = ContextCompat.checkSelfPermission(PicknDropLocations.this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;

        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {


    }

    private class MapClickListner implements GoogleMap.OnMapClickListener {
        @Override
        public void onMapClick(LatLng latLng) {
//            Toast.makeText(PicknDropLocations.this," "+latLng.latitude+" "+latLng.longitude,Toast.LENGTH_SHORT).show();


            try {
                if(pickLocAddress.getText().toString().isEmpty()){
                    map.clear();
                    markerOptions.position(latLng);
                    showProgress("Getting Location ...");
                    map.addMarker(markerOptions);
                    hideProgress();
                    pickLocAddress.setText("" + makeAddress(latLng).toString());
                    DevicePreferences.getInstance().setSourceLocationObject(latLng.latitude,latLng.longitude,pickLocAddress.getText().toString());
                }
                else{
                    markerOptions.position(latLng);
                    showProgress("Getting Location ...");
                    final Marker dropLocationMarker = map.addMarker(markerOptions);
                    dropLocationMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    hideProgress();
                    dropLocAddress.setText("" + makeAddress(latLng).toString());
                    DevicePreferences.getInstance().setDestinationLocationObject(latLng.latitude,latLng.longitude,dropLocAddress.getText().toString());
                }

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String makeAddress(LatLng latLng) throws IOException {
        String location = null ;

        geocoder = new Geocoder(this, Locale.getDefault());
        addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 5); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

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

    private void showSearchField() {
        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                                               .setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS)
                                               .build();

            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                                 .setBoundsBias(new LatLngBounds(new LatLng(24.751980, 66.660957),
                                                  new LatLng(25.270311, 67.468452)))
                                                 .setFilter(typeFilter)
                                                 .build(PicknDropLocations.this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        }
          catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    private class PickSearchFieldListner implements View.OnClickListener {
        @Override
        public void onClick(View v) { showSearchField(); }
    }

    private class DropSearchFieldListner implements View.OnClickListener {
        @Override
        public void onClick(View v) { showSearchField(); }
    }

    private class DropLocListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //slideUpDown();
           // colllaspe_expand.setVisibility(View.VISIBLE);
           // expand(colllaspe_expand);
            expandOrCollapse(colllaspe_expand,"expand");

//            pickLoc.setVisibility(View.GONE);
//            dropLoc.setVisibility(View.VISIBLE);
        }
    }

    private class DelivoListner implements View.OnClickListener {
        @Override
        public void onClick(final View v) {

            if(dropLocAddress.getText().toString().isEmpty()){
                Toast.makeText(PicknDropLocations.this,getResources().getString(R.string.drop_address_empty), Toast.LENGTH_SHORT).show();
            }

            else{
                mapLayout.setVisibility(v.GONE);
                containerLayout.setVisibility(v.VISIBLE);
                NavigationUtils.placeOrderPanel(PicknDropLocations.this,getFragmentManager());
                hideKeyboard();
            }
        }
    }

    public void setDraweropened() {
        drawerLayout.openDrawer(drawerList);
    }

    public void setDrawerclosed() {
        drawerLayout.closeDrawer(drawerList);
    }

    @Override
    protected void onStop() {
        super.onStop();
        DevicePreferences.getInstance().init(this);
        DevicePreferences.getInstance().setUser();
    }
    public void expandOrCollapse(final View v,String exp_or_colpse) {
        TranslateAnimation anim = null;
        if(exp_or_colpse.equals("expand"))
        {
            anim = new TranslateAnimation(0.0f, 0.0f, -v.getHeight(), 0.0f);
            v.setVisibility(View.VISIBLE);
        }
        else{
            anim = new TranslateAnimation(0.0f, 0.0f, 0.0f, -v.getHeight());
            Animation.AnimationListener collapselistener= new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    v.setVisibility(View.GONE);
                }
            };

            anim.setAnimationListener(collapselistener);
        }

        // To Collapse
        //

        anim.setDuration(300);
        anim.setInterpolator(new AccelerateInterpolator(0.5f));
        v.startAnimation(anim);
    }
    private boolean isPanelShown() {

        return hiddenPanel.getVisibility() == View.VISIBLE;
    }
    public void slideUpDown() {
        if (!isPanelShown()) {
            // Show the panel
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
        }
        else {
            // Hide the Panel
            Animation bottomDown = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.GONE);
        }
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

                    intent = new Intent(getApplicationContext(),PicknDropLocations.class);

                    if(getApplicationContext() instanceof PicknDropLocations ){
                        setDrawerclosed();
                    }
                    else{
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    break;

                case 1:

                    MyOrders fragment_myOrders = new MyOrders();
                    fragmentManager = getFragmentManager();
                    mapLayout.setVisibility(LinearLayout.GONE);
                    fragmentManager.beginTransaction().replace(R.id.fragments_container, fragment_myOrders).commit();
                    setDrawerclosed();
                    break;

                case 2:

                    Settings fragment_settings = new Settings();
                    fragmentManager = getFragmentManager();
                    mapLayout.setVisibility(LinearLayout.GONE);
                    fragmentManager.beginTransaction().replace(R.id.fragments_container, fragment_settings).commit();
                    setDrawerclosed();
                    break;

                case 3:

                    CallUs fragment_callUs = new CallUs();
                    fragmentManager = getFragmentManager();
                    mapLayout.setVisibility(LinearLayout.GONE);
                    fragmentManager.beginTransaction().replace(R.id.fragments_container, fragment_callUs).commit();
                    setDrawerclosed();
                    break;

                case 4:
                    UserAuthentication userAuthentication = new UserAuthentication();
                    fragmentManager = getFragmentManager();
                    DevicePreferences.getInstance().removeUser(PicknDropLocations.this);
                    mapLayout.setVisibility(LinearLayout.GONE);
                    fragmentManager.beginTransaction().replace(R.id.fragments_container, userAuthentication).commit();
                    setDrawerclosed();
                    break;

                default:
                    Toast.makeText(getApplicationContext(), "Sorry.... wrong choice!", Toast.LENGTH_LONG).show();
                    break;
            }

        }
    }
}
