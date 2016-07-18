package network;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.DevicePreferences;

import java.io.IOException;

/**
 * Created by Sabih Ahmed on 06-Jun-16.
 */
public class RestClient {

    private static ServicesInterface servicesInterface;

    private RestClient(){

    }

    static{

        setupRestClient();
    }

    public static void setupRestClient() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder().
                        addHeader("Authorization", DevicePreferences.getInstance().getHeader()).
                        build();

                return chain.proceed(newRequest);
            }
        };


        // Add the interceptor to OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(interceptor);

        OkHttpClient client = builder.build();



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EndPoints.STAGE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        servicesInterface = retrofit.create(ServicesInterface.class);
    }

    public static ServicesInterface getAdapter(){

        return servicesInterface;

    }





}
