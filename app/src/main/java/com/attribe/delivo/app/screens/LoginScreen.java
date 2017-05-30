package com.attribe.delivo.app.screens;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.attribe.delivo.app.interfaces.onDialogeListner;
import com.attribe.delivo.app.models.response.ErrorBody;
import com.attribe.delivo.app.utils.NavigationUtils;
import com.attribe.delivo.app.R;
import com.attribe.delivo.app.databinding.LoginActivityBinding;

import com.attribe.delivo.app.dialouge.AlertDialouge;
import com.attribe.delivo.app.models.response.AuthenticationResponse;
import com.attribe.delivo.app.bals.UserAutenticationBAL;
import com.attribe.delivo.app.interfaces.ResponseCallback;
import com.attribe.delivo.app.utils.ValidationUtills;

public class LoginScreen extends AppCompatActivity {

    private LoginActivityBinding binding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.signin_screen_layout);
        binding = DataBindingUtil.setContentView(LoginScreen.this, R.layout.login_activity);
        setEventListners();
    }

    //======================================Helper Methods ====================================================//
    private void setEventListners() {
        binding.loginBtn.setOnClickListener(new LoginListner());
        binding.accountsignupBtn.setOnClickListener(new SignUpNavigation());
        binding.facebookloginBtn.setOnClickListener(new VerifyListner());

    }

    private boolean isValidate(String phone, String password) {
        if (phone.equals("")) {
            binding.loginuserPhone.setError("missing phone no");
            return false;
        } else if (password.equals("")) {
            binding.loginuserPassword.setError("missing password");
            return false;
        } else if (!ValidationUtills.isValidPhoneNumber(phone)) {
            binding.loginuserPhone.setError("enter valid no");
            return false;
        }


        return true;
    }

    private void showProgress(String message) {
        progressDialog = ProgressDialog.show(LoginScreen.this, "", message, false);
    }

    private void hideProgress() {
        progressDialog.dismiss();
    }


    private void signInUser(String phone, String password) {
        showProgress("loading....");
        UserAutenticationBAL.signInUser(getApplicationContext(), phone, password, new ResponseCallback<AuthenticationResponse>() {
            @Override
            public void onSuccess(AuthenticationResponse response) {
                hideProgress();
                //navigate to main screen
                NavigationUtils.navigateScreen(LoginScreen.this, DeliveryOptionScreen.class);

            }

            @Override
            public void OnResponseFailure(ErrorBody error_body) {
                hideProgress();
                if (error_body.getError_code() == 422) {//this condition will execute when Account is not verified and navigate to verify code screen
                    AlertDialouge.showYesDialoge(LoginScreen.this, error_body.getError_msg(), new onDialogeListner() {
                        @Override
                        public void onYes() {
                            NavigationUtils.navigateScreen(LoginScreen.this, VerifyCode.class);

                        }

                        @Override
                        public void onCacle() {
                            //do nothing
                        }
                    });

                } else {
                    AlertDialouge.showDialoge(LoginScreen.this, error_body.getError_msg());
                }


            }

            @Override
            public void onfailure(String message) {
                hideProgress();
                AlertDialouge.showError(LoginScreen.this, message);

            }
        });
    }

    //===========================================Click Listners ==================================================//
    private class LoginListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (isValidate("92"+binding.loginuserPhone.getText().toString().trim(), binding.loginuserPassword.getText().toString().trim())) {
                signInUser("92"+binding.loginuserPhone.getText().toString().trim(),
                        binding.loginuserPassword.getText().toString().trim());

            }
        }
    }

    private class SignUpNavigation implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            // startActivity(new Intent(LoginScreen.this,SignUpScreen.class));
            NavigationUtils.navigateScreen(LoginScreen.this, SignUpScreen.class);


        }
    }

    private class VerifyListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //startActivity(new Intent(LoginScreen.this,VerifyCode.class));

        }
    }
}
