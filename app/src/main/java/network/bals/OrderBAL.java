package network.bals;

import android.widget.Toast;
import models.NewOrder;
import models.UpdatOrderStatus;
import models.response.GenerateTokenResponse;
import models.response.ResponseConfirmOrder;
import models.response.ResponseNewOrder;
import network.interfaces.CreateOrderResponse;
import network.RestClient;
import network.interfaces.OrderConfirmListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.DevicePreferences;

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
}
