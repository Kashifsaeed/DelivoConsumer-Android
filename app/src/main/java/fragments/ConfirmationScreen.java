package fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.attribe.delivo.app.NavigationUtils;
import com.attribe.delivo.app.R;
import models.response.ResponseNewOrder;
import network.bals.OrderBAL;
import network.interfaces.CreateOrderResponse;
import network.interfaces.OrderConfirmListener;
import screens.Confirmation;
import utils.DevicePreferences;
import utils.Progress;

/**
 * Created by Maaz on 7/29/2016.
 */
public class ConfirmationScreen extends Fragment {

    private View view;
    private String orderID ="";
    private Button confirmButton;
    private Progress progress;

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

        progress = NavigationUtils.getProgress(false);
        initViews(view);
        fetchIntent();
        return view;

    }

    private void initViews(View view) {
        confirmButton = (Button)view.findViewById(R.id.confirm);
        confirmButton.setOnClickListener(new ConfirmOrderListener());

    }

    private void fetchIntent() {
        if(getArguments().get("KEY_ORDERID") != null){

//            orderID = getIntent().getStringExtra(NavigationUtils.KEY_ORDERID);
              orderID = getArguments().getString("KEY_ORDERID");

            if(orderID.equalsIgnoreCase(ScreenRegistration.STATUS_ORDER_DEFERRED)){
                progress.show(getFragmentManager(),"");
                //show progress bar loading
                //fetch order from preferences
                //place new order request
                //retrieve orderID and update orderID
                //release progress
                OrderBAL.createOrder(DevicePreferences.getInstance().getOrder(), new CreateOrderResponse() {
                    @Override
                    public void orderCreatedSuccessfully(ResponseNewOrder body) {

                        progress.dismiss();
                        orderID = body.getData().getOrderid();
                    }

                    @Override
                    public void tokenExpired() {
                        progress.dismiss();
                    }

                    @Override
                    public void failure(String s) {
                        progress.dismiss();
                    }
                });

            }

        }

    }

    public interface ConfirmationFragmentInteraction {

        public void onFragmentInteraction(Uri uri);
    }


    private class ConfirmOrderListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            OrderBAL.confirmOrder(orderID, new OrderConfirmListener() {
                @Override
                public void OnOrderConfirmed() {
                    NavigationUtils.showRiderDetailScreen(getActivity());
                }

                @Override
                public void OnFailure() {

                }
            });
        }
    }
}
