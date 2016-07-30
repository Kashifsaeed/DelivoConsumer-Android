package network;

import models.NewOrder;
import models.NewUser;
import models.User;
import models.response.GenerateTokenResponse;
import models.response.ResponseConfirmOrder;
import models.response.ResponseGuestSignup;
import models.response.ResponseNewOrder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.*;

/**
 * Created by Sabih Ahmed on 06-Jun-16.
 */
public interface ServicesInterface {



    @POST("deleveryorder")
    Call<ResponseNewOrder> createOrder(@Body NewOrder newOrder);

    @FormUrlEncoded
    @POST("http://stage.dmenu.co:8080/delvo-api/oauth/token")
    Call<GenerateTokenResponse> login(@Field("grant_type") String grant_type,
                                      @Field("username") String username,
                                      @Field("password") String password,
                                      @Field("scope") String scope);

    @POST("http://stage.dmenu.co:8080/delvo-api/oauth/token")
    Call<GenerateTokenResponse> refresh(@Field("grant_type") String grant_type,
                                        @Field("username") String username,
                                        @Field("refresh_token") String refresh_token,
                                        @Field("scope") String scope);

    @POST("guest")
    Call<User> createUser(@Body NewUser user);

    @PUT("deleveryorder/confirm/{orderid}")
    Call<ResponseConfirmOrder> confirmOrder(@Path("orderid") String orderID);
}
