package fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.attribe.delivo.app.NavigationUtils;
import com.attribe.delivo.app.PicknDropLocations;
import com.attribe.delivo.app.R;
import models.DeleveryOrderItem;
import models.NewOrder;
import models.response.ResponseNewOrder;
import network.bals.OrderBAL;
import network.interfaces.CreateOrderResponse;
import utils.DevicePreferences;

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
        order_pickAddress = (TextView)view.findViewById(R.id.tvPickAdrDetail) ;
        order_dropAdress  = (TextView)view.findViewById(R.id.tvDropAdrDetail) ;
        orderDescription = (EditText)view.findViewById(R.id.et_order_description_area) ;
        submitOrder = (Button)view.findViewById(R.id.placeOrderBtn) ;
    }

    private void makingOrder(final View v) {

        List<DeleveryOrderItem> paramList = new ArrayList<DeleveryOrderItem>();

        DeleveryOrderItem param = new DeleveryOrderItem( DevicePreferences.getInstance().getSourceLocationObject(),
                                                         DevicePreferences.getInstance().getDestinationLocationObject());
        paramList.add(param);

        String sourceAddress = DevicePreferences.getInstance().getSourceLocationObject().getAddress();
        String destinationAddress = DevicePreferences.getInstance().getDestinationLocationObject().getAddress();
        String order_description = orderDescription.getText().toString();

        order_pickAddress.setText(sourceAddress);
        order_dropAdress.setText(destinationAddress);

        NewOrder newOrder = new NewOrder(order_description, sourceAddress, destinationAddress,paramList);

        DevicePreferences.getInstance().setOrder(newOrder);

        submitOrder.setOnClickListener(new SubmitOrderListner());

    }

    private void login(final String orderId, View view) {

        mapLayout.setVisibility(view.GONE);
        containerLayout.setVisibility(view.VISIBLE);
        NavigationUtils.showConfirmationScreen(getFragmentManager(),orderId);
    }

    private class SubmitOrderListner implements View.OnClickListener {
        @Override
        public void onClick(final View v) {

            if(DevicePreferences.getInstance().getUser()!=null){

                OrderBAL.createOrder(DevicePreferences.getInstance().getOrder(), new CreateOrderResponse() {
                    @Override
                    public void orderCreatedSuccessfully(ResponseNewOrder body) {
                        Toast.makeText(getActivity(), " "+body.getData().getStatus().toString()+" , "+"Order created successfully ....", Toast.LENGTH_SHORT).show();
                        DevicePreferences.getInstance().setNewOrderResponse(body);

                        login(body.getData().getOrderid(),v);
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
}
