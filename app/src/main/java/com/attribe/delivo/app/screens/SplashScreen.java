package com.attribe.delivo.app.screens;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.attribe.delivo.app.Extras.AppConstants;
import com.attribe.delivo.app.R;
import com.attribe.delivo.app.interfaces.OnLocationPermmisionListner;
import com.attribe.delivo.app.utils.DevicePreferences;
import com.attribe.delivo.app.utils.LocationPermissionUtil;

public class SplashScreen extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private  long SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        DevicePreferences.getInstance().init(SplashScreen.this);

       LocationPermissionUtil.askForPermission( SplashScreen.this, new OnLocationPermmisionListner() {
           @Override
           public void onPermissionAlreadyGranted() {//invoked when permission is already granted
               openNewScreen();
           }

           @Override
           public void onPermisionRequested() {//invoked when permission is not granted
               ActivityCompat.requestPermissions(SplashScreen.this, new String[]{AppConstants.FINE_LOCATION}, PERMISSION_REQUEST_CODE);


           }

           @Override
           public void onPermissionBeforeDenied() {//invoked when permission is denied before
               ActivityCompat.requestPermissions(SplashScreen.this, new String[]{AppConstants.FINE_LOCATION}, PERMISSION_REQUEST_CODE);


           }
       });
    }



    private void openNewScreen(){

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {


                Intent i;
                if (isUserExist()) {
                    i = new Intent(SplashScreen.this, DeliveryOptionScreen.class);

                } else {

                    i = new Intent(SplashScreen.this, LoginScreen.class);
                }
                startActivity(i);
                finish();

            }
        }, SPLASH_TIME_OUT);



    }

    private boolean isUserExist() {
        if(DevicePreferences.getInstance().getUserProfile()!=null)
        {
        if (DevicePreferences.getInstance().getUserProfile().isLogin()){
            return true;
        }
        }
        return false;
    }

    private void showDialogue(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(AppConstants.deny_tittle);
        alertDialogBuilder.setMessage(AppConstants.deny_dialogue);
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        LocationPermissionUtil.showdialougeRequestpermission(SplashScreen.this,PERMISSION_REQUEST_CODE);

                    }
                });
//

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    // executes when Permission Is Granted Or Allow
                    openNewScreen();

                }
                else
                {
                    // executes when Permission Is Denied
                    showDialogue();


                }

                }
    }
}
