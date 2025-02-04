package utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import models.NewOrder;
import models.User;
import models.response.GenerateTokenResponse;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Response;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sabih Ahmed on 09-Jun-16.
 */
public class DevicePreferences {

    private static final String KEY_USER = "user";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_PICK_LOC = "pickLoc";
    private static final String KEY_DROP_LOC = "dropLoc";
    private static final String KEY_NEW_ORDER = "newOrder";
    private static DevicePreferences instance;
    private static SharedPreferences prefs;
    private Context mContext;
    private static String header = "bearer 13f7ce77-16fb-426d-bb34-3ff37ae667a0";


    private static String authHeader = "Basic NDlhMmRiZTExN2E0NDdjZWFmYjhiOTZiNTIwMTE2ZTY6ZDYzODQ0YjU2MDE3NDI4NjlhODQwNzRhYWZmNGNiNjY=";
    private String KEY_AUTH_HEADER = "AuthenticationHeader";

    public void init(Context context){

        this.mContext = context;

        prefs = mContext.getSharedPreferences("clientPrefs", Context.MODE_PRIVATE);
    }


    private DevicePreferences() {

    }

    public static DevicePreferences getInstance(){

        if(instance == null ){

            instance = new DevicePreferences();
        }

        return instance;

    }
    public String getHeader() {

        //check in prefs which header flag has to be used
        //return that header

        if(isAuthHeaderFlag()){

            return authHeader ;
        }
        else{

            return header;
        }

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

    public void setUser(Response<User> response) {
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String user = gson.toJson(response.body());

        editor.putString(KEY_USER,user);
        editor.commit();
    }

    public User getUser(){
        Gson gson = new Gson();
        String userString = prefs.getString(KEY_USER, "");

        User user = gson.fromJson(userString, User.class);

        return user;

    }

    public void setUserToken(GenerateTokenResponse response) {

        header = "bearer " + response.access_token;
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String token = gson.toJson(response);
        editor.putString(KEY_TOKEN,token);
        editor.commit();

    }

    public GenerateTokenResponse getUserToken(){
        Gson gson = new Gson();
        String userToken = prefs.getString(KEY_TOKEN, null);
        GenerateTokenResponse token = gson.fromJson(userToken, GenerateTokenResponse.class);

        return token;

    }

    public void setPickLocation(String pickLoc) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_PICK_LOC,pickLoc);
        editor.commit();
    }

    public String getPickLoc(){
        String pickLoc = "";

        pickLoc = prefs.getString(KEY_PICK_LOC, "");

        return pickLoc;
    }

    public void setDropLocation(String dropLoc){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_DROP_LOC,dropLoc);
        editor.commit();

    }

    public String getDropLoc(){
        String dropLoc = "";

        dropLoc = prefs.getString(KEY_DROP_LOC, "");

        return dropLoc;

    }


    public void setOrder(NewOrder newOrder) {
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String order = gson.toJson(newOrder);
        editor.putString(KEY_NEW_ORDER,order);
        editor.commit();
    }

    public NewOrder getOrder(){
        Gson gson = new Gson();
        String order = prefs.getString(KEY_NEW_ORDER, null);
        NewOrder newOrder = gson.fromJson(order, NewOrder.class);
        return newOrder;

    }
}
