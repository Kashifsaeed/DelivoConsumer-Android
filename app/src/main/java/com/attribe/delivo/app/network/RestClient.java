package com.attribe.delivo.app.network;

import com.attribe.delivo.app.Extras.AppConstants;
import com.attribe.delivo.app.network.interfaces.GoogleApiInterface;
import com.attribe.delivo.app.utils.DevicePreferences;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sabih Ahmed on 06-Jun-16.
 */
public class RestClient {

    private static ServicesInterface authClient;
    private static ServicesInterface authRestClient;
    private static GoogleApiInterface googleApiInterface;

    static {
        setupClient();
    }

    private static void setupClient() {

        // Todo remove log level while release

        setupAuthClient();
        setupRestClient();
        //setGoogleAPiclient();

    }
    private static void setGoogleAPiclient(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request newRequest;
                        newRequest = request.newBuilder()
                               // .addHeader("Authorization", "bearer " + User.getInstance().getUserToken().getAccess_token())
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(logging)
//                .connectTimeout(ApplicationConstant.TIMEOUT, TimeUnit.MILLISECONDS)
                //.authenticator()
               // .authenticator(new RestAuthenticator())
                .build();

        Retrofit retrofit = new Retrofit.Builder().
               // baseUrl(ServerURL.STAGE_URL_10).
                addConverterFactory(GsonConverterFactory.create()).
                client(httpClient).
                build();

        googleApiInterface = retrofit.create(GoogleApiInterface.class);


    }


    private static void setupRestClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request newRequest;
                        newRequest = request.newBuilder()
                              //  .addHeader("Authorization", "bearer " + User.getInstance().getUserToken().getAccess_token())
                                .addHeader("COMPANYAPIKEY",AppConstants.COMPANY_KEY)
                                .addHeader("Authorization", DevicePreferences.getInstance().getAuthKey().getAuth_token())
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(logging)
                .connectTimeout(AppConstants.TIMEOUT, TimeUnit.MILLISECONDS)
                //.authenticator()
                //.authenticator(new RestAuthenticator())
                .build();

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(ServerURL.BASE_URl).
                addConverterFactory(GsonConverterFactory.create()).
                client(httpClient).
                build();

        authRestClient = retrofit.create(ServicesInterface.class);
    }

    private static void setupAuthClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request newRequest;
                newRequest = request.newBuilder()
                        .addHeader("COMPANYAPIKEY", AppConstants.COMPANY_KEY)
                        .build();
                return chain.proceed(newRequest);
            }
        }).addInterceptor(logging)
                .connectTimeout(AppConstants.TIMEOUT, TimeUnit.MILLISECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(ServerURL.BASE_URl).
                //baseUrl(ServerURL.STAGE_URL_10).
                addConverterFactory(GsonConverterFactory.create()).
                client(httpClient).
                build();

        authClient = retrofit.create(ServicesInterface.class);
    }





    public static ServicesInterface getAuthAdapter(){
        return authClient;
    }
    public static ServicesInterface getAuthRestAdapter(){
        return authRestClient;
    }

    public static GoogleApiInterface getGoogleApiInterface(){
        return googleApiInterface;
    }

}


