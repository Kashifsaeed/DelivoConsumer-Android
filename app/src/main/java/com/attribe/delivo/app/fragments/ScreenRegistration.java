package com.attribe.delivo.app.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.attribe.delivo.app.NavigationUtils;
import com.attribe.delivo.app.R;
import com.attribe.delivo.app.models.NewUser;
import com.attribe.delivo.app.models.User;
import com.attribe.delivo.app.network.bals.SignupBAL;
import com.attribe.delivo.app.network.interfaces.SignupUserResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Maaz on 7/28/2016.
 */
public class ScreenRegistration extends Fragment {

    public static final String STATUS_ORDER_DEFERRED = "222";
    private EditText fieldName,fieldFullName,fieldEmail,fieldNumber;
    private Button buttonSignup;
    private View view;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.screen_registration, container, false);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setHasOptionsMenu(true);

        init(view);
        return view;

    }

    private void init(View view) {
        fieldName = (EditText)view.findViewById(R.id.name);
        fieldFullName = (EditText) view.findViewById(R.id.fullname);
        fieldEmail = (EditText)view.findViewById(R.id.email);
        fieldNumber = (EditText)view.findViewById(R.id.number);

        buttonSignup = (Button)view.findViewById(R.id.signup);
        buttonSignup.setOnClickListener(new SignupListener());
    }

    private class SignupListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            String userName, fullname, phone, email;
            userName = fieldName.getText().toString();
            fullname = fieldFullName.getText().toString();
            email = fieldEmail.getText().toString();
            phone = fieldNumber.getText().toString();

            if (!isValidEmail(email)) {
                fieldEmail.setError("Invalid Email");

                if (!isValidPhoneNumber(phone)) {
                    fieldNumber.setError("Invalid Phone Number");
                }

                ScreenRegistration fragment = (ScreenRegistration)getFragmentManager().findFragmentById(R.id.fragments_container);
                getFragmentManager().beginTransaction()
                        .detach(fragment)
                        .attach(fragment)
                        .commit();
            }
            else {
                NewUser newUser = new NewUser(userName, phone, email, fullname);

                showProgress("Loading.....");
                SignupBAL.createUser(newUser, new SignupUserResponse() {
                    @Override
                    public void OnUserCreatedAndLoggedIn() {
                        hideProgress();

                        NavigationUtils.showConfirmationScreen( getFragmentManager(), STATUS_ORDER_DEFERRED);
                    }

                    @Override
                    public void OnUserCreated(User data) {

                    }


                    @Override
                    public void OnuserAlreadyexits() {

                    }


                    @Override
                    public void OnError() {
                        hideProgress();
                    }
                });
            }
        }
    }

    private boolean isValidPhoneNumber(String phone) {
        if (phone != null && phone.length() == 13) {
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

    private void showProgress(String message) {
        progressDialog=ProgressDialog.show(getActivity(),"",message,false);
    }

    private void hideProgress(){progressDialog.dismiss();}
}
