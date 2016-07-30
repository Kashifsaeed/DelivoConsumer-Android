package com.attribe.delivo.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import fragments.ConfirmationScreen;
import fragments.ScreenRegistration;
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

//    public static void showDelivoParamsScreen(FragmentManager fragmentManager){
//        Fragment newFragment = DelivoParameter.newInstance();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//
//        transaction.replace(R.id.container, newFragment);
//
//
//        transaction.replace(R.id.container, newFragment);
//        transaction.addToBackStack(null);
//
//
//        transaction.commit();
//    }

    public static void showRegistrationScreen(FragmentManager fragmentManager) {

        ScreenRegistration screenRegistration = new ScreenRegistration();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.auth_container, screenRegistration).commit();
    }

    public static void startMainActivity(Context context) {
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }

    public static void showConfirmationScreen( FragmentManager fragmentManager , String orderid) {

        ConfirmationScreen confirmationScreen = new ConfirmationScreen();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ORDERID,orderid);
        confirmationScreen.setArguments(bundle);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.auth_container, confirmationScreen).commit();
    }

    public static void showRiderDetailScreen(Context context) {
        Intent intent = new Intent(context,ScreenRiderDetail.class);
        context.startActivity(intent);
    }

    public static void showUserAuthScreen(Context context){
        Intent intent = new Intent(context, UserAuthenticationScreenContainer.class);
        context.startActivity(intent);
    }


    public static Progress getProgress(boolean attachText) {

        if(attachText){ progress = new DemoTextProgress();
        }
        else{ progress = new DemoSimpleProgress();
        }
        return progress;
    }

    public static void showPickLocationScreen(/*FragmentManager fragmentManager*/Context context) {

        Intent intent = new Intent(context,PickLocation.class);
        context.startActivity(intent);
    }

}
