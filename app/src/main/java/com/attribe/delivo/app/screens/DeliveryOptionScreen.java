package com.attribe.delivo.app.screens;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.attribe.delivo.app.NavigationUtils;
import com.attribe.delivo.app.R;
import com.attribe.delivo.app.databinding.DeliveryOptionActivityBinding;
import com.attribe.delivo.app.teststeplayout.MainStepActivity;

public class DeliveryOptionScreen extends AppCompatActivity implements View.OnClickListener {
    private DeliveryOptionActivityBinding parent_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        //
        parent_view = DataBindingUtil.setContentView(DeliveryOptionScreen.this, R.layout.delivery_option_activity);
        setEventListners();
    }

    private void setEventListners() {
        // parent_view.selectOption.setOnClickListener(this);
        parent_view.selectOptionRipple.setOnClickListener(this);
//        parent_view.moneyLayout.setOnClickListener(this);
//        parent_view.foodItemLayout.setOnClickListener(this);
//        parent_view.billLayout.setOnClickListener(this);
//        parent_view.otherLayout.setOnClickListener(this);
//        parent_view.parcelLayout.setOnClickListener(this);
//        parent_view.movieTicketLayout.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int view_id = view.getId();
        if (view_id == R.id.select_option_ripple) {
            NavigationUtils.navigateNext(DeliveryOptionScreen.this, MainStepActivity.class);
        }
//        if(view_id == R.id.food_item_layout|| view_id==R.id.parcel_layout||
//                view_id==R.id.bill_layout|| view_id==R.id.money_layout||
//                view_id==R.id.movie_ticket_layout|| view_id==R.id.other_layout)
//        {
//        }

    }
}
