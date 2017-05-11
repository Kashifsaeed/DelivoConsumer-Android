package com.attribe.delivo.app.screens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.attribe.delivo.app.R;
import com.attribe.delivo.app.databinding.SigninScreenActivityBinding;

import com.attribe.delivo.app.dialouge.AlertDialouge;
import com.attribe.delivo.app.models.response.AuthenticationResponse;
import com.attribe.delivo.app.network.bals.UserAutenticationBAL;
import com.attribe.delivo.app.network.interfaces.ResponseCallback;
import com.attribe.delivo.app.utils.ValidationUtills;

public class SignInScreen extends AppCompatActivity {
    //private
    //DataBindingUtilS
   private SigninScreenActivityBinding binding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.signin_screen_layout);
        binding= DataBindingUtil.setContentView(SignInScreen.this, R.layout.signin_screen_activity);
        setEventListners();
    }
//======================================Helper Methods ====================================================//
    private void setEventListners()
    {
        binding.loginBtn.setOnClickListener(new LoginListner());
        binding.accountsignupBtn.setOnClickListener(new SignUpNavigation());
        binding.facebookloginBtn.setOnClickListener(new VerifyListner());

    }
    private boolean isValidate(String phone,String password)
    {
        if(phone.equals(""))
        {
            binding.loginuserPhone.setError("missing phone no");
            return false;
        }
       else if(password.equals(""))
        {
            binding.loginuserPassword.setError("missing password");
            return false;
        }
        else if (!ValidationUtills.isValidPhoneNumber(phone))
        {
           binding.loginuserPhone.setError("enter valid no");
            return false;
        }


        return true;
    }

    private void showProgress(String message)
    {
        progressDialog= ProgressDialog.show(SignInScreen.this,"",message,false);
    }

    private void hideProgress()
    {
        progressDialog.dismiss();
    }


    private void signInUser(String phone,String password)
    {
        showProgress("loading....");
        UserAutenticationBAL.signInUser(getApplicationContext(), phone, password, new ResponseCallback<AuthenticationResponse>() {
            @Override
            public void onSuccess(AuthenticationResponse response)
            {
                hideProgress();
                //navigate to main screen
                startActivity(new Intent(SignInScreen.this,SelectOptionScreen.class));

            }

            @Override
            public void OnResponseFailure(String error_body)
            {
                hideProgress();
                AlertDialouge.showDialoge(SignInScreen.this,error_body);

            }

            @Override
            public void onfailure(String message) {
                hideProgress();
                AlertDialouge.showError(SignInScreen.this,message);

            }
        });
    }
//===========================================Click Listners ==================================================//
    private class LoginListner implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            if (isValidate(binding.loginuserPhone.getText().toString().trim(), binding.loginuserPassword.getText().toString().trim()))
            {
                signInUser(binding.loginuserPhone.getText().toString().trim(),
                        binding.loginuserPassword.getText().toString().trim());

            }
        }
    }

    private class SignUpNavigation implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {

              startActivity(new Intent(SignInScreen.this,SignUpScreen.class));

        }
    }

    private class VerifyListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //startActivity(new Intent(SignInScreen.this,VerifyCode.class));

        }
    }
}
