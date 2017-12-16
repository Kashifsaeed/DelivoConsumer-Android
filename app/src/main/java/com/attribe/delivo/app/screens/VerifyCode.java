package com.attribe.delivo.app.screens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.attribe.delivo.app.R;
import com.attribe.delivo.app.databinding.VerifyCodeActivityBinding;

import com.attribe.delivo.app.dialouge.AlertDialouge;
import com.attribe.delivo.app.models.response.AuthenticationResponse;
import com.attribe.delivo.app.models.response.ErrorBody;
import com.attribe.delivo.app.models.response.MessageResponse;
import com.attribe.delivo.app.bals.UserAutenticationBAL;
import com.attribe.delivo.app.interfaces.ResponseCallback;
import com.attribe.delivo.app.utils.DevicePreferences;

public class VerifyCode extends AppCompatActivity {
    // VerifyCodeActivityBin
    private VerifyCodeActivityBinding parentView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentView = DataBindingUtil.setContentView(VerifyCode.this, R.layout.verify_code_activity);
        setEventListner();
    }

    //============================================Helper Mehods =================================================//
    private void setEventListner() {
        parentView.pinSentBtn.setOnClickListener(new PinCodeListner());
        parentView.resetpinBtn.setOnClickListener(new ResentCodeListner());
    }


    private void showProgress(String message) {
        progressDialog = ProgressDialog.show(VerifyCode.this, "", message, false);
    }

    private void hideProgress() {
        progressDialog.dismiss();
    }

    private void verifyPin(String phone, String password, String pin) {
        showProgress("Verifying User...");
        UserAutenticationBAL.verifyUserPin(getApplicationContext(), phone, password, pin, new ResponseCallback<AuthenticationResponse>() {
            @Override
            public void onSuccess(AuthenticationResponse response) {
                hideProgress();
                //recieves an auth_token and do naviagtion
                startActivity(new Intent(VerifyCode.this, DeliveryOptionScreen.class));


            }

            @Override
            public void OnResponseFailure(ErrorBody error_body) {
                hideProgress();
                AlertDialouge.showDialoge(VerifyCode.this, error_body.getError_msg());

            }

            @Override
            public void onfailure(String message) {
                hideProgress();
                AlertDialouge.showError(VerifyCode.this, message);
            }
        });

    }

    private void resentPin(String phone) {
        showProgress("loading...");
        UserAutenticationBAL.resentPin(getApplicationContext(), phone, new ResponseCallback<MessageResponse>() {
            @Override
            public void onSuccess(MessageResponse response) {
                hideProgress();
                Toast.makeText(getApplicationContext(), "" + response.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnResponseFailure(ErrorBody error_body) {
                hideProgress();
                AlertDialouge.showDialoge(VerifyCode.this, error_body.getError_msg());


            }

            @Override
            public void onfailure(String message) {
                hideProgress();
                AlertDialouge.showError(VerifyCode.this, message);

            }
        });

    }


    //===========================================Click Listners ==================================================//
    private class PinCodeListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (DevicePreferences.getInstance().getuserInfo() != null) {
                verifyPin(DevicePreferences.getInstance().getuserInfo().getPhone_no(),
                        DevicePreferences.getInstance().getuserInfo().getPassword(),
                        parentView.pincodeEdittxt.getText().toString().trim());

            }
        }
    }

    private class ResentCodeListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (DevicePreferences.getInstance().getuserInfo() != null) {
                resentPin(DevicePreferences.getInstance().getuserInfo().getPhone_no()
                       );


            }
        }
    }
}
