package com.attribe.delivo.app.network.bals;

import com.attribe.delivo.app.models.User;
import com.attribe.delivo.app.models.response.GenerateTokenResponse;
import com.attribe.delivo.app.network.RestClient;
import com.attribe.delivo.app.network.interfaces.LoginUserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.attribe.delivo.app.utils.DevicePreferences;

/**
 * Created by Sabih Ahmed on 15-Jun-16.
 */
public class LoginBAL {


    public static void login(User user, final LoginUserResponse loginUserResponse) {
        DevicePreferences.getInstance().setAuthHeaderFlag(true);

         RestClient.getAuthAdapter().login("password",
                 user.getData().getMobilenum(),
                 user.getData().getMobilenum(),
                 "read write").enqueue(new Callback<GenerateTokenResponse>() {
            @Override
            public void onResponse(Call<GenerateTokenResponse> call, Response<GenerateTokenResponse> response) {

                if(response.isSuccessful()){

                            if(response.body()!=null ){

                        User.getInstance().setUserToken(response.body());
                                DevicePreferences.getInstance().setUser();
                        loginUserResponse.OnLoggedIn();

                    }
                }

                DevicePreferences.getInstance().setAuthHeaderFlag(false);

            }

            @Override
            public void onFailure(Call<GenerateTokenResponse> call, Throwable t) {

                loginUserResponse.OnLoggedInFailed();
            }
        });
    }
    public static void userLogin(String username,String password, final LoginUserResponse loginUserResponse){
        RestClient.getAuthAdapter().
                login("password",
                        username,
                        password,
                        "read write").enqueue(new Callback<GenerateTokenResponse>() {
            @Override
            public void onResponse(Call<GenerateTokenResponse> call, Response<GenerateTokenResponse> response) {
                if(response.isSuccessful()){

                    if(response.body()!=null){

                       // loginUserResponse.OnSuccessLogin(response.body());
                        User.getInstance().setUserToken(response.body());
                        loginUserResponse.OnLoggedIn();


                    }

                }
                if(response.errorBody()!=null){

                    loginUserResponse.onLoginError();

                }
            }

            @Override
            public void onFailure(Call<GenerateTokenResponse> call, Throwable t) {

                loginUserResponse.OnLoggedInFailed();

            }
        });



    }
}
