package com.attribe.delivo.app.network.interfaces;

import com.attribe.delivo.app.network.EndPoints;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by attribe on 8/23/16.
 */
public class GoogleClient {
    private static GoogleApiInterface googleclient;


    static {
        setupClient();
    }

    private static void setupClient() {

        // Todo remove log level while release

       // setupAuthClient();
       // setupRestClient();
        setupGoogleClient();

    }
    private static void setupGoogleClient() {


        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(EndPoints.Google_base_url).
                addConverterFactory(GsonConverterFactory.create()).
                //client(httpClient).
                build();

        googleclient = retrofit.create(GoogleApiInterface.class);
    }







    public static GoogleApiInterface googleEndclient(){

      //  return googleEndclient();
        return googleclient;
    }
}
