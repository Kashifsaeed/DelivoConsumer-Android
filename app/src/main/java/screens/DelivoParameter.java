package screens;

import android.app.Fragment;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.attribe.delivo.app.NavigationUtils;
import com.attribe.delivo.app.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;
import utils.Progress;

import java.util.List;
import java.util.Locale;


public class DelivoParameter extends Fragment {

    private OnDelivoParameterInteration mListener;
    private Button buttonDelivo;
    private EditText pickAddress;
    private EditText dropAddress;
    private DilatingDotsProgressBar dilatingDotsProgressBar;
    private GoogleMap map;
    private View view;
    private Progress progress;

    public static DelivoParameter newInstance() {
        DelivoParameter fragment = new DelivoParameter();

        return fragment;
    }

    public DelivoParameter() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_delivo_parameter, container, false);

        init(view);


        dilatingDotsProgressBar = (DilatingDotsProgressBar) view.findViewById(R.id.progress);
        inflateParameterFragment();

        return view;
    }

    private void inflateParameterFragment() {
//        PickLocation pickLocationFragment = new PickLocation();
//
//        FragmentManager fragmentManager = getFragmentManager();
//                        fragmentManager.
//                                beginTransaction().
//                                replace(R.id.Parametercontainer,pickLocationFragment).
//                                commit();


    }

    private void init(View view) {
        initMap();
        //buttonDelivo = (Button)view.findViewById(R.id.buttonDelivo);
        pickAddress = (EditText)view.findViewById(R.id.pickAddress);
        dropAddress = (EditText)view.findViewById(R.id.dropAddress);

        //buttonDelivo.setOnClickListener(new DelivoListener());
    }

    private void initMap() {

        final LatLng currentLocation = new LatLng(24.8571506,67.0455643);
        final LatLng KIEL = new LatLng(53.551, 9.993);

        map = ((MapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment))
                .getMap();

        final MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLocation);

        final Marker pickLocationMarker = map.addMarker(markerOptions);


        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);


        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                try {
                    progress = NavigationUtils.getProgress(false);
                    progress.show(getFragmentManager(),"");

                    Geocoder geo = new Geocoder(getActivity(), Locale.getDefault());
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
                            //pickAddress.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());


                            mListener.onPickLocMapClick(location);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace(); // getFromLocation() may sometimes fail
                }

            }
        });


    }
    void startAnim(){
        ProgressView progress = new ProgressView();
        progress.show(getFragmentManager(),"");


    }

    void stopAnim(){
        view.findViewById(R.id.avloadingIndicatorView).setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnDelivoParameterInteration) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ConfirmationFragmentInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnDelivoParameterInteration {

        public void onFragmentInteraction(Uri uri);
        public void onPickLocMapClick(String location);
    }

    private class DelivoListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
//            NavigationUtils.showDropLocationScreen(getFragmentManager());

//            List<DeleveryOrderItem> paramList = new ArrayList<DeleveryOrderItem>();
//
//            DeleveryOrderItem param = new DeleveryOrderItem(
//                    pickAddress.getText().toString(),
//                    dropAddress.getText().toString()
//                    ,"sabih");
//
//            paramList.add(param);
//
//            NewOrder newOrder = new NewOrder("SUN2",
//                                            "lorem ipsum description",
//                                                "userId",paramList);
//
//            dilatingDotsProgressBar.showNow();
//
//            if(DevicePreferences.getInstance().getUser()!=null){
//
//                OrderBAL.createOrder(newOrder, new CreateOrderResponse() {
//                    @Override
//                    public void orderCreatedSuccessfully(ResponseNewOrder body) {
//                        dilatingDotsProgressBar.hideNow();
//                        Toast.makeText(getActivity(),"order created successfully",Toast.LENGTH_SHORT).show();
//                        NavigationUtils.showConfirmationScreen(getFragmentManager(),body.getData().getOrderid());
//                    }
//
//                    @Override
//                    public void tokenExpired() {
//                        Toast.makeText(getActivity(),"token expired",Toast.LENGTH_SHORT).show();
//
//                    }
//
//                    @Override
//                    public void failure(String s) {
//
//                    }
//                });
//
//            }
//
//            else{
//                NavigationUtils.showRegistrationScreen(getActivity().getFragmentManager());
//            }
//
        }
    }
}
