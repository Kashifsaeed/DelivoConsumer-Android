package network.bals;

import models.User;
import models.response.GenerateTokenResponse;
import network.RestClient;
import network.interfaces.LoginUserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.DevicePreferences;

/**
 * Created by Sabih Ahmed on 15-Jun-16.
 */
public class LoginBAL {


    public static void login(User user, final LoginUserResponse loginUserResponse) {
        DevicePreferences.getInstance().setAuthHeaderFlag(true);

        Call<GenerateTokenResponse> generateToken = RestClient.getAdapter().login("password",
                user.getData().getMobilenum(),
                user.getData().getMobilenum(),
                "read write");

        generateToken.enqueue(new Callback<GenerateTokenResponse>() {
            @Override
            public void onResponse(Call<GenerateTokenResponse> call, Response<GenerateTokenResponse> response) {

                if(response.isSuccessful()){

                    if(response.body()!=null ){

                        DevicePreferences.getInstance().setUserToken(response.body());
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
}
