package com.attribe.delivo.app.bals;

import android.content.Context;

import com.attribe.delivo.app.interfaces.ResponseCallback;
import com.attribe.delivo.app.models.request.SignUpInfo;
import com.attribe.delivo.app.models.request.UpdatePassword;
import com.attribe.delivo.app.models.request.UpdateUserProfile;
import com.attribe.delivo.app.models.response.ErrorBody;
import com.attribe.delivo.app.models.response.MessageResponse;
import com.attribe.delivo.app.models.response.UserProfile;
import com.attribe.delivo.app.network.RestClient;
import com.attribe.delivo.app.utils.DevicePreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author: Uzair Qureshi
 * Date:  6/14/17.
 * Description:
 */

public class UserProfileBAL
{
    /**
     * This method will hit to server and get userprofile
     * @param context
     * @param listner
     */
    public static void getUserProfile(final Context context, final ResponseCallback<UserProfile> listner)

    {
        RestClient.getAuthRestAdapter().getUserProfile().enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful() && response.body() != null)
                {
                    //save profile date into cache

                    DevicePreferences.getInstance().init(context);
                    DevicePreferences.getInstance().setUserProfile(response.body());


                    listner.onSuccess(response.body());

                }
                if (response.errorBody() != null) {

                    String message = "";

                    try {
                        message = new JSONObject(response.errorBody().string())
                                .getString("message");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ErrorBody errorBody = new ErrorBody(response.raw().code(), message);
                    listner.OnResponseFailure(errorBody);

                }


            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                if (t instanceof ConnectException) {
                    listner.onfailure("No Internet Connection!");
                } else {
                    listner.onfailure("Some thing went wrong!");


                }


            }
        });

    }
    public static void updateUserProfile(final Context context, UpdateUserProfile userProfile, final ResponseCallback<UserProfile> listner)
    {
     if(DevicePreferences.getInstance().getUserProfile()!=null)
        RestClient.getAuthRestAdapter().updateProfile(String.valueOf(DevicePreferences.getInstance().getUserProfile().getId()),userProfile).enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if(response.isSuccessful() &&response.body()!=null)
                {
                    //save update profile date into cache
                    DevicePreferences.getInstance().init(context);
                    DevicePreferences.getInstance().setUserProfile(response.body());
                    listner.onSuccess(response.body());

                }
               else if (response.errorBody() != null) {

                    String message = "";

                    try {
                        message = new JSONObject(response.errorBody().string())
                                .getString("message");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ErrorBody errorBody = new ErrorBody(response.raw().code(), message);
                    listner.OnResponseFailure(errorBody);

                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t)
            {
                if (t instanceof ConnectException)
                {
                    listner.onfailure("No Internet Connection!");
                } else
                {
                    listner.onfailure("Some thing went wrong!");


                }

            }
        });
    }
    public static void resetPassword(Context context, UpdatePassword updatePassword, final ResponseCallback<MessageResponse> listner)
    {
        RestClient.getAuthRestAdapter().resetPassword(updatePassword).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response)
            {
                if(response.isSuccessful()&&response.body()!=null)
                {
                    listner.onSuccess(response.body());

                }
                else if (response.errorBody()!=null){
                    String message = "";

                    try {
                        message = new JSONObject(response.errorBody().string())
                                .getString("error");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ErrorBody errorBody = new ErrorBody(response.raw().code(), message);
                    listner.OnResponseFailure(errorBody);
                }


            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t)
            {
                if (t instanceof ConnectException)
                {
                    listner.onfailure("No Internet Connection!");
                } else
                {
                    listner.onfailure("Some thing went wrong!");


                }

            }
        });

    }
}
