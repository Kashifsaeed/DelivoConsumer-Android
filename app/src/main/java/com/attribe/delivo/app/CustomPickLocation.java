package com.attribe.delivo.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.*;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import models.response.AutoCompleteResponse;
import models.response.GoogleAPiByText;
import models.response.PlaceDetailsResponse;
import utils.CustomMapView;
import utils.LocationBAL;
import utils.LocationReceiveListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CustomPickLocation extends AppCompatActivity implements  OnMapReadyCallback,
        CustomMapView.MapTouchListener
       {
    private static final String TAG = "DemoActivity";
           private static final String MotionTag = "MotionDetect";


           private static final int PERMISSION_REQUEST_CODE = 400;
    private CustomMapView mapView;
    private GoogleMap mMap;
    private TextView transparentview,picklocationname;
    private Button mButton;
    private RelativeLayout mainlayout;
    private FrameLayout dragframe;
           private Button pickDelivoBtn;
           View mapcover;

   // public MapFrameLayout mTouchView;


    private com.sothree.slidinguppanel.SlidingUpPanelLayout mLayout;
    private LinearLayout dragView;
    private Toolbar toolbar;
    Geocoder geocoder;
    List<Address> addresses;
    private CameraPosition cp;
   private GestureDetector detectorCompat;
    private LatLng cameraPickLocation;
    private boolean mMapIsTouched;
           private ProgressBar mBar;
           private ImageView searchClick;
           public static int PickLocation_RequestCode =200;
    private LatLng GEOCDES_NY=new LatLng(40.711555, -74.008257);

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
        //mButton= (Button) findViewById(R.id.mButton);
        pickDelivoBtn= (Button) findViewById(R.id.proceedDelivo);
        dragframe= (FrameLayout) findViewById(R.id.drag_region);

        picklocationname= (TextView) findViewById(R.id.picklocname);
        mainlayout= (RelativeLayout) findViewById(R.id.mainlayout);
        setSupportActionBar(toolbar);
        dragView = (LinearLayout) findViewById(R.id.dragView);
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
       // mBar= (ProgressBar) findViewById(R.id.progressLocation);
        searchClick= (ImageView) findViewById(R.id.searchPlaces);
        searchClick.setOnClickListener(new FindNearPlaces());

         //todo :check drag region
       // mLayout.setDragView(dragView);//This is for Collaspe or expand sliding panel using click
        mLayout.setDragView(dragframe);
        //dragframe.setDragView



        pickDelivoBtn.setOnClickListener(new ProceedDelivoListner());








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


   private void zoomlocation(LatLng mlocation){
       mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(mlocation.latitude,mlocation.longitude) , 16) );





   }
    private void getLocation(){

        LocationBAL location=new LocationBAL(getApplicationContext());
        location.getLocation(getApplicationContext(), new LocationReceiveListener() {
            @Override
            public void onLocationReceive(LatLng geocodes) {

                zoomlocation(geocodes);

            }
        });
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

           private void hidePanel(){
               if (mLayout.getPanelState() != SlidingUpPanelLayout.PanelState.HIDDEN) {
                   mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);

               }
           }
           private void collapsePanel() {
               mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
               // mLayout.setVisibility(View.INVISIBLE);
           }

           private void expandPanel() {

               mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
           }
           private void mapGestureEnabled(boolean gestureEnabled)
           {
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

        //mapGestureEnabled(true);






    }

   @Override
   public boolean onTouchEvent(MotionEvent event) {
       Log.d("","Touched");
       return true;

   }

           @Override
           protected void onActivityResult(int requestCode, int resultCode, Intent data) {
               super.onActivityResult(requestCode, resultCode, data);
               if(requestCode== PickLocation_RequestCode && resultCode==RESULT_OK && data!=null){


                   //GoogleAPiByText.Result place= (GoogleAPiByText.Result) data.getSerializableExtra("SearchPlace");
               // AutoCompleteResponse.Prediction place= (AutoCompleteResponse.Prediction) data.getSerializableExtra("SearchPlace");

                  PlaceDetailsResponse.Result place= (PlaceDetailsResponse.Result) data.getSerializableExtra("SearchPlace");
                   LatLng mlatlng=new LatLng(place.getGeometry().getLocation().getLat(),place.getGeometry().getLocation().getLng());
                   zoomlocation(mlatlng);

                   picklocationname.setText(""+place.getFormatted_address().toString());
                   //expandPanel();


               }
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
               //todo:set hide panel on touch
               //hidePanel();
               //mLayout.getChildAt(1).setVisibility(View.GONE);




            }

           @Override
           public void onScroll() {

           }



           //==============================================Local Views Listners ====================================================//

    private class MyCameraPosition implements GoogleMap.OnCameraChangeListener {
        @Override
        public void onCameraChange(CameraPosition cameraPosition) {


            cameraPickLocation =new LatLng(cameraPosition.target.latitude,cameraPosition.target.longitude);

        }
    }




    private class MyCameraStop implements GoogleMap.OnCameraIdleListener {
        @Override
        public void onCameraIdle() {

            collapsePanel();

            new ReverseGeocodingTask(getBaseContext()).execute(cameraPickLocation.latitude,cameraPickLocation.longitude);
//            try {
//                picklocationname.setText(""+makeAddress(cameraPickLocation));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


        }
    }
           private class ReverseGeocodingTask extends AsyncTask<Double, Void, String> {
               Context mContext;
               ProgressBar bar;

               public void setBar(ProgressBar bar) {
                   this.bar = bar;
               }





               public ReverseGeocodingTask(Context context){
                   super();
                   mContext = context;
               }

               @Override
               protected void onProgressUpdate(Void... values) {
                   super.onProgressUpdate(values);
                   //picklocationname.setText("Loading.......");
               }

               @Override
               protected String doInBackground(Double... params) {
                   Geocoder geocoder = new Geocoder(mContext);
                   double latitude = params[0].doubleValue();
                   double longitude = params[1].doubleValue();

                   List<Address> addresses = null;
                   String addressText="";

                   try {
                       addresses = geocoder.getFromLocation(latitude, longitude,1);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }

                   if(addresses != null && addresses.size() > 0 ){
                       Address address = addresses.get(0);
//
                                addressText = address.getAddressLine(0) + ", "
                               + address.getLocality() + ", "
                               + address.getAdminArea();

                   }
                   else {addressText="Waiting...";}

                   return addressText;
               }

               @Override
               protected void onPostExecute(String addressText) {
                   // Setting address of the touched Position

                   picklocationname.setText(""+addressText);
               }
           }


           private class FindNearPlaces implements View.OnClickListener {
               @Override
               public void onClick(View view) {
                   Intent intent=new Intent(CustomPickLocation.this,PlaceSearch.class);
                   intent.putExtra("Query",picklocationname.getText().toString());
                   intent.putExtra("PickFlag",true);
                   startActivityForResult(intent, PickLocation_RequestCode);
                   //startActivity(intent);

               }
           }

           private class ProceedDelivoListner implements View.OnClickListener {
               @Override
               public void onClick(View view) {
                   //startActivity(new Intent(CustomPickLocation.this,CustomDropLocation.class));
                   if(!picklocationname.getText().toString().equals("")) {
                       Intent intent = new Intent(CustomPickLocation.this, CustomDropLocation.class);
                       intent.putExtra("pickLocation", picklocationname.getText().toString());
                       intent.putExtra("pickLatitude",cameraPickLocation.latitude);
                       intent.putExtra("pickLongitude",cameraPickLocation.longitude);
                       startActivity(intent);
                   }

               }
           }
       }
