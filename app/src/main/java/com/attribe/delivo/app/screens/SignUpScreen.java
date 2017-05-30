package com.attribe.delivo.app.screens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.attribe.delivo.app.R;
import com.attribe.delivo.app.databinding.SignupScreenActivityBinding;

import com.attribe.delivo.app.dialouge.AlertDialouge;
import com.attribe.delivo.app.models.response.ErrorBody;
import com.attribe.delivo.app.models.response.SignUpResponse;
import com.attribe.delivo.app.bals.UserAutenticationBAL;
import com.attribe.delivo.app.interfaces.ResponseCallback;
import com.attribe.delivo.app.utils.ValidationUtills;

public class SignUpScreen extends AppCompatActivity {
   private SignupScreenActivityBinding binding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=  DataBindingUtil.setContentView(SignUpScreen.this, R.layout.signup_screen_activity);
        setEventListners();
    }


//============================================ HelperMethods ===================================================//
private void setEventListners()
{
    binding.signupBtn.setOnClickListener(new SignUpListner());
    binding.alreadySignUp.setOnClickListener(new AleadySignUpListner());

}
    private void showProgress(String message)
    {
        progressDialog= ProgressDialog.show(SignUpScreen.this,"",message,false);
    }

    private void hideProgress()
    {
        progressDialog.dismiss();
    }

    private boolean isValidate(String name,String phone,String email,String password,String confirm_password)
    {
        if(name.equals(""))
        {
           binding.signupUserName.setError("missing name");
            return false;
        }
        else if(email.equals("")){
            binding.signupEmailAddress.setError("missing email");
            return false;
        }
        else if(phone.equals("")){
            binding.signupUserPhone.setError("missing phone no");
            return false;
        }

        else if(password.equals("")){
            binding.signupUserPassword.setError("missing password");
            return false;
        }
        else if(confirm_password.equals("")){
            binding.signupUserConfirmpassword.setError("missing confirm password");
            return false;
        }
        else if(!ValidationUtills.isValidEmail(email))
        {
            binding.signupEmailAddress.setError("enter valid email");
            return false;

        }
        else if(!ValidationUtills.isValidPhoneNumber(phone))
        {
            binding.signupUserPhone.setError("enter valid no");
            return false;
        }
        else if (!password.equals(confirm_password))
        {
            binding.signupUserConfirmpassword.setError("password does not match");
            return false;
        }



        return true;
    }
    private void userSignUp(String name,String email,String phone,String password,String confirm_passwrod)
    {
        showProgress("wait...");
        UserAutenticationBAL.signUpUser(SignUpScreen.this, name, email, phone, password, confirm_passwrod, new ResponseCallback<SignUpResponse>() {
            @Override
            public void onSuccess(SignUpResponse response)
            {
                hideProgress();
              //  Toast.makeText(getApplicationContext(),response.getMessage(),Toast.LENGTH_SHORT);
                startActivity(new Intent(SignUpScreen.this,VerifyCode.class));
            }

            @Override
            public void OnResponseFailure(ErrorBody error_body)
            {
                hideProgress();
              //  Toast.makeText(getApplicationContext(),""+error_body,Toast.LENGTH_SHORT).show();
                AlertDialouge.showDialoge(SignUpScreen.this,error_body.getError_msg());
            }


            @Override
            public void onfailure(String message) {
               // Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                hideProgress();
                AlertDialouge.showError(SignUpScreen.this,message);

            }
        });
    }
    //===========================================Click Listners ==================================================//
    private class SignUpListner implements View.OnClickListener {
        @Override
        public void onClick(View view)


        {
            if(isValidate(binding.signupUserName.getText().toString().trim(),
                   "92"+ binding.signupUserPhone.getText().toString().trim(),binding.signupEmailAddress.getText().toString().trim(),
                    binding.signupUserPassword.getText().toString().trim(),
                    binding.signupUserConfirmpassword.getText().toString().trim()))
            {
                userSignUp(binding.signupUserName.getText().toString().trim(),
                        binding.signupEmailAddress.getText().toString().trim(),
                        "92"+binding.signupUserPhone.getText().toString().trim(),
                        binding.signupUserPassword.getText().toString().trim(),
                        binding.signupUserConfirmpassword.getText().toString().trim());
            }

        }
    }

    private class AleadySignUpListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }
}
