package utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import models.NewOrder;
import models.User;
import models.response.GenerateTokenResponse;
import models.response.ResponseNewOrder;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Response;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sabih Ahmed and Maaz on 09-Jun-16.
 */
public class DevicePreferences {

    private static final String KEY_USER = "user";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_NEW_ORDER = "newOrder";
    private static final String KEY_NEW_ORDER_RESPONSE = "newOrderResponse";

    private static DevicePreferences instance;
    private static SharedPreferences prefs;
    private Context mContext;
//    private static String header = "bearer 13f7ce77-16fb-426d-bb34-3ff37ae667a0";
    private static String header = "";

    private static String authHeader = "Basic NDlhMmRiZTExN2E0NDdjZWFmYjhiOTZiNTIwMTE2ZTY6ZDYzODQ0YjU2MDE3NDI4NjlhODQwNzRhYWZmNGNiNjY=";
    private String KEY_AUTH_HEADER = "AuthenticationHeader";

    NewOrder.SourceLocation sourceLocation;
    NewOrder.DestinationLocation destinationLocation;


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

    public String getHeader() {

        //check in prefs which header flag has to be used and return that header

        if(isAuthHeaderFlag()){
            return authHeader ;
        }
        else{
            return getInstance().getUserToken().getAccess_token();
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

    public void setUser() {
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String user = gson.toJson(User.getInstance());

        editor.putString(KEY_USER,user);
        editor.commit();
    }

    public User getUser(){
        Gson gson = new Gson();
        String userString = prefs.getString(KEY_USER, "");

        User user = gson.fromJson(userString, User.class);
        return user;

    }

    public void removeUser(Context context){
        SharedPreferences mySPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = mySPrefs.edit();
        editor.remove(KEY_USER);
        editor.apply();
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

    public void setSourceLocationObject(double latitude, double longitude, String address){

        String sLatitude = String.valueOf(latitude);
        String sLongitude = String.valueOf(longitude);
        sourceLocation = new NewOrder().new SourceLocation(sLatitude,sLongitude,address);
    }

    public NewOrder.SourceLocation getSourceLocationObject(){
        return sourceLocation;
    }

    public void setDestinationLocationObject(double latitude, double longitude, String address){

        String dLatitude = String.valueOf(latitude);
        String dLongitude = String.valueOf(longitude);
        destinationLocation = new NewOrder().new DestinationLocation(dLatitude, dLongitude, address);
    }

    public NewOrder.DestinationLocation getDestinationLocationObject(){
        return destinationLocation;
    }

    public void setNewOrderResponse(ResponseNewOrder body){

        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String body_response = gson.toJson(body);
        editor.putString(KEY_NEW_ORDER_RESPONSE,body_response);
        editor.commit();
    }


    public ResponseNewOrder getResponseNewOrder(){

        Gson gson = new Gson();
        String body_response = prefs.getString(KEY_NEW_ORDER_RESPONSE, null);
        ResponseNewOrder newOrderResponse = gson.fromJson(body_response, ResponseNewOrder.class);
        return newOrderResponse;
    }

}
