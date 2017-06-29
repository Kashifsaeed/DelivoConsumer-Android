package com.attribe.delivo.app.bals;

import android.content.Context;

import com.attribe.delivo.app.models.request.OrderCreate;
import com.attribe.delivo.app.models.response.ErrorBody;
import com.attribe.delivo.app.models.response.MyOrders;
import com.attribe.delivo.app.models.response.OrderResponse;
import com.attribe.delivo.app.network.RestClient;
import com.attribe.delivo.app.interfaces.ResponseCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author: Uzair Qureshi
 * Date:  4/21/17.
 * Description:
 */
public class OrderBAL {
    /**
     * This mehod of bal will hit the order creating api and get response
     * @param orderCreate
     * @param listner
     */
    public static void placeOrder(final OrderCreate orderCreate, final ResponseCallback<OrderResponse> listner){
        RestClient.getAuthRestAdapter().placeOrder(orderCreate).enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response)
            {
                if (response.isSuccessful())
                {
                    listner.onSuccess(response.body());

                }
              else if(response.errorBody()!=null)
                {
                    String message = "";

                    try {
                        message = new JSONObject(response.errorBody().string())
                                .getString("message");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ErrorBody errorBody = new ErrorBody(response.raw().code(), message);
                    listner.OnResponseFailure(errorBody);

                }

            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                if(t instanceof ConnectException)
                {
                    listner.onfailure("No Internet Connection!");
                }
                else
                {
                    listner.onfailure("Some thing went wrong!");


                }

            }
        });

    }

    public static void getMyOrders(Context context, final ResponseCallback<MyOrders> listner) {
        RestClient.getAuthRestAdapter().getMyOrders().enqueue(new Callback<MyOrders>() {
            @Override
            public void onResponse(Call<MyOrders> call, Response<MyOrders> response) {
                if (response.isSuccessful() && response.body().getMeta().getSuccess()) {
                    listner.onSuccess(response.body());//do whatever u want for this response

//                } else if (!response.body().getMeta().getSuccess()) {
//                    listner.onSuccess(response.body());
//

                } else if (response.errorBody() != null) {
                    String message = "";

                    try {
                        message = new JSONObject(response.errorBody().string())
                                .getString("message");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ErrorBody errorBody = new ErrorBody(response.raw().code(), message);
                    listner.OnResponseFailure(errorBody);

                }
            }

            @Override
            public void onFailure(Call<MyOrders> call, Throwable t) {
                if (t instanceof ConnectException) {
                    listner.onfailure("No Internet Connection!");
                } else {
                    listner.onfailure("Some thing went wrong!");


                }

            }
        });
    }
}
