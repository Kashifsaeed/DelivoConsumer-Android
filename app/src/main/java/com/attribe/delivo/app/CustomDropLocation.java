package com.attribe.delivo.app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.*;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import models.response.PlaceDetailsResponse;
import utils.LocationBAL;
import utils.LocationReceiveListener;

import java.util.ArrayList;

public class CustomDropLocation extends AppCompatActivity implements OnMapReadyCallback {
    private MapView dmapview;
    private GoogleMap mMap;
    private TextView picklocationText, droplocknameText;
    private Button goDelivo_btn;
    private LinearLayout dragingRegion;
    private String pickAdd;
    private LatLng pickLatlng,droplatlng;
    public static int DropLocation_ResultsCode = 201;

    private ArrayList<Marker> pickDropMarkers = new ArrayList<Marker>();
    private Toolbar droptoolbar;


   // private com.sothree.slidinguppanel.SlidingUpPanelLayout slidinglayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_custom_drop_location);
        setContentView(R.layout.drop_location_layout);

        initMaps(savedInstanceState);
        initViews();
    }


//========================================== Helper Methods ============================================================//
    private void initViews() {//initialize Views
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getIntentValues();
        droptoolbar= (Toolbar) findViewById(R.id.my_toolbar);
        inittoolbar(droptoolbar);
        goDelivo_btn = (Button) findViewById(R.id.confirm_location_btn);
        //slidinglayout = (SlidingUpPanelLayout) findViewById(R.id.dropsliding_layout);
        dragingRegion = (LinearLayout) findViewById(R.id.dropdragView);
        picklocationText = (TextView) findViewById(R.id.pickconfrmname);
        droplocknameText = (TextView) findViewById(R.id.droplockname);
        picklocationText.setText("" + pickAdd);
//        slidinglayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
//        slidinglayout.setDragView(dragingRegion);
        goDelivo_btn.setOnClickListener(new PoceedOrderCreation());
        droplocknameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CustomDropLocation.this, PlaceSearch.class);

                startActivityForResult(intent, DropLocation_ResultsCode);
            }
        });


    }


    private void initMaps(Bundle savedInstanceState) {
        dmapview = (MapView) findViewById(R.id.dropmapview);
        dmapview.onCreate(savedInstanceState);
        dmapview.getMapAsync(this);


    }
    private void inittoolbar(Toolbar toolbar)
    {
        toolbar.setTitle("Drop Location");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
       // getSupportActionBar().setHomeButtonEnabled(true);

        //setSupportActionBar(toolbar);

       toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void getIntentValues() {


        Intent intent = getIntent();
        pickAdd = intent.getExtras().getString("pickLocation");
        double lat = intent.getDoubleExtra("pickLatitude", 0);
        double lon = intent.getDoubleExtra("pickLongitude", 0);
        pickLatlng = new LatLng(lat, lon);


        // return getIntent().getExtras().getString("pickLocation").toString();

    }

    private void makeMarkers(LatLng markerpoints, String tittle) {

        Marker m = mMap.addMarker(new MarkerOptions().title(tittle)
                .position(markerpoints)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        pickDropMarkers.add(m);

    }

    private void zoomlocation(LatLng mlocation ,float zoom_level) {

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mlocation.latitude, mlocation.longitude), zoom_level));


    }
    private void getMyLocation(){

        LocationBAL location=new LocationBAL(getApplicationContext());
        location.getLocation(getApplicationContext(), new LocationReceiveListener() {
            @Override
            public void onLocationReceive(LatLng geocodes) {

                zoomlocation(geocodes,16);

            }
        });
    }
    private ArrayList<Marker> createMarker(LatLng mMarkerpick, LatLng mMarkerdrop) {
        ArrayList<Marker> pickdrop_Marker = new ArrayList<Marker>();
        Marker mp = mMap.addMarker(new MarkerOptions()
                .position(mMarkerpick)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        pickdrop_Marker.add(mp);

        Marker md = mMap.addMarker(new MarkerOptions()
                .position(mMarkerdrop)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        pickdrop_Marker.add(md);


        return pickdrop_Marker;


    }
    private void zoomToAllMarkers(LatLng pick_ltln, LatLng drop_ltlng) {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(pick_ltln);
        builder.include(drop_ltlng);
        LatLngBounds bounds = builder.build();
        //mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 11));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 40));
    }

    //========================================= Activity Callbacks ===================================================//

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        dmapview.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dmapview.onDestroy();

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.overridePendingTransition(R.anim.transition_right_in,R.anim.transition_right_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dmapview.onResume();

        this.overridePendingTransition(R.anim.transition_left_in, R.anim.transition_left_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //this.overridePendingTransition(R.anim.transition_right_in,R.anim.transition_right_out);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getMyLocation();
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


    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DropLocation_ResultsCode && resultCode == RESULT_OK && data != null) {
            PlaceDetailsResponse.Result place = (PlaceDetailsResponse.Result) data.getSerializableExtra("SearchPlace");

//            GoogleAPiByText.Result place = (GoogleAPiByText.Result) data.getSerializableExtra("SearchPlace");
            //droplocknameText.setText(place.getName() + place.getFormatted_address());
            droplocknameText.setText(""+place.getFormatted_address());
           // LatLng droplatlng = new LatLng(place.getGeometry().getMyLocation().getLat(), place.getGeometry().getMyLocation().getLng());
        droplatlng = new LatLng(place.getGeometry().getLocation().getLat(), place.getGeometry().getLocation().getLng());

            mMap.clear();
            createMarker(pickLatlng, droplatlng);
            zoomlocation(droplatlng,10);

            mMap.getUiSettings().setAllGesturesEnabled(false);
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
            mMap.setMyLocationEnabled(false);



        }
    }

    //========================================= Buttons Listners =============================================================//

    private class PoceedOrderCreation implements View.OnClickListener {
        @Override
        public void onClick(View view) {


//            DevicePreferences.getInstance().setDestinationLocationObject(droplatlng.latitude,droplatlng.longitude, droplocknameText.getText().toString());
//            DevicePreferences.getInstance().setSourceLocationObject(pickLatlng.latitude,pickLatlng.longitude,pickAdd);

            Intent intent =new Intent(CustomDropLocation.this,OrderCreating.class);
            Bundle order_bundle=new Bundle();
            order_bundle.putString("pickAddress",pickAdd);
            order_bundle.putParcelable("pickLatLng",pickLatlng);
            order_bundle.putString("dropAddress",droplocknameText.getText().toString());
            order_bundle.putParcelable("dropLatLng",droplatlng);
            intent.putExtra("Orderinfo",order_bundle);
            startActivity(intent);

        }
    }

}
