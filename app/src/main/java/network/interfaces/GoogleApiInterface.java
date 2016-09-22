package network.interfaces;

import models.response.AutoCompleteResponse;
import models.response.GoogleAPiByText;
import models.response.GoogleApi;
import models.response.PlaceDetailsResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Maaz on 8/22/2016.
 */
public interface GoogleApiInterface {
    @GET("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
    Call<GoogleApi> getLocationSearches(@Field("location") String location,
                                        @Field("radius") String radius,
                                        @Field("key") String key);

    @GET("maps/api/place/textsearch/json")
   // @FormUrlEncoded
    Call<GoogleAPiByText> getPlacesByText(@Query("query") String query,
                                          @Query("types") String types
                                          ,@Query("keyword") String keyword,
                                          @Query("key") String key);

    @GET("maps/api/place/autocomplete/json")
    Call<AutoCompleteResponse> getplacepridiction(@Query("input") String input,
                                                  @Query("types") String types,
                                                  @Query("key") String key );

    @GET("maps/api/place/details/json")
    Call<PlaceDetailsResponse> getplaceDetails(@Query("placeid") String placeId,
                                               @Query("key") String Key);




}
