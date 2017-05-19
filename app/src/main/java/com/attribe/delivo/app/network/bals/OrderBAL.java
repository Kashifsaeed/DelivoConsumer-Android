package com.attribe.delivo.app.network.bals;

import com.attribe.delivo.app.models.NewOrder;
import com.attribe.delivo.app.models.UpdatOrderStatus;
import com.attribe.delivo.app.models.request.OrderCreate;
import com.attribe.delivo.app.models.response.ResponseConfirmOrder;
import com.attribe.delivo.app.models.response.ResponseNewOrder;
import com.attribe.delivo.app.network.RestClient;
import com.attribe.delivo.app.network.interfaces.CreateOrderResponse;
import com.attribe.delivo.app.network.interfaces.OrderConfirmListener;
import com.attribe.delivo.app.network.interfaces.ResponseCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sabih Ahmed on 09-Jun-16.
 */
public class OrderBAL {


    public static void createOrder(NewOrder newOrder, final CreateOrderResponse callback){


        Call<ResponseNewOrder> order = RestClient.getAuthRestAdapter().createOrder(newOrder);

        order.enqueue(new Callback<ResponseNewOrder>() {
            @Override
            public void onResponse(Call<ResponseNewOrder> call, Response<ResponseNewOrder> response) {

                if(response.body() != null){
                    callback.orderCreatedSuccessfully(response.body());

                }

                if(response.errorBody()!=null){
                    //response has errors
                    if(response.raw().message().equals("Unauthorized")){

                        callback.tokenExpired();
                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseNewOrder> call, Throwable t) {

                callback.failure(t.getMessage().toString());
            }
        });

    }


    public static void confirmOrder(UpdatOrderStatus updatOrderStatus,String orderID, final OrderConfirmListener orderConfirmListener) {

        Call<ResponseConfirmOrder> confirmOrder = RestClient.getAuthRestAdapter().confirmOrder(updatOrderStatus,orderID);

        confirmOrder.enqueue(new Callback<ResponseConfirmOrder>() {
            @Override
            public void onResponse(Call<ResponseConfirmOrder> call, Response<ResponseConfirmOrder> response) {

                if(response.isSuccessful()){
                    if(response.body()!=null){//safe check
                        orderConfirmListener.OnOrderConfirmed();
                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseConfirmOrder> call, Throwable t) {

            }
        });

    }
    public static void placeOrder(final OrderCreate orderCreate, final ResponseCallback<Object> listner){
        RestClient.getAuthRestAdapter().placeOrder(orderCreate).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response)
            {
                if (response.isSuccessful())
                {
                    listner.onSuccess(response.body());

                }
                if(response.errorBody()!=null)
                {
                    listner.OnResponseFailure("Some server error");

                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                listner.onfailure(t.getMessage());

            }
        });

    }
}
