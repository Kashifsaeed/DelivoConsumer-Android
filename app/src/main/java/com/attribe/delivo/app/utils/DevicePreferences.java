package com.attribe.delivo.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.attribe.delivo.app.models.response.AuthenticationResponse;
import com.attribe.delivo.app.models.response.UserProfile;
import com.google.gson.Gson;
import com.attribe.delivo.app.models.request.SignUpInfo;

/**
 * Created by Sabih Ahmed and Maaz on 09-Jun-16.
 */
public class DevicePreferences {

    private static final String KEY_USER = "user";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_NEW_ORDER = "newOrder";
    private static final String KEY_NEW_ORDER_RESPONSE = "newOrderResponse";
    private static final String KEY_SIGNUPINFO="userinfo";
    private static String AUTH_KEY="authenticationkey";
    private static String USER_PROFILE="user_profile";

    private static DevicePreferences instance;
    private static SharedPreferences prefs;
    private Context mContext;
//    private static String header = "bearer 13f7ce77-16fb-426d-bb34-3ff37ae667a0";
    private static String header = "";

    private static String authHeader = "Basic NDlhMmRiZTExN2E0NDdjZWFmYjhiOTZiNTIwMTE2ZTY6ZDYzODQ0YjU2MDE3NDI4NjlhODQwNzRhYWZmNGNiNjY=";
    private String KEY_AUTH_HEADER = "AuthenticationHeader";



    public DevicePreferences init(Context context){

        this.mContext = context;

        prefs = mContext.getSharedPreferences("clientPrefs", Context.MODE_PRIVATE);
        return instance;
    }
    private DevicePreferences() {}

    public static DevicePreferences getInstance(){

        if(instance == null ){
            instance = new DevicePreferences();
        }
        return instance;
    }



    public void setAuthHeaderFlag(Boolean flag) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_AUTH_HEADER,flag);
        editor.commit();

    }

    public boolean isAuthHeaderFlag(){

        boolean headerFlag = prefs.getBoolean(KEY_AUTH_HEADER, false);

        return headerFlag;
    }

    public void setUser() {
//        SharedPreferences.Editor editor = prefs.edit();
//
//        Gson gson = new Gson();
//        String user = gson.toJson(User.getInstance());
//
//        editor.putString(KEY_USER,user);
//        editor.commit();
    }

//    public User getUser(){
//        Gson gson = new Gson();
//        String userString = prefs.getString(KEY_USER, "");
//
//        User user = gson.fromJson(userString, User.class);
//        return user;
//
//    }

    public void removeUser(Context context){
        SharedPreferences mySPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = mySPrefs.edit();
        editor.remove(KEY_USER);
        editor.apply();
    }






    public void setSourceLocationObject(double latitude, double longitude, String address){

        String sLatitude = String.valueOf(latitude);
        String sLongitude = String.valueOf(longitude);
       // sourceLocation = new NewOrder().new SourceLocation(sLatitude,sLongitude,address);
    }


    public void setDestinationLocationObject(double latitude, double longitude, String address){

        String dLatitude = String.valueOf(latitude);
        String dLongitude = String.valueOf(longitude);
     //   destinationLocation = new NewOrder().new DestinationLocation(dLatitude, dLongitude, address);
    }




    public void setSignUpInfo(SignUpInfo userinfo)
    {
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String user_signup_info = gson.toJson(userinfo);
        editor.putString(KEY_SIGNUPINFO,user_signup_info);
        editor.commit();

    }
    public SignUpInfo getuserInfo()
    {
        Gson gson = new Gson();
        String userinfo = prefs.getString(KEY_SIGNUPINFO, null);
        SignUpInfo user_signup_info = gson.fromJson(userinfo, SignUpInfo.class);
        return user_signup_info;

    }
    public void setAuthKey(AuthenticationResponse authentication){
        SharedPreferences.Editor editor=prefs.edit();
        Gson gson=new Gson();
        String user_auth=gson.toJson(authentication);
        editor.putString(AUTH_KEY,user_auth);
        editor.commit();

    }
    public AuthenticationResponse getAuthKey(){
        Gson gson=new Gson();
        String user_auth=prefs.getString(AUTH_KEY,null);
        AuthenticationResponse user_authentication_key=gson.fromJson(user_auth,AuthenticationResponse.class);
        return user_authentication_key;
    }
    public void setUserProfile(UserProfile userProfile){
        SharedPreferences.Editor editor=prefs.edit();
        Gson gson=new Gson();
        String user_auth=gson.toJson(userProfile);
        editor.putString(USER_PROFILE,user_auth);
        editor.commit();


    }
    public UserProfile getUserProfile(){
        Gson gson=new Gson();
        String user_profile=prefs.getString(USER_PROFILE,null);
        UserProfile userProfile=gson.fromJson(user_profile,UserProfile.class);
        return userProfile;
    }

}
