package com.attribe.delivo.app.utils;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.attribe.delivo.app.PicknDropLocations;
import com.attribe.delivo.app.R;
import com.attribe.delivo.app.fragments.DropLocationFragment;
import com.attribe.delivo.app.fragments.PickDetailsFragment;
import com.attribe.delivo.app.fragments.ScreenRiderDetail;
import com.attribe.delivo.app.fragments.PickLocationFragment;


/**
 * Created by Sabih Ahmed on 06-Jun-16.
 * Edited by Uzair Qureshi till created
 */
public class NavigationUtils {

    public static String KEY_ORDERID="orderID";
    public static void placeOrderPanel(Context context , FragmentManager fragmentManager ){

//        OrderMaking orderMaking = new OrderMaking();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.fragments_container, orderMaking).commit();
    }

    public static void navigateScreen(Context context, Class<? extends AppCompatActivity> activity){

        Intent intent=new Intent(context,activity);
        context.startActivity(intent);
    }
}
