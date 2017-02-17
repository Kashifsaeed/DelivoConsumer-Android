package network;

import models.*;
import models.request.*;
import models.response.*;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by Sabih Ahmed on 06-Jun-16.
 */
public interface ServicesInterface {


    @POST("deleveryorder")
    Call<ResponseNewOrder> createOrder(@Body NewOrder newOrder);

    @FormUrlEncoded

    @POST("oauth/token")
    Call<GenerateTokenResponse> login(@Field("grant_type") String grant_type,
                                      @Field("username") String username,
                                      @Field("password") String password,
                                      @Field("scope") String scope);

    @POST("http://stage.dmenu.co:8080/delvo-api/oauth/token")
    Call<GenerateTokenResponse> refresh(@Field("grant_type") String grant_type,
                                        @Field("username") String username,
                                        @Field("refresh_token") String refresh_token,
                                        @Field("scope") String scope);

    @POST(EndPoints.STAGE_URL_10 + "guest")
    Call<User> createUser(@Body NewUser user);

    @PUT("deleveryorder/confirm/{orderid}")
    Call<ResponseConfirmOrder> confirmOrder(@Body UpdatOrderStatus updatOrderStatus, @Path("orderid") String orderID);

    @POST("guest")
    Call<User> userEditProfile(@Body EditUserProfile editUserProfile);

    //    @GET("deleveryorder")
//    void getUserOrders(Callback<ArrayList<ConsumerOrders>> callback);
    @GET("deleveryorder")
    Call<ConsumerOrders> getUserOrders();
//============================================= Rails APi Endpoints ================================================================//


    @POST(ServerEndpoints.delivery_request)
    Call<DeliverRequestResponse> createnewRequest(@Body DeliveryItem deliveryItem);
}