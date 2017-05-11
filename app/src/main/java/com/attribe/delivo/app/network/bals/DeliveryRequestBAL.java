package com.attribe.delivo.app.network.bals;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import com.attribe.delivo.app.Extras.CallBackResponse;
import com.attribe.delivo.app.models.request.DeliveryItem;
import com.attribe.delivo.app.models.response.DeliverRequestResponse;
import com.attribe.delivo.app.network.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author: Uzair Qureshi
 * Date:  2/15/17.
 * Description:
 */

public class DeliveryRequestBAL
{
    public static void createNewRequest(DeliveryItem item, final CallBackResponse<DeliverRequestResponse> listner)
    {
        RestClient.getAuthAdapter().createnewRequest(item).enqueue(new Callback<DeliverRequestResponse>() {
            @Override
            public void onResponse(Call<DeliverRequestResponse> call, Response<DeliverRequestResponse> response)
            {
                if(response.isSuccessful()&&response.body()!=null)
                {
                    listner.onSuccess(response.body());

                }
                if(response.errorBody()!=null)
                {
                    try {

                        String message = new JSONObject(response.errorBody().string())
                                .getJSONObject("meta")
                                .getString("message");

                         listner.onFailure(message);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

            }

            @Override
            public void onFailure(Call<DeliverRequestResponse> call, Throwable t)
            {
                listner.onFailure(t.getMessage());

            }
        });

    }
}
