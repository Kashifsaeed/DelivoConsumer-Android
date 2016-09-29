package com.attribe.delivo.app;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.google.android.gms.maps.model.LatLng;
import models.NewOrder;
import models.UpdatOrderStatus;
import models.response.ResponseConfirmOrder;
import models.response.ResponseNewOrder;
import network.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class OrderCreating extends AppCompatActivity {

    private TextView txt_pickAddress_details, txt_dropAddress_details;
    private EditText order_description_edittxt;
    private Button createOrder_btn;
    private  String pickadd,dropadd;
    private LatLng pickltlng,dropltlng;
    NewOrder.SourceLocation mSouradd;
    NewOrder.DestinationLocation mDestinationadd;
    ArrayList<NewOrder.DeleveryOrderItem> mdeliveryorderItems;
    private ProgressDialog mprogress;
    private FrameLayout orderconfirm_frame;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_creating);
        initViews();
    }


    private void initViews() {
        orderconfirm_frame= (FrameLayout) findViewById(R.id.confirmorder_frame);
        txt_pickAddress_details = (TextView) findViewById(R.id.tvPickAdrDetail);
        txt_dropAddress_details = (TextView) findViewById(R.id.tvDropAdrDetail);
        order_description_edittxt= (EditText) findViewById(R.id.et_order_description_area);
        createOrder_btn= (Button) findViewById(R.id.placeOrderBtn);
        getOrderIntent();
        createOrder_btn.setOnClickListener(new CreateOrderListner());


    }
    private void getOrderIntent()
    {
        Bundle bundle=this.getIntent().getBundleExtra("Orderinfo");
        if(bundle!=null)
        {
            pickadd=bundle.getString("pickAddress");
            dropadd=bundle.getString("dropAddress");
            pickltlng=bundle.getParcelable("pickLatLng");
            dropltlng=bundle.getParcelable("dropLatLng");
            txt_pickAddress_details.setText(""+pickadd);
            txt_dropAddress_details.setText(""+dropadd);
//            String mSourcelat=String.valueOf(pickltlng.latitude);
//            String mSorcise

        }

    }
    private NewOrder.SourceLocation getSource(LatLng picklocation,String p_add)
    {
        String plat=String.valueOf(picklocation.latitude);
        String plong=String.valueOf(picklocation.longitude);


        return mSouradd=new NewOrder().new SourceLocation(plat,plong,p_add);
    }

    private NewOrder.DestinationLocation getDestination(LatLng droplocation,String d_add){
        String dlat=String.valueOf(droplocation.latitude);
        String dlong=String.valueOf(droplocation.longitude);


        return mDestinationadd=new NewOrder().new DestinationLocation(dlat,dlong,d_add);


    }

    private ArrayList<NewOrder.DeleveryOrderItem> createdeliveryList(NewOrder.SourceLocation picklocation, NewOrder.DestinationLocation droplocation){
        ArrayList<NewOrder.DeleveryOrderItem> deleveryOrderItems =new ArrayList<NewOrder.DeleveryOrderItem>();

        NewOrder.DeleveryOrderItem mOrder=new NewOrder().new DeleveryOrderItem(picklocation,droplocation) ;
        deleveryOrderItems.add(mOrder);


      return deleveryOrderItems;

    }
    private void confirmOrders(UpdatOrderStatus updatOrderStatus, String order_id){
        RestClient.getAuthRestAdapter().confirmOrder(updatOrderStatus,order_id).enqueue(new Callback<ResponseConfirmOrder>() {
            @Override
            public void onResponse(Call<ResponseConfirmOrder> call, Response<ResponseConfirmOrder> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),""+response.body().getMeta().getMessage(),Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<ResponseConfirmOrder> call, Throwable t) {

            }
        });



    }
    private void placesOrder(String orderDesc,String pickadd,String dropadd,List<NewOrder.DeleveryOrderItem> deleveryOrderItem)
    {
        showProgress("Loading..");
        NewOrder mOrder=new NewOrder(orderDesc,pickadd,dropadd,deleveryOrderItem);
        RestClient.getAuthRestAdapter().createOrder(mOrder).enqueue(new Callback<ResponseNewOrder>() {
            @Override
            public void onResponse(Call<ResponseNewOrder> call, Response<ResponseNewOrder> response) {
                if(response.isSuccessful()){
                    hideprogress();

                    Toast.makeText(getApplicationContext(),"Sccessfully place Order",Toast.LENGTH_SHORT).show();


                }
                if(response.errorBody()!=null){
                    hideprogress();

                    Toast.makeText(getApplicationContext(),"Error body",Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<ResponseNewOrder> call, Throwable t) {
                hideprogress();

            }
        });



    }
    private void hideprogress(){
        mprogress.dismiss();


    }
    private void showProgress(String message){

        mprogress= ProgressDialog.show(OrderCreating.this,"",message,false);
    }




    private class CreateOrderListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //NewOrder order =new NewOrder();

            mdeliveryorderItems =  createdeliveryList(getSource(pickltlng,pickadd),getDestination(dropltlng,dropadd));
            placesOrder(order_description_edittxt.getText().toString(),pickadd,dropadd,mdeliveryorderItems);



        }
    }
}
