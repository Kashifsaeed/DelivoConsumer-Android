package com.attribe.delivo.app.bals;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;

import com.attribe.delivo.app.models.request.SignUpInfo;
import com.attribe.delivo.app.models.response.AuthenticationResponse;
import com.attribe.delivo.app.models.response.ErrorBody;
import com.attribe.delivo.app.models.response.MessageResponse;
import com.attribe.delivo.app.models.response.SignUpResponse;
import com.attribe.delivo.app.network.RestClient;
import com.attribe.delivo.app.interfaces.ResponseCallback;

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
    public static void signInUser(final Context context, final String phone_no, final String password, final ResponseCallback<AuthenticationResponse> listner)
    {
        RestClient.getAuthAdapter().signIn(phone_no,password).enqueue(new Callback<AuthenticationResponse>() {
            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                if (response.isSuccessful() && response.body() != null)
                {
                    DevicePreferences.getInstance().init(context);
                    DevicePreferences.getInstance().setAuthKey(response.body());
                    listner.onSuccess(response.body());

                }

                if (response.errorBody() != null)
                {
                    SignUpInfo signUpInfo=new SignUpInfo(phone_no,password);
                    DevicePreferences.getInstance().init(context);
                    DevicePreferences.getInstance().setSignUpInfo(signUpInfo);
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
            public void onFailure(Call<AuthenticationResponse> call, Throwable t)
            {
                if(t instanceof ConnectException)
                {
                    listner.onfailure("No Internet Connection!");
                }
                else
                {
                    listner.onfailure("Some thing went wrong!");


                }

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
                    callback.OnResponseFailure(errorBody);

                }


            }
            public void onFailure(Call<SignUpResponse> call, Throwable t)
            {
                if(t instanceof ConnectException)
                {
                    callback.onfailure("No Internet Connection!");
                }
                else
                {
                    callback.onfailure("Some thing went wrong!");


                }

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

    public static void verifyUserPin(final Context context, String phone, String password, String pin, final ResponseCallback<AuthenticationResponse> listner)
    {
     RestClient.getAuthAdapter().verifyPin(phone,password,pin).enqueue(new Callback<AuthenticationResponse>() {
         @Override
         public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response)

         {
             if(response.isSuccessful()&&response.body()!=null)
             {
                 DevicePreferences.getInstance().init(context);
                 DevicePreferences.getInstance().setAuthKey(response.body());
                 listner.onSuccess(response.body());

             }

             if(response.errorBody()!=null)
             {
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
         public void onFailure(Call<AuthenticationResponse> call, Throwable t)
         {
             if(t instanceof ConnectException)
             {
                 listner.onfailure("No Internet Connection!");
             }
             else
             {
                 listner.onfailure("Some thing went wrong!");


             }


         }


     });

    }
    public static void resentPin(Context context, String phone,final ResponseCallback<MessageResponse> listner)
    {
     RestClient.getAuthAdapter().resentPin(phone).enqueue(new Callback<MessageResponse>() {
         @Override
         public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response)
         {
             if(response.isSuccessful()&&response.body()!=null)//excuted when response is successsfull
             {
                 listner.onSuccess(response.body());

             }

             if(response.errorBody()!=null)
             {
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
         public void onFailure(Call<MessageResponse> call, Throwable t)
         {
             if(t instanceof ConnectException)
             {
                 listner.onfailure("No Internet Connection!");
             }
             else
             {
                 listner.onfailure("Some thing went wrong!");


             }

         }
     });
    }
}
