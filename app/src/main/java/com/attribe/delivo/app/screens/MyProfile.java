package com.attribe.delivo.app.screens;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.attribe.delivo.app.R;
import com.attribe.delivo.app.bals.UserProfileBAL;
import com.attribe.delivo.app.databinding.MyProfileActivityBinding;
import com.attribe.delivo.app.dialouge.AlertDialouge;
import com.attribe.delivo.app.interfaces.ResponseCallback;
import com.attribe.delivo.app.models.request.UpdatePassword;
import com.attribe.delivo.app.models.request.UpdateUserProfile;
import com.attribe.delivo.app.models.response.ErrorBody;
import com.attribe.delivo.app.models.response.MessageResponse;
import com.attribe.delivo.app.models.response.UserProfile;
import com.attribe.delivo.app.network.RestClient;
import com.attribe.delivo.app.utils.DevicePreferences;
import com.attribe.delivo.app.utils.SnackBars;
import com.attribe.delivo.app.utils.ValidationUtills;

public class MyProfile extends AppCompatActivity implements View.OnClickListener{
    MyProfileActivityBinding binding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(MyProfile.this,R.layout.my_profile_activity);
        loadData();

    }

    private void loadData()
    {
        DevicePreferences.getInstance().init(this);
        initToolbar();
        setListners();
        if(DevicePreferences.getInstance().getUserProfile()!=null)
        {
            binding.userProfilename.setText(DevicePreferences.getInstance().getUserProfile().getName());
            binding.userProfileEmail.setText(DevicePreferences.getInstance().getUserProfile().getEmail());
            binding.userProfilenumber.setText(DevicePreferences.getInstance().getUserProfile().getPhone());
            binding.userProfilenameEdittxt.setText(DevicePreferences.getInstance().getUserProfile().getName());
            binding.userProfileEmailEdittxt.setText(DevicePreferences.getInstance().getUserProfile().getEmail());
        }


    }

    private void setListners() {
        binding.editProfileBtn.setOnClickListener(this);
        binding.saveBtn.setOnClickListener(this);
        binding.resetNavBtn.setOnClickListener(this);
        binding.resetPasswordBtn.setOnClickListener(this);

    }

    private void initToolbar() {
        binding.toolbar.myToolbar.setTitle(R.string.my_profile_tittle);
        binding.toolbar.myToolbar.setNavigationIcon(R.drawable.ic_back_nav);
        binding.toolbar.myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    /**
     * this method will call an updateuserprofile bal method of userprofilebal
     * @param profile
     */
    private void updateProfile(UpdateUserProfile profile)
    {
        showProgress("Waiting..");
        UserProfileBAL.updateUserProfile(this, profile, new ResponseCallback<UserProfile>() {
            @Override
            public void onSuccess(UserProfile response) {
                hideProgress();
                SnackBars.showMessage(binding.getRoot(),"Successfully update profile");


            }

            @Override
            public void OnResponseFailure(ErrorBody error_body)
            {
                hideProgress();
                AlertDialouge.showError(MyProfile.this,error_body.getError_msg());
            }


            @Override
            public void onfailure(String message) {
                hideProgress();
                SnackBars.showMessage(binding.getRoot(),message);

            }
        });

    }
    private void updatePassword(UpdatePassword password){
        showProgress("Waiting..");
        UserProfileBAL.resetPassword(MyProfile.this, password, new ResponseCallback<MessageResponse>() {
            @Override
            public void onSuccess(MessageResponse response) {
                hideProgress();
                SnackBars.showMessage(binding.getRoot(),response.getMessage());
                binding.editProfileLayout.setVisibility(View.VISIBLE);
                binding.resetPasswordLayout.setVisibility(View.GONE);


            }

            @Override
            public void OnResponseFailure(ErrorBody error_body) {
                hideProgress();
                AlertDialouge.showError(MyProfile.this,error_body.getError_msg());


            }

            @Override
            public void onfailure(String message) {
                hideProgress();
                SnackBars.showMessage(binding.getRoot(),message);

            }
        });
    }

    /**
     * this method will validate an input fields
     * @param name
     * @param email
     * @return
     */
    private boolean isValidate(String name, String email)
    {
        if (name.equals("")) {
            binding.userProfilenameEdittxt.setError("missing name");
            return false;
        } else if (email.equals(""))
        {
            binding.userProfileEmailEdittxt.setError("missing password");
            return false;

        }
        else if(!ValidationUtills.isValidEmail(email))
        {
            binding.userProfileEmailEdittxt.setError("enter valid email");
            return false;

        }


        return true;
    }
    private boolean isPaswordValid(String old_pass,String new_pass,String confirm_pass)
    {
        if(old_pass.equals("")||new_pass.equals("")||confirm_pass.equals(""))
        {
            SnackBars.showMessage(binding.getRoot(),"Fields missing");
            return false;
        }
        else if (!new_pass.equals(confirm_pass)) {
            SnackBars.showMessage(binding.getRoot(), "Password does not match");
            return false;
        }


        return true;
    }
    private void showProgress(String message) {
        progressDialog = ProgressDialog.show(MyProfile.this, "", message, false);
    }

    private void hideProgress() {
        progressDialog.dismiss();
    }


    @Override
    public void onClick(View view) {
        int item = view.getId();
        switch (item)
        {
            case R.id.edit_profile_btn:
                binding.profileLayout.setVisibility(View.GONE);
                binding.editProfileLayout.setVisibility(View.VISIBLE);
                break;

            case R.id.save_btn:
                //call to edit profile request to server
                if(isValidate(binding.userProfilenameEdittxt.getText().toString().trim(),binding.userProfileEmailEdittxt.getText().toString().trim()))
                {
                    UpdateUserProfile userProfile = new UpdateUserProfile(binding.userProfilenameEdittxt.getText().toString().trim(), binding.userProfileEmailEdittxt.getText().toString().trim());
                    updateProfile(userProfile);
                }
                break;
            case R.id.reset_nav_btn:
                binding.profileLayout.setVisibility(View.GONE);
                binding.editProfileLayout.setVisibility(View.GONE);
                binding.resetPasswordLayout.setVisibility(View.VISIBLE);

                break;
            case R.id.reset_password_btn:
                if(isPaswordValid(binding.oldPassword.getText().toString().trim(),binding.newResetPassword.getText().toString().trim(),
                        binding.confirmResetPassword.getText().toString().trim())) {
                    UpdatePassword password = new UpdatePassword(binding.oldPassword.getText().toString().trim(), binding.newResetPassword.getText().toString().trim(),
                            binding.confirmResetPassword.getText().toString().trim());
                    updatePassword(password);
                }
                break;
        }

    }
}
