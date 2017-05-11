package com.attribe.delivo.app.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.attribe.delivo.app.NavigationUtils;
import com.attribe.delivo.app.R;
import com.attribe.delivo.app.models.NewOrder;
import com.attribe.delivo.app.models.response.ResponseNewOrder;
import com.attribe.delivo.app.network.bals.OrderBAL;
import com.attribe.delivo.app.network.interfaces.CreateOrderResponse;
import com.attribe.delivo.app.utils.DevicePreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maaz on 8/12/2016.
 */
public class OrderMaking extends Fragment {

    private View view;
    private LinearLayout containerLayout;
    private LinearLayout mapLayout;
    private TextView order_pickAddress, order_dropAdress;
    private EditText orderDescription;
    private Button submitOrder;
    private ProgressDialog progressDialog;

    List<NewOrder.DeleveryOrderItem> paramList;
    String order_description;
    String sourceAddress;
    String destinationAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_order_making, container, false);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setHasOptionsMenu(true);

        init(view);
        makingOrder(view);
        return view;
    }

    private void init(View view) {
        containerLayout = (LinearLayout)getActivity().findViewById(R.id.containerLayout);
        mapLayout = (LinearLayout)getActivity().findViewById(R.id.mapLayout);
        orderDescription = (EditText)view.findViewById(R.id.et_order_description_area) ;
        order_pickAddress = (TextView)view.findViewById(R.id.tvPickAdrDetail) ;
        order_dropAdress  = (TextView)view.findViewById(R.id.tvDropAdrDetail) ;
        submitOrder = (Button)view.findViewById(R.id.placeOrderBtn) ;
    }

    private void makingOrder(final View v) {

        paramList = new ArrayList<NewOrder.DeleveryOrderItem>();
        NewOrder.DeleveryOrderItem param = new NewOrder().new DeleveryOrderItem( DevicePreferences.getInstance().getSourceLocationObject(),
                                                         DevicePreferences.getInstance().getDestinationLocationObject());
        paramList.add(param);

        sourceAddress = DevicePreferences.getInstance().getSourceLocationObject().getAddress();
        destinationAddress = DevicePreferences.getInstance().getDestinationLocationObject().getAddress();

        order_pickAddress.setText(sourceAddress);
        order_dropAdress.setText(destinationAddress);

        submitOrder.setOnClickListener(new SubmitOrderListner());

    }

//    private void login(final String orderId, View view) {
//
//        mapLayout.setVisibility(view.GONE);
//        containerLayout.setVisibility(view.VISIBLE);
//        NavigationUtils.showConfirmationScreen(getFragmentManager(),orderId);
//    }

    private class SubmitOrderListner implements View.OnClickListener {
        @Override
        public void onClick(final View v) {

            order_description = orderDescription.getText().toString();
            NewOrder newOrder = new NewOrder(order_description, sourceAddress, destinationAddress,paramList);
            DevicePreferences.getInstance().setOrder(newOrder);

            if(DevicePreferences.getInstance().getUser()!=null){

                showProgress("Submitting your order ....");
                OrderBAL.createOrder(newOrder , new CreateOrderResponse() {
                    @Override
                    public void orderCreatedSuccessfully(ResponseNewOrder body) {

                        hideProgress();
                        Toast.makeText(getActivity(), " "+body.getData().getStatus().toString()+" , "+"Order created successfully ....", Toast.LENGTH_SHORT).show();
                        DevicePreferences.getInstance().setNewOrderResponse(body);

//                        login(body.getData().getOrderid(),v);
                        mapLayout.setVisibility(view.GONE);
                        containerLayout.setVisibility(view.VISIBLE);
                        NavigationUtils.showConfirmationScreen(getFragmentManager(),
                                                               DevicePreferences.getInstance().getResponseNewOrder().getData().getOrderid());
//                        NavigationUtils.showConfirmationScreen(getFragmentManager(),body.getData().getOrderid());
                    }

                    @Override
                    public void tokenExpired() {
                        Toast.makeText(getActivity(), "Token expired !", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(String s) {

                    }
                });
            }
            else{
//                mapLayout.setVisibility(v.GONE);
//                containerLayout.setVisibility(v.VISIBLE);
                NavigationUtils.showUserAuthScreen(getActivity(),getFragmentManager());
            }
        }
    }

    private void showProgress(String message) {
        progressDialog= ProgressDialog.show(getActivity(),"",message,false);
    }

    private void hideProgress(){progressDialog.dismiss();}
}
