package com.attribe.delivo.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainScreen extends AppCompatActivity {
    private Button signUp,login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        initViews();
    }
//=================================== Initialize views ====================================//

    private void initViews() {
        signUp= (Button) findViewById(R.id.btn_signUp_switch);
        login= (Button) findViewById(R.id.btn_login_switch);
        login.setOnClickListener(new LoginListner());
        signUp.setOnClickListener(new SignUpListner());



    }
//===================================Nagigations Methods ====================================//
    private void navigateSignUp(){
        startActivity(new Intent(MainScreen.this,ConsumerSignUp.class));


    }
    private void navigateLogin(){

        startActivity(new Intent(MainScreen.this,ConsumerLogin.class));

    }

//===================================Buttons Listners ====================================//
    private class LoginListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            navigateLogin();

        }
    }

    private class SignUpListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            navigateSignUp();

        }
    }
}
