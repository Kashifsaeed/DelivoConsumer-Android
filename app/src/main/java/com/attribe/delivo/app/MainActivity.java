package com.attribe.delivo.app;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;
import fragments.ConfirmationScreen;
import screens.*;

public class MainActivity extends Activity implements
        DelivoParameter.OnDelivoParameterInteration,
        ConfirmationScreen.ConfirmationFragmentInteraction,
        GoogleApiClient.OnConnectionFailedListener {


    private DilatingDotsProgressBar progressView;
    private Button showDropLocation;
    private Button delivoButton;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        NavigationUtils.showPickLocationScreen(/*getFragmentManager()*/this);


    }

    private void initView() {
        showDropLocation = (Button)findViewById(R.id.showDropLoc);
        delivoButton =(Button)findViewById(R.id.delivo);
        showDropLocation.setOnClickListener(new showDropButtonListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onPickLocMapClick(String location) {

//        PickLocation pickLocation = (PickLocation)
//                getFragmentManager().findFragmentById(R.id.container).
//                        getFragmentManager().findFragmentById(R.id.Parametercontainer);
//
//
//
//        pickLocation.setLocationOnView(location);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private class showDropButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

//            showDelivoButton();
//            NavigationUtils.showDropLocationScreen(getFragmentManager());
        }
    }

    private void showDelivoButton() {

        showDropLocation.setVisibility(View.GONE);
        delivoButton.setVisibility(View.VISIBLE);
    }
}
