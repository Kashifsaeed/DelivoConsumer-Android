package com.attribe.delivo.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.maps.model.LatLng;
import screens.*;
import utils.DemoSimpleProgress;
import utils.DemoTextProgress;
import utils.Progress;

/**
 * Created by Sabih Ahmed on 06-Jun-16.
 */
public class NavigationUtils {

    public static final String KEY_LATLNG = "geocodes";
    //private static ProgressView progress;
    private static Progress progress;
    public static String KEY_ORDERID="orderID";

    public static void showDelivoParamsScreen(FragmentManager fragmentManager){
        Fragment newFragment = DelivoParameter.newInstance();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.container, newFragment);


        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);


        transaction.commit();
    }

    public static void showRegistrationScreen(/**FragmentManager fragmentManager**/Context context) {

        Intent intent = new Intent(context,ScreenRegistration.class);
        context.startActivity(intent);
        /**
        Fragment newFragment = ScreenRegistration.newInstance();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.container, newFragment);


        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);


        transaction.commit();
         **/

    }

    public static void startMainActivity(Context context) {
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }

    public static void showConfirmationScreen(/**FragmentManager fragmentManager, String args**/Context context, String orderid) {

        Intent intent = new Intent(context,Confirmation.class);
        intent.putExtra(KEY_ORDERID,orderid);
        context.startActivity(intent);
        /**
        Fragment confirmFragment = new Confirmation().newInstance(args);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, confirmFragment);

        transaction.replace(R.id.container, confirmFragment);
        transaction.addToBackStack(null);
        transaction.commit();**/
    }

    public static void showRiderDetailScreen(Context context) {

        Intent intent = new Intent(context,ScreenRiderDetail.class);
        context.startActivity(intent);

    }

    public static void showDropLocationScreen(/**FragmentManager fragmentManager**/Context context, LatLng userLocation) {
        Intent intent = new Intent(context,DropLocation.class);
        intent.putExtra(KEY_LATLNG,userLocation);
        context.startActivity(intent);

        /**
        Fragment dropLocation = new DropLocation().newInstance();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container,dropLocation );
        transaction.commit();**/
    }

    public static Progress getProgress(boolean attachText) {

        if(attachText){
            progress = new DemoTextProgress();

        }
        else{
            progress = new DemoSimpleProgress();

        }

        //progress = new ProgressView();

        return progress;
    }

//    public static Progress getTextProgress(){
//
//        textProgressView = new DemoTextProgress();
//
//        return textProgressView;
//    }


    public static void hideProgressView() {

        if(progress != null){

            progress.dismiss();
        }
    }

    public static void showPickLocationScreen(/*FragmentManager fragmentManager*/Context context) {

        Intent intent = new Intent(context,PickLocation.class);
        context.startActivity(intent);

//        Fragment pickLocation = new PickLocation();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.container,pickLocation );
//        transaction.commit();
    }
}
