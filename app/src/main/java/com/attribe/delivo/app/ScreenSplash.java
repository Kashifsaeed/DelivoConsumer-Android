package com.attribe.delivo.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import network.bals.LoginBAL;
import network.interfaces.LoginUserResponse;
import utils.DevicePreferences;


public class ScreenSplash extends Activity {

    final int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_splash);
        DevicePreferences.getInstance().init(this);

        Thread welcomeThread = new Thread() {
            int wait = 0;

            @Override
            public void run() {
                try {
                    super.run();
                    while (wait < SPLASH_TIME_OUT) {
                        sleep(100);
                        wait += 100;
                    }
                } catch (Exception e) {
                    System.out.println("EXc=" + e);
                } finally {
                    startActivity(new Intent(getApplicationContext(), PicknDropLocations.class));
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}
