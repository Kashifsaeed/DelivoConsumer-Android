package com.attribe.delivo.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import network.bals.LoginBAL;
import network.interfaces.LoginUserResponse;
import utils.DevicePreferences;


public class ScreenSplash extends Activity {

    final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_splash);
        DevicePreferences.getInstance().init(this);
        runSplash();


    }
    private void runSplash(){
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {

                Intent i;

                if (DevicePreferences.getInstance().getUser()!=null)

                {
                    i = new Intent(ScreenSplash.this,CustomPickLocation.class);
                }
                else
                {
                    i = new Intent(ScreenSplash.this, MainScreen.class);
                }
                startActivity(i);
                finish();

            }
        }, SPLASH_TIME_OUT);

    }
    private void runThread(){

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
                    // startActivity(new Intent(getApplicationContext(), MapBox.class));
                    finish();
                }
            }
        };
        welcomeThread.start();

    }
}
