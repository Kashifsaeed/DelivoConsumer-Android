package com.attribe.delivo.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import network.bals.LoginBAL;
import network.interfaces.LoginUserResponse;
import utils.DevicePreferences;

public class ConsumerLogin extends AppCompatActivity {
   private EditText user_auth_name,user_auth_number;
    private Button Login_btn,swictch_to_signup;
    private ProgressDialog mprogress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_login);
        initViews();
    }

    private void initViews() {
        user_auth_name= (EditText) findViewById(R.id.user_auth_name);
        user_auth_number= (EditText) findViewById(R.id.user_auth_number);
        Login_btn= (Button) findViewById(R.id.login_btn);
        swictch_to_signup= (Button) findViewById(R.id.signUp_btn);
        Login_btn.setOnClickListener(new LoginListner());
        swictch_to_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private boolean isValidusername(String username) {
        if (username != null && username.length() == 10) {
            return true;
        }
        return false;
    }
    private boolean isValiduserpassword( String password){
        if (password != null && password.length() == 10) {
            return true;
        }
        return false;

    }
    private void hideprogress(){
        mprogress.dismiss();


    }
    private void showProgress(String message){

        mprogress= ProgressDialog.show(ConsumerLogin.this,"",message,false);
    }

    private class LoginListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String username = user_auth_name.getText().toString();
            String user_password = user_auth_number.getText().toString();

            if (!isValidusername(username)) {
                user_auth_name.setError("Invalid name");
            }
            if (!isValiduserpassword(user_password)) {
                user_auth_number.setError("Invalid password");
            } else {
                  showProgress("Loading...");
                LoginBAL.userLogin(username, user_password, new LoginUserResponse() {
                    @Override
                    public void OnLoggedIn() {
                        hideprogress();
                        Toast.makeText(getApplicationContext(), "Successfully Login", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ConsumerLogin.this, CustomPickLocation.class));
                    }

                    @Override
                    public void OnLoggedInFailed() {
                        hideprogress();
                        Toast.makeText(getApplicationContext(), "Server not respond", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onLoginError() {
                        hideprogress();
                        Toast.makeText(getApplicationContext(), "Bad Credetinals", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }
    }
}
