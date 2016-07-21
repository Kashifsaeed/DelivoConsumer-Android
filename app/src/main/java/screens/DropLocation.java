package screens;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.widget.Button;
import android.widget.Toast;
import com.attribe.delivo.app.NavigationUtils;
import com.attribe.delivo.app.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import models.DeleveryOrderItem;
import models.NewOrder;
import models.response.ResponseNewOrder;
import network.bals.OrderBAL;
import network.interfaces.CreateOrderResponse;
import utils.CustomEditText;
import utils.DevicePreferences;
import utils.Progress;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class DropLocation extends BaseActivity {

    private GoogleMap map;
    private Progress progress;
    private CustomEditText dropAddress;
    private LatLng GEOCODE_PIVOT_LOC = new LatLng(PickLocation.LAT_KARACHI,PickLocation.LNG_KARACHI);
    private Button delivoButton;
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE =1;
    private String orderid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_drop_location);
        super.initToolBar(this.getWindow().getDecorView().findViewById(android.R.id.content));
        initView();
    }

    @Override
    protected void onLocationRequirePermission() {

    }

    @Override
    protected void onLocationPermittedInPast() {

    }

    @Override
    protected void onToolBarInit(Toolbar toolbar) {
        toolbar.setTitle("Drop Location");
        toolbar.setTitleTextColor(getResources().getColor(R.color.text_color_button));
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void initView(){
        progress = NavigationUtils.getProgress(true);

        if(getIntent()!= null){

            GEOCODE_PIVOT_LOC = getIntent().getParcelableExtra(NavigationUtils.KEY_LATLNG);
        }

        initMap(GEOCODE_PIVOT_LOC);
        delivoButton = (Button)findViewById(R.id.buttonDelivo);
        delivoButton.setOnClickListener(new DelivoButtonListener());
        dropAddress = (CustomEditText) findViewById(R.id.dropAddress);
        dropAddress.setDrawableClickListener(new DropAddressIconListener());
        //dropAddress.setOnClickListener(new DropLocFieldListener());
    }
    private void initMap(LatLng geocodes) {


        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment))
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
                    progress = NavigationUtils.getProgress(true);
                    progress.show(getFragmentManager(), "");

                    Geocoder geo = new Geocoder(DropLocation.this, Locale.getDefault());
                    List<Address> addresses = geo.getFromLocation(latLng.latitude, latLng.longitude, 1);

                    if (addresses.isEmpty()) {

                        dropAddress.setText("Waiting for Location");
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
                DevicePreferences.getInstance().setDropLocation(place.getAddress().toString());
                initMap(place.getLatLng());
                dropAddress.setText(place.getAddress().toString());



            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.


            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
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
                            .build(DropLocation.this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }

    }
    private class DropAddressIconListener implements CustomEditText.DrawableClickListener {
        @Override
        public void onClick(DrawablePosition target) {
            showSearchField();

        }
    }

    private class DelivoButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(dropAddress.getText().toString().isEmpty()){
                Toast.makeText(DropLocation.this,getResources().getString(R.string.drop_address_empty),
                        Toast.LENGTH_SHORT).show();

            }

            else{
                List<DeleveryOrderItem> paramList = new ArrayList<DeleveryOrderItem>();

                DeleveryOrderItem param = new DeleveryOrderItem(
                        DevicePreferences.getInstance().getPickLoc(),
                        DevicePreferences.getInstance().getDropLoc()
                        ,"sabih");

                paramList.add(param);

                NewOrder newOrder = new NewOrder("SUN2",
                        "lorem ipsum description",
                        "userId",paramList);

                if(DevicePreferences.getInstance().getUser()!=null){

                    OrderBAL.createOrder(newOrder, new CreateOrderResponse() {
                        @Override
                        public void orderCreatedSuccessfully(ResponseNewOrder body) {

                            Toast.makeText(DropLocation.this, "order created successfully", Toast.LENGTH_SHORT).show();

                            showConfirmationScreen(body.getData().getOrderid());

                        }

                        @Override
                        public void tokenExpired() {
                            Toast.makeText(DropLocation.this, "token expired", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void failure(String s) {

                        }
                    });

                }

                else{
                    DevicePreferences.getInstance().setOrder(newOrder);
                    NavigationUtils.showRegistrationScreen(DropLocation.this);
                }

            }
            }

    }

    private void showConfirmationScreen(String orderid) {
        progress.show(getFragmentManager(),"");
        this.orderid = orderid;
        long TIME_OUT = 3000;

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                progress.dismiss();
                NavigationUtils.showConfirmationScreen(DropLocation.this,
                        DropLocation.this.orderid);

            }
        }, TIME_OUT);
    }


    private class DropLocFieldListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            showSearchField();
        }
    }
}
