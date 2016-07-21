package screens;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.attribe.delivo.app.NavigationUtils;
import com.attribe.delivo.app.R;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;
import models.NewUser;
import network.bals.SignupBAL;
import network.interfaces.SignupUserResponse;


public class ScreenRegistration extends FragmentActivity {

    public static final String STATUS_ORDER_DEFERRED = "222";
    private EditText fieldName,fieldFullName,fieldEmail,fieldNumber;
    private Button buttonSignup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_registration);
        init();
    }


    private void init(/**View view**/) {
        fieldName = (EditText)findViewById(R.id.name);
        fieldFullName = (EditText) findViewById(R.id.fullname);
        fieldEmail = (EditText)findViewById(R.id.email);
        fieldNumber = (EditText)findViewById(R.id.number);

        buttonSignup = (Button)findViewById(R.id.signup);
        buttonSignup.setOnClickListener(new SignupListener());
    }


    private class SignupListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            String userName,fullname,phone,email;
            userName = fieldName.getText().toString();
            fullname = fieldFullName.getText().toString();
            email= fieldEmail.getText().toString();
            phone = fieldNumber.getText().toString();

            NewUser newUser = new NewUser(userName,phone,email,fullname);

            SignupBAL.createUser(newUser, new SignupUserResponse() {
                @Override
                public void OnUserCreatedAndLoggedIn() {


                    NavigationUtils.showConfirmationScreen(ScreenRegistration.this, STATUS_ORDER_DEFERRED);
                }

                @Override
                public void OnUserCreated() {

                }


                @Override
                public void OnError() {

                }
            });
        }
    }
}
