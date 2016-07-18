package screens;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import android.widget.Button;
import com.attribe.delivo.app.NavigationUtils;
import com.attribe.delivo.app.R;
import models.response.ResponseNewOrder;
import network.bals.OrderBAL;
import network.interfaces.CreateOrderResponse;
import network.interfaces.OrderConfirmListener;
import utils.DevicePreferences;
import utils.Progress;


public class Confirmation extends FragmentActivity {



    private String orderID ="";
    private ConfirmationFragmentInteraction mListener;
    private Button confirmButton;
    private Progress progress;

//    public static Confirmation newInstance(String orderID) {
//        Confirmation fragment = new Confirmation();
//        Bundle args = new Bundle();
//
//        args.putString(KEY_ORDER_ID,orderID);
//        fragment.setArguments(args);
//        return fragment;
//    }

    public Confirmation() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_confirmation);

        progress = NavigationUtils.getProgress(false);
        init();
        fetchIntent();

    }

    private void fetchIntent() {
        if(getIntent() != null){

            orderID = getIntent().getStringExtra(NavigationUtils.KEY_ORDERID);

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

    /**
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirmation, container, false);

        init(view);
        return view;
    }**/

    private void init() {
        confirmButton = (Button)findViewById(R.id.confirm);
        confirmButton.setOnClickListener(new ConfirmOrderListener());
    }


    /**
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (ConfirmationFragmentInteraction) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ConfirmationFragmentInteraction");
        }
    }**/

    /**
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }**/


    public interface ConfirmationFragmentInteraction {

        public void onFragmentInteraction(Uri uri);
    }

    private class ConfirmOrderListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            OrderBAL.confirmOrder(orderID, new OrderConfirmListener() {
                @Override
                public void OnOrderConfirmed() {
                    NavigationUtils.showRiderDetailScreen(Confirmation.this);
                }

                @Override
                public void OnFailure() {

                }
            });
        }
    }
}
