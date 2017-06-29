package com.attribe.delivo.app.screens;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.attribe.delivo.app.R;
import com.attribe.delivo.app.adapters.OrdersAdapter;
import com.attribe.delivo.app.bals.OrderBAL;
import com.attribe.delivo.app.databinding.MyOrdersActivityBinding;
import com.attribe.delivo.app.dialouge.AlertDialouge;
import com.attribe.delivo.app.interfaces.ResponseCallback;
import com.attribe.delivo.app.models.response.ErrorBody;
import com.attribe.delivo.app.utils.SnackBars;

public class MyOrders extends AppCompatActivity {
    MyOrdersActivityBinding binding;
    com.attribe.delivo.app.models.response.MyOrders orders;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.my_orders_activity);
        binding= DataBindingUtil.setContentView(MyOrders.this,R.layout.my_orders_activity);
        loadViews();
    }

    private void loadViews() {
        initRecyclerView();
        initToolbar();


    }


    //========================================Helper Methods ========================================//

    private void initRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        binding.myOrdersRecyclerview.setLayoutManager(mLayoutManager);
        binding.myOrdersRecyclerview.setItemAnimator(new DefaultItemAnimator());
        fetchMyOrder();


    }
    private void initToolbar() {
        binding.mtoolbar.myToolbar.setTitle(R.string.my_order_tittle);
        binding.mtoolbar.myToolbar.setNavigationIcon(R.drawable.ic_back_nav);
        binding.mtoolbar.myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void setOrderAdapter(com.attribe.delivo.app.models.response.MyOrders orders)
    {
        OrdersAdapter adapter=new OrdersAdapter(orders,this);
        binding.myOrdersRecyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
    private void showProgress(String message) {
        progressDialog = ProgressDialog.show(MyOrders.this, "", message, false);
    }

    private void hideProgress() {
        progressDialog.dismiss();
    }

    private void fetchMyOrder(){
        showProgress("Loading..");
        OrderBAL.getMyOrders(this, new ResponseCallback<com.attribe.delivo.app.models.response.MyOrders>() {
            @Override
            public void onSuccess(com.attribe.delivo.app.models.response.MyOrders response) {
                hideProgress();
                setOrderAdapter(response);

            }

            @Override
            public void OnResponseFailure(ErrorBody error_body) {

                hideProgress();
                AlertDialouge.showError(MyOrders.this,error_body.getError_msg());
            }

            @Override
            public void onfailure(String message) {
                hideProgress();
                SnackBars.showMessage(binding.getRoot(),message);

            }
        });
    }
}
