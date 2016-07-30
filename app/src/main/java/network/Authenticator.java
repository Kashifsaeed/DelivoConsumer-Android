package network;

import android.widget.Toast;
import models.response.GenerateTokenResponse;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Callback;
import utils.DevicePreferences;

import java.io.IOException;

/**
 * Created by Maaz on 7/27/2016.
 */
public class Authenticator implements okhttp3.Authenticator {

    private String refreshToken;

    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        String newAccessToken = getRefreshtoken();
       // newAccessToken = service.refreshToken();
        DevicePreferences.getInstance().getUserToken().setAccess_token(DevicePreferences.getInstance().getUserToken().refresh_token);
       // Add new header to rejected request and retry it
        return response.request().newBuilder()
                .header("Authorization",  DevicePreferences.getInstance().getHeader())
                .header("Authorization",newAccessToken)
                .build();
    }

    private String getRefreshtoken(){


        RestClient.getAuthAdapter().refresh("refresh_token",DevicePreferences.getInstance().getUser().getData().getUsername()
                ,DevicePreferences.getInstance().getUser().getUserToken().refresh_token,"read write").enqueue(new Callback<GenerateTokenResponse>() {

            @Override
            public void onResponse(Call<GenerateTokenResponse> call, retrofit2.Response<GenerateTokenResponse> response) {
                if(response.isSuccessful()){

                    DevicePreferences.getInstance().getUser().setUserToken(response.body());
                    refreshToken= DevicePreferences.getInstance().getUser().getUserToken().getAccess_token();  // DevicePreferences.getInstance().getUser().getResponse().getAccess_token();
                }
            }

            @Override
            public void onFailure(Call<GenerateTokenResponse> call, Throwable t) {

            }
        });
        return refreshToken;
    }
}

