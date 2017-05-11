package com.attribe.delivo.app;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.attribe.delivo.app.fragments.ConfirmationScreen;
import com.attribe.delivo.app.fragments.DropLocationFragment;
import com.attribe.delivo.app.fragments.OrderMaking;
import com.attribe.delivo.app.fragments.ScreenRegistration;
import com.attribe.delivo.app.fragments.ScreenRiderDetail;
import com.attribe.delivo.app.fragments.PickLocationFragment;
import com.attribe.delivo.app.fragments.PickDetailFragment;
import com.attribe.delivo.app.fragments.UserAuthentication;



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
//        Bundle bundle = new Bundle();
//        bundle.putString(KEY_ORDERID,orderid);
//        confirmationScreen.setArguments(bundle);

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

    public static void placeOrderPanel(Context context , FragmentManager fragmentManager ){

        OrderMaking orderMaking = new OrderMaking();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragments_container, orderMaking).commit();
    }
    public static void navigateFragment(android.support.v4.app.FragmentManager fragmentManager, Fragment callingFragment)
    {
        if(callingFragment instanceof PickLocationFragment)
        {
            PickLocationFragment newFragment = new PickLocationFragment();
            android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
            transaction.replace(R.id.main_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(callingFragment instanceof PickDetailFragment)
        {
            PickDetailFragment newFragment = new PickDetailFragment();
            android.support.v4.app.FragmentTransaction transaction =fragmentManager.beginTransaction();

            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
            transaction.replace(R.id.main_container, newFragment);
            transaction.addToBackStack(null);

            transaction.commit();

        }
        else if(callingFragment instanceof DropLocationFragment)
        {
            DropLocationFragment newFragment = new DropLocationFragment();
            android.support.v4.app.FragmentTransaction transaction =fragmentManager.beginTransaction();

            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
            transaction.replace(R.id.main_container, newFragment);
            transaction.addToBackStack(null);

            transaction.commit();

        }


    }
}
