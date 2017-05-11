package com.attribe.delivo.app.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.attribe.delivo.app.R;
import com.attribe.delivo.app.databinding.StepThreeFragmentBinding;
import com.attribe.delivo.app.utils.ReverseGeoLocationTask;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

import java.util.concurrent.ExecutionException;

import static com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DropLocationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DropLocationFragment extends Fragment implements OnMapReadyCallback{

    private OnDropLocationFragmentInteractionListener mListener;
    StepThreeFragmentBinding viewBinding;
    private MapView mapView;
    private GoogleMap mMap;
    private LatLng cameradropLocation;
    private TextView droplocation_txtview;
    private Button addDropkloc_btn;


    public DropLocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.step_three_fragment, container, false);
         initViews(savedInstanceState);
        return viewBinding.getRoot();
    }
    //================================================Views Initialization ==================================//

    private void getViewsRefernce() {
        mapView = viewBinding.mymapview;
        droplocation_txtview=viewBinding.droplocname;
        addDropkloc_btn=viewBinding.confirmDroplocationBtn;


    }

    private void initViews(Bundle savedInstanceState) {
        getViewsRefernce();
        if (savedInstanceState == null) {
            mapView.onCreate(savedInstanceState);
        }
        mapView.getMapAsync(this);
      addDropkloc_btn.setOnClickListener(new onDropLocationListner());
    }

    //===========================================Helper Methods===============================================//
    private void setMapTheme(GoogleMap mMap)
    {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.map_style));



            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

    }


//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
    //===========================================LifeCycle Callbacks =========================================//

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDropLocationFragmentInteractionListener) {
            mListener = (OnDropLocationFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setMapTheme(mMap);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

        //mMap.setOnCameraMoveListener(new OnCameraMovingListner());
        mMap.setOnCameraIdleListener(new OnCameraStopListner());//when camera stops


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public interface OnDropLocationFragmentInteractionListener
    {
        void onDropLocationFragmentInteraction(String droplocation_name);

    }
    //========================================= Local Listners ================================================================//

    /**
     * This class will calling the geocoder when camera stops at certain position
     */
    private class OnCameraStopListner implements GoogleMap.OnCameraIdleListener {
        @Override
        public void onCameraIdle() {


            try {

                cameradropLocation = mMap.getCameraPosition().target;
                String pick_Location = new ReverseGeoLocationTask(getContext()).execute(cameradropLocation.latitude, cameradropLocation.longitude).get();
                droplocation_txtview.setText("" + pick_Location);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }

    private class onDropLocationListner implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            if(!droplocation_txtview.getText().toString().equals(""))
            {
                mListener.onDropLocationFragmentInteraction(droplocation_txtview.getText().toString());
            }


        }
    }
}
