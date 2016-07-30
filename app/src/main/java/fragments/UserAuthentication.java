package fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.attribe.delivo.app.NavigationUtils;
import com.attribe.delivo.app.R;
import models.response.ResponseNewOrder;
import network.bals.OrderBAL;
import network.interfaces.CreateOrderResponse;
import utils.DevicePreferences;

import static com.attribe.delivo.app.NavigationUtils.showConfirmationScreen;

/**
 * Created by Maaz on 7/28/2016.
 */
public class UserAuthentication extends Fragment {


    private View view;
    private Button logIn , signUp;
    private String orderID ="";
    private EditText userAuthName , userAuthNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_user_auth, container, false);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setHasOptionsMenu(true);

        initViews(view);
        return view;

    }

    private void initViews(View view) {

        userAuthName = (EditText)view.findViewById(R.id.user_auth_name);
        userAuthNumber = (EditText)view.findViewById(R.id.user_auth_number);

        logIn  = (Button)view.findViewById(R.id.login_btn);
        signUp = (Button)view.findViewById(R.id.signUp_btn);

        logIn.setOnClickListener(new LogInListner());
        signUp.setOnClickListener(new SignUpListner());

    }

    private class LogInListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            String userName, phone;
            userName = userAuthName.getText().toString();
            phone = userAuthNumber.getText().toString();

            if (!isValidPhoneNumber(phone)) {
                userAuthNumber.setError("Invalid Phone Number");
                UserAuthentication fragment = (UserAuthentication) getFragmentManager().findFragmentById(R.id.auth_container);
                getFragmentManager().beginTransaction()
                        .detach(fragment)
                        .attach(fragment)
                        .commit();
            } else {

                if (DevicePreferences.getInstance().getUser() != null) {
                    if (userName == DevicePreferences.getInstance().getUser().data.getUsername() || phone == DevicePreferences.getInstance().getUser().data.getMobilenum()) {
                        orderID = getArguments().getString("KEY_ORDERID");
                        showConfirmationScreen(getFragmentManager(), orderID);
                    }
                } else {
                    Toast.makeText(getActivity(), "Sorry user is not available , You have to register first ", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private boolean isValidPhoneNumber(String phone) {
        if (phone != null && phone.length() == 13) {
            return true;
        }
        return false;
    }

    private class SignUpListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            NavigationUtils.showRegistrationScreen(getFragmentManager());
        }
    }
}
