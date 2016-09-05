package network.interfaces;

import network.EndPoints;
import network.ServicesInterface;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

/**
 * Created by attribe on 8/23/16.
 */
public class GoogleClient {
    private static GoogleEndpoints googleclient;


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
                baseUrl(EndPoints.GoogleApi_url).
                addConverterFactory(GsonConverterFactory.create()).
                //client(httpClient).
                build();

        googleclient = retrofit.create(GoogleEndpoints.class);
    }







    public static GoogleEndpoints googleEndclient(){

      //  return googleEndclient();
        return googleclient;
    }
}
