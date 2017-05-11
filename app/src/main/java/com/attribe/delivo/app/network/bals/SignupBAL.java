package com.attribe.delivo.app.network.bals;

import com.attribe.delivo.app.models.NewUser;
import com.attribe.delivo.app.models.User;
import com.attribe.delivo.app.network.RestClient;
import com.attribe.delivo.app.network.interfaces.LoginUserResponse;
import com.attribe.delivo.app.network.interfaces.SignupUserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.attribe.delivo.app.utils.DevicePreferences;

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

                            @Override
                            public void onLoginError() {

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

    public static void userSignUp(NewUser user, final SignupUserResponse signupUserResponse){
        RestClient.getAuthAdapter().createUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null)
                    {
                        //if meta data is success
                        if(response.body().getMeta().getSuccess()==true && response.body().getData()!=null) {

                            signupUserResponse.OnUserCreated(response.body());



//                        User.getInstance(response.body());
//                        DevicePreferences.getInstance().setUser();
                        }
                        //if meta data is unsuccess
                        if(response.body().getMeta().getSuccess()==false && response.body().getMeta().getCode()==3001){

                            signupUserResponse.OnuserAlreadyexits();
                        }

                    }



                }
                if(response.errorBody()!=null){
                    signupUserResponse.OnError();

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {


            }
        });




    }
    private void loginFirst(String username,String password)
    {
       LoginBAL.userLogin(username, password, new LoginUserResponse() {
           @Override
           public void OnLoggedIn()
           {

           }

           @Override
           public void OnLoggedInFailed() {

           }

           @Override
           public void onLoginError() {

           }
       });


    }

}
