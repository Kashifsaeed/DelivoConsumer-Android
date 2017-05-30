package com.attribe.delivo.app.screens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.attribe.delivo.app.Extras.AppConstants;
import com.attribe.delivo.app.R;

import com.attribe.delivo.app.adapters.PagerAdapter;
import com.attribe.delivo.app.databinding.OrderContainerActivityBinding;
import com.attribe.delivo.app.dialouge.AlertDialouge;
import com.attribe.delivo.app.fragments.DropDetailFragment;
import com.attribe.delivo.app.fragments.PickDetailsFragment;
import com.attribe.delivo.app.fragments.PickLocationFragment;
import com.attribe.delivo.app.fragments.DropLocationFragment;
import com.attribe.delivo.app.interfaces.OnNextPageNavigation;
import com.attribe.delivo.app.interfaces.onDialogeListner;
import com.attribe.delivo.app.models.request.Order;
import com.attribe.delivo.app.models.request.OrderCreate;
import com.attribe.delivo.app.models.request.Task;
import com.attribe.delivo.app.bals.OrderBAL;
import com.attribe.delivo.app.interfaces.ResponseCallback;
import com.attribe.delivo.app.models.response.ErrorBody;
import com.attribe.delivo.app.models.response.OrderResponse;
import com.attribe.delivo.app.utils.LocationTracker;
import com.attribe.delivo.app.utils.NavigationUtils;

import java.util.ArrayList;

public class OrderContainerActivity extends AppCompatActivity implements
        OnNextPageNavigation,
        PickDetailsFragment.OnPickDetailFragmentInteractionListner,
        DropLocationFragment.OnDropLocationFragmentInteractionListener,
        DropDetailFragment.OnDropDetailFragmentInteractionListner,
        PickLocationFragment.OnPickLocationFragmentInteractionListner, View.OnClickListener {

    public OrderContainerActivityBinding databinding;
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

        databinding = DataBindingUtil.setContentView(OrderContainerActivity.this, R.layout.order_container_activity);//set root view
        databinding.mainpager.setAdapter(new PagerAdapter(getSupportFragmentManager(), this));

        initViews();


    }

    //==============================================Helper Method ==============================================================//
    private void initViews()
    {
        setViewpager();
        setToolbar();
        setEventListners();
        setUpDrawer();
    }

    private void setUpDrawer() {
        setupDrawerContent(databinding.nvView);

    }

    private void setToolbar() {
        setSupportActionBar(databinding.maintoolbar.myToolbar);
        databinding.maintoolbar.myToolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        databinding.maintoolbar.myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (databinding.mainpager.getCurrentItem() > 0) {
                    databinding.mainpager.setCurrentItem((databinding.mainpager.getCurrentItem() - 1), true);
                } else {
                    finish();
                }
            }
        });

    }

    private void setViewpager() {
        databinding.mainpager.setOffscreenPageLimit(3);//load each pager in cache and handle
        if (databinding.mainpager.getCurrentItem() == 0) {
            databinding.maintoolbar.myToolbar.setTitle("" + "Pick Location");

        }
        databinding.mainpager.addOnPageChangeListener(new OnPageChangedListner());
        databinding.steplayout.steptwoView.setEnabled(false);
        databinding.steplayout.stepthreeView.setEnabled(false);
        databinding.steplayout.stepfourView.setEnabled(false);


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
    private void stepsHandle(int postion)
    {
        if(postion==1){
            databinding.steplayout.steptwoView.setAlpha(1.0f);
            databinding.steplayout.steptwoView.setEnabled(true);


           ;
        }
        if(postion==2){
            databinding.steplayout.stepthreeView.setAlpha(1.0f);
            databinding.steplayout.stepthreeView.setEnabled(true);
        }
        if(postion==3){
            databinding.steplayout.stepfourView.setAlpha(1.0f);
            databinding.steplayout.stepfourView.setEnabled(true);
        }
    }
         private void selectDrawerItem(MenuItem item)

         {
             switch (item.getItemId()) {

                 case R.id.nav_myorder_screen:
                     NavigationUtils.navigateScreen(OrderContainerActivity.this, sampleActivity.class);
                     break;
                 case R.id.nav_signout:
                     Intent intent = new Intent(OrderContainerActivity.this,
                             LoginScreen.class);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                     startActivity(intent);



                     break;

                 case R.id.nav_myprofile_screen:
                     break;

                 default:
                     break;
             }


         }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }



    private void setEventListners() {
        databinding.steplayout.steponeView.setOnClickListener(this);
        databinding.steplayout.steptwoView.setOnClickListener(this);
        databinding.steplayout.stepthreeView.setOnClickListener(this);
        databinding.steplayout.stepfourView.setOnClickListener(this);
    }
    private void openDrawer(){
       databinding.drawerLayout.openDrawer(GravityCompat.END);
    }
    private void closeDrawer()
    {
       databinding.drawerLayout.closeDrawer(GravityCompat.END);
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
        progressDialog = ProgressDialog.show(OrderContainerActivity.this, "", message, false);
    }

    private void hideProgress() {
        progressDialog.dismiss();
    }


    private void createOrder(OrderCreate orderCreate) {
        showProgress("Place Order...");

        OrderBAL.placeOrder(orderCreate, new ResponseCallback<OrderResponse>() {
            @Override
            public void onSuccess(OrderResponse response) {
                hideProgress();
                AlertDialouge.showSuccess(OrderContainerActivity.this, "Your Order has been placed!", new onDialogeListner() {
                    @Override
                    public void onYes() {
                        NavigationUtils.navigateScreen(OrderContainerActivity.this,DeliveryOptionScreen.class);
                    }

                    @Override
                    public void onCacle() {

                    }
                });

            }

            @Override
            public void OnResponseFailure(ErrorBody error_body) {

                hideProgress();
                AlertDialouge.showError(OrderContainerActivity.this,error_body.getError_msg());

            }

            @Override
            public void onfailure(String message) {
                hideProgress();
                AlertDialouge.showError(OrderContainerActivity.this,message);


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
    public void onPageChange(int position)
    {
        databinding.mainpager.setCurrentItem(position, true);
    }

    private class OnPageChangedListner implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setToolbarTittle(position);//this method will change toolbar tittle when pager fragment is changed
            stepsHandle(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      //  for enable clicking the nav icon
        if (item != null && item.getItemId() == R.id.menu_btn) {
            if (databinding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
                closeDrawer();
            } else {
                openDrawer();
            }
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onResume() {
        super.onResume();
       // showLocationAlert();
    }
}
