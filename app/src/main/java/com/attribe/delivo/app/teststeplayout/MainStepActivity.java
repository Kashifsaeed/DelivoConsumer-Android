package com.attribe.delivo.app.teststeplayout;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.attribe.delivo.app.Extras.AppConstants;
import com.attribe.delivo.app.R;
import com.attribe.delivo.app.databinding.MainStepActivityBinding;

import com.attribe.delivo.app.adapters.PagerAdapter;
import com.attribe.delivo.app.fragments.DropDetailFragment;
import com.attribe.delivo.app.fragments.PickDetailsFragment;
import com.attribe.delivo.app.fragments.PickLocationFragment;
import com.attribe.delivo.app.fragments.DropLocationFragment;
import com.attribe.delivo.app.interfaces.OnNextPageNavigation;
import com.attribe.delivo.app.models.request.Order;
import com.attribe.delivo.app.models.request.OrderCreate;
import com.attribe.delivo.app.models.request.Task;
import com.attribe.delivo.app.network.bals.OrderBAL;
import com.attribe.delivo.app.network.interfaces.ResponseCallback;
import com.attribe.delivo.app.screens.LoginScreen;

import java.util.ArrayList;

public class MainStepActivity extends AppCompatActivity implements
        OnNextPageNavigation,
        PickDetailsFragment.OnPickDetailFragmentInteractionListner,
        DropLocationFragment.OnDropLocationFragmentInteractionListener,
        DropDetailFragment.OnDropDetailFragmentInteractionListner,
        PickLocationFragment.OnPickLocationFragmentInteractionListner, View.OnClickListener {

    public MainStepActivityBinding databinding;
    public ViewPager mViewPager;
    public Toolbar mToolbar;
    private Task pickupTask = new Task();
    private Task dropoffTask = new Task();
    private Order order = new Order();
    //private OrderCreate ordercreate;
    private ArrayList<Task> taskslist = new ArrayList<Task>();
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databinding = DataBindingUtil.setContentView(MainStepActivity.this, R.layout.main_step_activity);//set root view
        databinding.mainpager.setAdapter(new PagerAdapter(getSupportFragmentManager(), this));
        initViews();


    }

    //==============================================Helper Method ==============================================================//
    private void initViews() {
        setViewpager();
        setToolbar();
        // setEventListners();
    }

    private void setToolbar() {
        databinding.maintoolbar.myToolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        databinding.maintoolbar.myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(databinding.mainpager.getCurrentItem()>0)
                {
                    databinding.mainpager.setCurrentItem((databinding.mainpager.getCurrentItem() - 1), true);
                }
                else
                {
                    finish();
                }
            }
        });

    }

    private void setViewpager() {
        databinding.mainpager.setOffscreenPageLimit(3);//load each pager in cache and handle
        if (databinding.mainpager.getCurrentItem() == 0)
        {
            databinding.maintoolbar.myToolbar.setTitle("" + "Pick Location");

        }
        databinding.mainpager.addOnPageChangeListener(new OnPageChangedListner());
    }

    private void setToolbarTittle(int position) {
        switch (position) {
            case 0:
                databinding.maintoolbar.myToolbar.setTitle("" + "Pick Location");

                break;
            case 1:
                databinding.maintoolbar.myToolbar.setTitle("" + "Pick Detail");
                break;
            case 2:
                databinding.maintoolbar.myToolbar.setTitle("" + "Drop Location");
                break;
            case 3:
                databinding.maintoolbar.myToolbar.setTitle("" + "Drop Detail");
                break;

            default:
                break;


        }
    }


    private void setEventListners() {
        databinding.steplayout.steponeView.setOnClickListener(this);
        databinding.steplayout.steptwoView.setOnClickListener(this);
        databinding.steplayout.stepthreeView.setOnClickListener(this);
        databinding.steplayout.stepfourView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int itemId = view.getId();
        switch (itemId) {
            case R.id.stepone_view:
                databinding.mainpager.setCurrentItem(AppConstants.PICK_LOCATION, true);
                break;

            case R.id.steptwo_view:
                databinding.mainpager.setCurrentItem(AppConstants.PICK_DETAIL, true);
                break;

            case R.id.stepthree_view:
                databinding.mainpager.setCurrentItem(AppConstants.DROP_LOCATION, true);
                break;
            case R.id.stepfour_view:
                databinding.mainpager.setCurrentItem(AppConstants.DROP_DETAIL, true);
                break;
            default:
                break;


        }
    }

    @Override
    public void OnPickLocationFragmentInteraction(String picklocation) {
        pickupTask.setNearby(picklocation);
    }

    @Override
    public void pickDetailFragmentInteraction(String pp_name, String pp_contactno, String detail_address, String pick_time, String pick_date, String pick_desc) {
        pickupTask.setName(pp_name);
        pickupTask.setContact(pp_contactno);
        pickupTask.setAddress(detail_address);
        pickupTask.setTask_type(true);
        pickupTask.setDetails(pick_desc);
        order.setPickup_time(pick_time);
        order.setPickup_date(pick_date);


        //taskslist.add(pickupTask);
        // taskslist.set(0,pickupTask);
        if (taskslist.contains(pickupTask)) {
            taskslist.set(0, pickupTask);

        } else {
            taskslist.add(0, pickupTask);

        }

        //taskslist.contains(pickupTask)


    }

    private void showProgress(String message) {
        progressDialog = ProgressDialog.show(MainStepActivity.this, "", message, false);
    }

    private void hideProgress() {
        progressDialog.dismiss();
    }


    private void createOrder(OrderCreate orderCreate) {
        showProgress("Place Order...");

        OrderBAL.placeOrder(orderCreate, new ResponseCallback<Object>() {
            @Override
            public void onSuccess(Object response) {
                hideProgress();
                Toast.makeText(MainStepActivity.this, "Successfully place Order", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void OnResponseFailure(String error_body) {
                hideProgress();

            }

            @Override
            public void onfailure(String message) {
                hideProgress();

            }
        });
    }

    @Override
    public void onDropLocationFragmentInteraction(String droplocation_name) {
        dropoffTask.setNearby(droplocation_name);

    }

    @Override
    public void dropDetailFragmentInteraction(String pp_name, String pp_contactno, String detail_address, String drop_time, String drop_date, String pick_desc) {
        dropoffTask.setName(pp_name);
        dropoffTask.setContact(pp_contactno);
        dropoffTask.setAddress(detail_address);
        dropoffTask.setTask_type(false);
        dropoffTask.setDetails(pick_desc);
        order.setDrop_time(drop_time);
        order.setDrop_date(drop_date);
        // taskslist.add(dropoffTask);
        if (taskslist.contains(dropoffTask)) {
            taskslist.set(1, dropoffTask);
        } else {
            taskslist.add(1, dropoffTask);


        }
        OrderCreate.getInstance().setOrder(order);
        OrderCreate.getInstance().setTasks(taskslist);
        createOrder(OrderCreate.getInstance());


    }


    @Override
    public void onPage(int position) {
        databinding.mainpager.setCurrentItem(position, true);
    }

    private class OnPageChangedListner implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setToolbarTittle(position);//this method will change toolbar tittle when pager fragment is changed

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
