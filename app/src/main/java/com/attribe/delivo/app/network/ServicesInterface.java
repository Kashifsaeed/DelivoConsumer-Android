package com.attribe.delivo.app.network;

import com.attribe.delivo.app.models.EditUserProfile;
import com.attribe.delivo.app.models.NewOrder;
import com.attribe.delivo.app.models.NewUser;
import com.attribe.delivo.app.models.UpdatOrderStatus;
import com.attribe.delivo.app.models.User;
import com.attribe.delivo.app.models.request.DeliveryItem;
import com.attribe.delivo.app.models.request.OrderCreate;
import com.attribe.delivo.app.models.response.AuthenticationResponse;
import com.attribe.delivo.app.models.response.ConsumerOrders;
import com.attribe.delivo.app.models.response.DeliverRequestResponse;
import com.attribe.delivo.app.models.response.GenerateTokenResponse;
import com.attribe.delivo.app.models.response.MessageResponse;
import com.attribe.delivo.app.models.response.ResponseConfirmOrder;
import com.attribe.delivo.app.models.response.ResponseNewOrder;
import com.attribe.delivo.app.models.response.SignUpResponse;

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

    @POST(ServerURL.STAGE_URL_10 + "guest")
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

    @FormUrlEncoded
    @POST(ServerEndpoints.sign_in)
    Call<AuthenticationResponse> signIn(@Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST(ServerEndpoints.sign_up)
    Call<SignUpResponse> signUpUSer(@Field("name") String username , @Field("email") String email,
                                    @Field("phone") String phone_no, @Field("password") String password,
                                    @Field("password_confirmation") String confirm_password);

    @FormUrlEncoded
    @POST(ServerEndpoints.verify_pin)
    Call<AuthenticationResponse> verifyPin(@Field("hidden_phone") String phone ,
                                   @Field("hidden_password") String password,
                                    @Field("pin") String pin);

    @FormUrlEncoded
    @POST(ServerEndpoints.resent_pin)
    Call<MessageResponse> resentPin(@Field("hidden_phone") String phone,
                                    @Field("hidden_password") String password);
    @POST(ServerEndpoints.order_create)
    Call<Object> placeOrder(@Body OrderCreate orderCreate);
}