package com.attribe.delivo.app;

import Extras.MyConstants;
import adapters.GoogleSearchesAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import models.response.GoogleAPiByText;
import network.interfaces.GoogleClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.CustomEditText;

public class PlaceSearch extends Activity {
   CustomEditText searchedittext;
    GoogleSearchesAdapter adapter;
    RecyclerView mylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_search);
        initViews();
    }

    private void initViews() {
        searchedittext= (CustomEditText) findViewById(R.id.searchaddress);
        mylist= (RecyclerView) findViewById(R.id.SearchList);
        searchedittext.setDrawableClickListener(new SearchPlacesListner());
    }


    private class SearchPlacesListner implements CustomEditText.DrawableClickListener {
        @Override
        public void onClick(DrawablePosition target) {

            getPlacesByTextSearch(searchedittext.getText().toString()+"in Karachi");

        }

    }

    private void getPlacesByTextSearch(String query){
        String type="bank|mosque|hospitals";
        String key= MyConstants.Googlekey;

        GoogleClient.googleEndclient().getPlacesByText(query,type,key).enqueue(new Callback<GoogleAPiByText>() {
            @Override
            public void onResponse(Call<GoogleAPiByText> call, Response<GoogleAPiByText> response) {
                if(response.isSuccessful()) {
                    mylist.setLayoutManager(new LinearLayoutManager(PlaceSearch.this));
                    adapter = new GoogleSearchesAdapter(PlaceSearch.this, response.body().getResults());
                    mylist.setAdapter(adapter);
                }
                if(response.errorBody()!=null){

                    Toast.makeText(getApplicationContext(),"Error"+response.errorBody().toString(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<GoogleAPiByText> call, Throwable t) {

            }
        });




    }
}
