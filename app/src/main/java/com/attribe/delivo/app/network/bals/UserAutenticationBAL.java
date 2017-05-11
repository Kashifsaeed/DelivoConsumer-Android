package com.attribe.delivo.app.network.bals;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import com.attribe.delivo.app.models.SignUpInfo;
import com.attribe.delivo.app.models.response.AuthenticationResponse;
import com.attribe.delivo.app.models.response.MessageResponse;
import com.attribe.delivo.app.models.response.SignUpResponse;
import com.attribe.delivo.app.network.RestClient;
import com.attribe.delivo.app.network.interfaces.ResponseCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.attribe.delivo.app.utils.DevicePreferences;

/**
 * Author: Uzair Qureshi
 * Date:  4/21/17.
 * Description:
 */

public class UserAutenticationBAL
{
    public static void signInUser(Context context, String phone_no, String password, final ResponseCallback<AuthenticationResponse> listner)
    {
        RestClient.getAuthAdapter().signIn(phone_no,password).enqueue(new Callback<AuthenticationResponse>() {
            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response)
            {
                if(response.isSuccessful()&&response.body()!=null)
                {
                    listner.onSuccess(response.body());

                }
                if(response.errorBody()!=null)
                {
                    try {
                        String message = new JSONObject(response.errorBody().string())
                                .getString("message");
                        listner.OnResponseFailure(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t)
            {
                listner.onfailure(t.getMessage());

            }
        });

    }
    public static void signUpUser(final Context context, final String username, final String email, final String phone_no,
                                  final String password, String confirm_password,
                                  final ResponseCallback<SignUpResponse> callback)

    {
        RestClient.getAuthAdapter().signUpUSer(username,email,phone_no,password,confirm_password).enqueue(new Callback<SignUpResponse>()
        {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response)
            {
                if(response.isSuccessful()&&response.body()!=null)
                {
                    SignUpInfo signUpInfo=new SignUpInfo(username,phone_no,email,password);
                    DevicePreferences.getInstance().init(context);
                    DevicePreferences.getInstance().setSignUpInfo(signUpInfo);
                    callback.onSuccess(response.body());

                }
                if(response.errorBody()!=null)
                {
                    try {
                        String message = new JSONObject(response.errorBody().string())
                                .getString("message");
                        callback.OnResponseFailure(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


            }
            public void onFailure(Call<SignUpResponse> call, Throwable t)
            {
                callback.onfailure(t.getMessage());
            }
        });

    }

    /**
     * This method call server to confirm pin and get an authenticate key
     * @param context
     * @param phone
     * @param password
     * @param pin
     * @param listner
     */

    public static void verifyUserPin(Context context, String phone, String password, String pin, final ResponseCallback<AuthenticationResponse> listner)
    {
     RestClient.getAuthAdapter().verifyPin(phone,password,pin).enqueue(new Callback<AuthenticationResponse>() {
         @Override
         public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response)

         {
             if(response.isSuccessful()&&response.body()!=null)
             {
                listner.onSuccess(response.body());

             }

             if(response.errorBody()!=null)
             {
                 try {
                     String message = new JSONObject(response.errorBody().string())

                             .getString("message");
                     listner.OnResponseFailure(message);
                 } catch (JSONException e) {
                     e.printStackTrace();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }

         }

         @Override
         public void onFailure(Call<AuthenticationResponse> call, Throwable t)
         {
             listner.onfailure(t.getMessage());

         }


     });

    }
    public static void resentPin(Context context, String phone, String password,final ResponseCallback<MessageResponse> listner)
    {
     RestClient.getAuthAdapter().resentPin(phone,password).enqueue(new Callback<MessageResponse>() {
         @Override
         public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response)
         {
             if(response.isSuccessful()&&response.body()!=null)//excuted when response is successsfull
             {
                 listner.onSuccess(response.body());

             }

             if(response.errorBody()!=null)
             {
                 try {
                     String message = new JSONObject(response.errorBody().string())//excuted if server gives an error

                             .getString("message");
                     listner.OnResponseFailure(message);
                 } catch (JSONException e) {
                     e.printStackTrace();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }

         }

         @Override
         public void onFailure(Call<MessageResponse> call, Throwable t)
         {
               listner.onfailure(t.getMessage());
         }
     });
    }
}
