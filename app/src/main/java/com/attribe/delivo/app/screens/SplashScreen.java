package com.attribe.delivo.app.screens;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.attribe.delivo.app.R;

public class SplashScreen extends AppCompatActivity {

    private  long SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        openNewScreen();
    }
    private void openNewScreen(){

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {



//                if (isUserExist())
//                {
                   Intent i = new Intent(SplashScreen.this, LoginScreen.class);

//                }
//                else
//                {

                   // i = new Intent(SplashScreen.this, SignInScreen.class);
                //}
                startActivity(i);
                finish();

            }
        }, SPLASH_TIME_OUT);



    }
}
