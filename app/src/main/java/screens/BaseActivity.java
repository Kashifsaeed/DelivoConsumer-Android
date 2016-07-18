package screens;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.attribe.delivo.app.R;
import utils.LocationHelper;

/**
 * Created by Sabih Ahmed on 11-Jul-16.
 */
public abstract class BaseActivity extends AppCompatActivity{

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 100;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void initToolBar(View view){

         myToolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
//        myToolbar.setTitleTextColor(getResources().getColor(R.color.text_color_button));
        //setSupportActionBar(myToolbar);

        onToolBarInit(myToolbar);

    }

    public void getLocationPermission(Activity activity) {

        //see if location is granted previously
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                onLocationRequirePermission();

                /**
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
                **/
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

        }


        else{
            //user has granted the location previously, call the method to get location
            //trigger abstract method here
            onLocationPermittedInPast();
            //getLocation();

        }
    }


    private void getLocation(){


        LocationHelper.getLocationRequest();


    }

    protected abstract void onLocationRequirePermission();
    protected abstract void onLocationPermittedInPast();
    protected abstract void onToolBarInit(Toolbar toolbar);
}
