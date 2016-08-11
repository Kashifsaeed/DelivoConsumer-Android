package network;

import models.User;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.DevicePreferences;

import java.io.IOException;

/**
 * Created by Sabih Ahmed on 06-Jun-16.
 */
public class RestClient {

    private static ServicesInterface authClient;
    private static ServicesInterface authRestClient;

    static {
        setupClient();
    }

    private static void setupClient() {

        // Todo remove log level while release

        setupAuthClient();
        setupRestClient();

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
                                .addHeader("Authorization", "bearer " + User.getInstance().getUserToken().getAccess_token())
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(logging)
//                .connectTimeout(ApplicationConstant.TIMEOUT, TimeUnit.MILLISECONDS)
                //.authenticator()
                .authenticator(new RestAuthenticator())
                .build();

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(EndPoints.STAGE_URL_10).
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
                        .addHeader("Authorization", "Basic NDlhMmRiZTExN2E0NDdjZWFmYjhiOTZiNTIwMTE2ZTY6ZDYzODQ0YjU2MDE3NDI4NjlhODQwNzRhYWZmNGNiNjY=")
                        .build();
                return chain.proceed(newRequest);
            }
        }).addInterceptor(logging).build();

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(EndPoints.STAGE_URL_BASE).
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
}


