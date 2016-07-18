package com.attribe.delivo.app;

import android.app.Activity;
import android.os.Bundle;
import network.bals.LoginBAL;
import network.interfaces.LoginUserResponse;
import screens.ProgressView;
import utils.DevicePreferences;
import utils.Progress;


public class ScreenSplash extends Activity {

    private Progress progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_splash);
        DevicePreferences.getInstance().init(this);

        progress = NavigationUtils.getProgress(false);
        progress.show(getFragmentManager(),"");

        //login();
        NavigationUtils.startMainActivity(ScreenSplash.this);


    }

    private void login() {
        if(DevicePreferences.getInstance().getUser()!=null){

            //refresh token &
            //login the user

            LoginBAL.login(DevicePreferences.getInstance().getUser(), new LoginUserResponse() {
                @Override
                public void OnLoggedIn() {
                    progress.dismiss();
                    NavigationUtils.startMainActivity(ScreenSplash.this);
                }

                @Override
                public void OnLoggedInFailed() {

                }
            });

        }

        else{

            NavigationUtils.startMainActivity(ScreenSplash.this);

        }
    }


}
