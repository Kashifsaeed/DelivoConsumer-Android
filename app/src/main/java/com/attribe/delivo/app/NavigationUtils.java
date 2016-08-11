package com.attribe.delivo.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import fragments.ConfirmationScreen;
import fragments.ScreenRegistration;
import fragments.ScreenRiderDetail;
import fragments.UserAuthentication;


/**
 * Created by Sabih Ahmed on 06-Jun-16.
 */
public class NavigationUtils {

    public static String KEY_ORDERID="orderID";

    public static void showRegistrationScreen(FragmentManager fragmentManager) {

        ScreenRegistration screenRegistration = new ScreenRegistration();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragments_container, screenRegistration).commit();
    }

    public static void showConfirmationScreen( FragmentManager fragmentManager , String orderid ) {

        ConfirmationScreen confirmationScreen = new ConfirmationScreen();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ORDERID,orderid);
        confirmationScreen.setArguments(bundle);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragments_container, confirmationScreen).commit();
    }

    public static void showRiderDetailScreen(FragmentManager fragmentManager) {

        ScreenRiderDetail screenRiderDetail = new ScreenRiderDetail();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragments_container, screenRiderDetail).commit();
    }

    public static void showUserAuthScreen(Context context , FragmentManager fragmentManager ){

        UserAuthentication userAuthentication = new UserAuthentication();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragments_container, userAuthentication).commit();

//        FragmentManager mfragmentManager = getFragmentManager();
//        mfragmentManager.beginTransaction().replace(R.id.fragments_container, userAuthentication).commit();
    }



    public static void showPicknDropLocationsScreen(Context context) {

        Intent intent = new Intent(context,PicknDropLocations.class);
        context.startActivity(intent);
    }

}
