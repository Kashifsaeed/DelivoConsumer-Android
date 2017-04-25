package com.attribe.delivo.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import models.NewUser;
import models.User;
import network.bals.SignupBAL;
import network.interfaces.SignupUserResponse;
import utils.DevicePreferences;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsumerSignUp extends AppCompatActivity {
    private EditText username,fullname,mobileno,email;
    private Button signUp;
    private ProgressDialog mprogress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_sign_up);
        initViews();

    }

    private void initViews() {
        username= (EditText) findViewById(R.id.name);
        fullname= (EditText) findViewById(R.id.fullname);
        mobileno= (EditText) findViewById(R.id.number);
        email= (EditText) findViewById(R.id.email);
        signUp= (Button) findViewById(R.id.signup);
        signUp.setOnClickListener(new SignUpListner());
    }

    //===================================Validate Input fields ================================================//
    private boolean isValidPhoneNumber(String phone)
    {
        if (phone != null && phone.length() == 10) {
            return true;
        }
        return false;
    }
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //==================================== Helper Methods ========================================================//
    private void openLogin(){
        startActivity(new Intent(ConsumerSignUp.this,ConsumerLogin.class));

    }
    private void hideprogress(){
        mprogress.dismiss();


    }
    private void showProgress(String message){

        mprogress= ProgressDialog.show(ConsumerSignUp.this,"",message,false);
    }

//====================================   Button Listners ===========================================================//
    private class SignUpListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String name=username.getText().toString();
            String cell=mobileno.getText().toString();
            String useremail=email.getText().toString();
            String userfullname=fullname.getText().toString();
            if(!isValidEmail(useremail)){

                email.setError("Invalid Email");

            }
            if(!isValidPhoneNumber(cell)){
                mobileno.setError("Invalid number");

            }
            else {
                NewUser user=new NewUser(name,cell,useremail,userfullname);
                showProgress("Loading...");
                SignupBAL.userSignUp(user, new SignupUserResponse() {
                    @Override
                    public void OnUserCreatedAndLoggedIn() {

                    }

                    @Override
                    public void OnUserCreated(User data) {
                        hideprogress();
                        Toast.makeText(getApplicationContext(),"Successfully SignUp"+data.getData().getFullname(),Toast.LENGTH_SHORT).show();
                        User.getInstance(data);
                        DevicePreferences.getInstance().init(getApplicationContext()).setdelivoUser(User.getInstance());
                        openLogin();

                    }

                    @Override
                    public void OnuserAlreadyexits() {
                        hideprogress();
                        Toast.makeText(getApplicationContext(),"User with this number already exist",Toast.LENGTH_SHORT).show();
                    }


                    @Override
                    public void OnError() {
                        hideprogress();
                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();

                    }
                });

            }


        }

    }
}
