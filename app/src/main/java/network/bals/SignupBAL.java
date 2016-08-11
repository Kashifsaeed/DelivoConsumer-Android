package network.bals;

import models.NewUser;
import models.User;
import models.response.GenerateTokenResponse;
import models.response.ResponseGuestSignup;
import network.RestClient;
import network.interfaces.LoginUserResponse;
import network.interfaces.SignupUserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.DevicePreferences;

/**
 * Created by Sabih Ahmed on 14-Jun-16.
 */
public class SignupBAL {

    public static void createUser(NewUser user, final SignupUserResponse signupUserResponse) {

        Call<User> newUser = RestClient.getAuthRestAdapter().createUser(user);

        newUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, final Response<User> response) {

                if(response.isSuccessful()){


                    if(response.body() != null){

                        User.getInstance(response.body());
                        DevicePreferences.getInstance().setUser();

                        //user has been created successfully,persist this user's info

                        //login this user

                        LoginBAL.login(DevicePreferences.getInstance().getUser(), new LoginUserResponse() {
                            @Override
                            public void OnLoggedIn() {
                                signupUserResponse.OnUserCreatedAndLoggedIn();
                            }

                            @Override
                            public void OnLoggedInFailed() {
                                signupUserResponse.OnError();
                            }
                        });



                    }
                }


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }
}
