package fragments;

import android.app.Fragment;
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
import models.User;
import models.response.GenerateTokenResponse;
import models.response.ResponseNewOrder;
import network.RestClient;
import network.bals.LoginBAL;
import network.bals.OrderBAL;
import network.interfaces.CreateOrderResponse;
import network.interfaces.LoginUserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

        if(DevicePreferences.getInstance().getUser()!= null){
            userAuthName.setText(DevicePreferences.getInstance().getUser().getData().getUsername());
            userAuthNumber.setText(DevicePreferences.getInstance().getUser().getData().getMobilenum());
        }

    }

    private class LogInListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            String userName, phone;
            userName = userAuthName.getText().toString();
            phone = userAuthNumber.getText().toString();

//            makeUser(userName,phone);

            if (!isValidPhoneNumber(phone)) {
                userAuthNumber.setError("Invalid Phone Number");
                UserAuthentication fragment = (UserAuthentication) getFragmentManager().findFragmentById(R.id.fragments_container);
                getFragmentManager().beginTransaction()
                        .detach(fragment)
                        .attach(fragment)
                        .commit();
            } else {

                if (DevicePreferences.getInstance().getUser() != null) {
                LoginBAL.login(DevicePreferences.getInstance().getUser(), new LoginUserResponse() {
                    @Override
                    public void OnLoggedIn() {
                        orderID = getArguments().getString("KEY_ORDERID");
                        showConfirmationScreen(getFragmentManager(), orderID);
                    }

                    @Override
                    public void OnLoggedInFailed() {
                        Toast.makeText(getActivity(), "Sorry user is not present , You have to register first ", Toast.LENGTH_LONG).show();
                    }
                });

                }
                else{
                    logIn(phone);
                }
            }
        }
    }

    private void logIn(String phone) {

        Call<GenerateTokenResponse> generateToken = RestClient.getAuthRestAdapter().login("password"
                ,phone,phone,
                "read write");
        generateToken.enqueue(new Callback<GenerateTokenResponse>() {
            @Override
            public void onResponse(Call<GenerateTokenResponse> call, Response<GenerateTokenResponse> response) {
                if(response.isSuccessful()){

                    if(response.body()!=null ){

                        DevicePreferences.getInstance().setUserToken(response.body());

                        OrderBAL.createOrder(DevicePreferences.getInstance().getOrder(), new CreateOrderResponse() {
                            @Override
                            public void orderCreatedSuccessfully(ResponseNewOrder body) {
                                Toast.makeText(getActivity(), " "+body.getData().getStatus().toString()+" , "+"Order created successfully ....", Toast.LENGTH_SHORT).show();
                                DevicePreferences.getInstance().setNewOrderResponse(body);
                                NavigationUtils.showConfirmationScreen(getFragmentManager(),body.getData().getOrderid());
                            }

                            @Override
                            public void tokenExpired() {
                                Toast.makeText(getActivity(), "Token expired !", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void failure(String s) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<GenerateTokenResponse> call, Throwable t) {

            }
        });
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
