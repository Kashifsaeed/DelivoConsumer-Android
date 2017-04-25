package screens;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.attribe.delivo.app.BaseActivity;
import com.attribe.delivo.app.CustomDropLocation;
import com.attribe.delivo.app.PlaceSearch;
import com.attribe.delivo.app.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import adapters.DrawerListAdapter;
import models.AgentsLocation;
import models.response.PlaceDetailsResponse;
import utils.CustomMapView;
import utils.LocationBAL;
import utils.LocationReceiveListener;
import utils.ReverseGeoLocationTask;

//CustomMapView.MapTouchListener
public class CustomPickLocation extends BaseActivity implements OnMapReadyCallback
         {
    FirebaseDatabase mRoot=FirebaseDatabase.getInstance();
    DatabaseReference mRef=mRoot.getReference("AgentsLocation");
    private static final String TAG = "DemoActivity";
    private static final String MotionTag = "MotionDetect";
    private ArrayList<AgentsLocation> markersdata=new ArrayList<AgentsLocation>();
    private Marker marker;
    private ArrayList<Marker> markerArrayList=new ArrayList<Marker>();



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

        getLayoutInflater().inflate(R.layout.picklocation_activity, frameLayout);


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


       // mapView.setMapTouchListener(CustomPickLocation.this);

        if(savedInstance==null) {
            mapView.onCreate(savedInstance);
        }
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



    private void mapGestureEnabled(boolean gestureEnabled) {
        mMap.getUiSettings().setAllGesturesEnabled(gestureEnabled);
    }


    //=========================================Activity callbacks ==================================================//
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getLocation();
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));



            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

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
        //re-positioning the location button
        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 250);//left top right bottom
        }

        mMap.setMyLocationEnabled(true);
       // mMap.getUiSettings().setTiltGesturesEnabled(true);
//        mMap.setPadding(0,0,0,0);



        mMap.setOnCameraChangeListener(new MyCameraPosition());
        mMap.setOnCameraIdleListener(new MyCameraStop());
//
       // mMap.clear();

        //getAgentsLocation();


    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("", "Touched");
        return true;

    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * invoked when users search location to search places screen and get back to result
     */
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
             protected void onPause()
             {
                 super.onPause();
                 mapView.onPause();
             }

             @Override
             protected void onStop() {
                 super.onStop();
                 mapView.onStop();
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

//            if (!picklocationname.getText().toString().equals("")) {
//                Intent intent = new Intent(CustomPickLocation.this, CustomDropLocation.class);
//                intent.putExtra("pickLocation", picklocationname.getText().toString());
//                intent.putExtra("pickLatitude", cameraPickLocation.latitude);
//                intent.putExtra("pickLongitude", cameraPickLocation.longitude);
//                startActivity(intent);
//            }
            startActivity(new Intent(CustomPickLocation.this,PickDetailsActivity.class));

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


}
