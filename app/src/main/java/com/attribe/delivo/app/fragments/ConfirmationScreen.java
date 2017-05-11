package com.attribe.delivo.app.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.attribe.delivo.app.NavigationUtils;
import com.attribe.delivo.app.R;
import com.attribe.delivo.app.models.UpdatOrderStatus;
import com.attribe.delivo.app.models.response.ResponseNewOrder;
import com.attribe.delivo.app.network.bals.OrderBAL;
import com.attribe.delivo.app.network.interfaces.CreateOrderResponse;
import com.attribe.delivo.app.network.interfaces.OrderConfirmListener;
import com.attribe.delivo.app.utils.DevicePreferences;

/**
 * Created by Maaz on 7/29/2016.
 */
public class ConfirmationScreen extends Fragment {

    private View view;
    private String orderID ="";
    private Button confirmButton;
    private ProgressDialog progressDialog;

//    public ConfirmationScreen() {
//        // Required empty public constructor
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_confirmation, container, false);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setHasOptionsMenu(true);

        initViews(view);
        fetchIntent();
        return view;

    }

    private void initViews(View view) {
        confirmButton = (Button)view.findViewById(R.id.confirm);
        confirmButton.setOnClickListener(new ConfirmOrderListener());
    }

    private void fetchIntent() {
    //    if(getArguments().get("KEY_ORDERID") != null){

              //orderID = getArguments().getString("KEY_ORDERID");

            if(orderID.equalsIgnoreCase(ScreenRegistration.STATUS_ORDER_DEFERRED)){

                //show progress bar loading
                //fetch order from preferences
                //place new order request
                //retrieve orderID and update orderID
                //release progress
                OrderBAL.createOrder(DevicePreferences.getInstance().getOrder(), new CreateOrderResponse() {
                    @Override
                    public void orderCreatedSuccessfully(ResponseNewOrder body) {
                        orderID = body.getData().getOrderid();
                    }

                    @Override
                    public void tokenExpired() {

                    }

                    @Override
                    public void failure(String s) {

                    }
                });

//            }

        }

    }


    private class ConfirmOrderListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            final UpdatOrderStatus updatOrderStatus=new UpdatOrderStatus();
            updatOrderStatus.setStatus("902");

            showProgress("Confirming your order ....");
            OrderBAL.confirmOrder(updatOrderStatus,DevicePreferences.getInstance().getResponseNewOrder().getData().getOrderid(),
                                  new OrderConfirmListener() {

                @Override
                public void OnOrderConfirmed() {

                    hideProgress();
                    Toast.makeText(getActivity(), "" + updatOrderStatus.getStatus() + ", your order is confirmed", Toast.LENGTH_SHORT).show();

                    NavigationUtils.showRiderDetailScreen(getFragmentManager());
                }

                @Override
                public void OnFailure() {
                    Toast.makeText(getActivity(), " Order confirmation failed !" , Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showProgress(String message) {
        progressDialog= ProgressDialog.show(getActivity(),"",message,false);
    }

    private void hideProgress(){progressDialog.dismiss();}
}
