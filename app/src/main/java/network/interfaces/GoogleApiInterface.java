package network.interfaces;

import models.response.GoogleApi;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;

/**
 * Created by Maaz on 8/22/2016.
 */
public interface GoogleApiInterface {
    @GET("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
    Call<GoogleApi> getLocationSearches(@Field("location") String location,
                                        @Field("radius") String radius,
                                        @Field("key") String key);
}
