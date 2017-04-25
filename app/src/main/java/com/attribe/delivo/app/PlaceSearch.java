package com.attribe.delivo.app;

import Extras.MyConstants;
import adapters.GoogleSearchesAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import models.response.AutoCompleteResponse;
import models.response.GoogleAPiByText;
import models.response.PlaceDetailsResponse;
import network.interfaces.GoogleClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.CustomEditText;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

public class PlaceSearch extends Activity {
   CustomEditText searchedittext;
    private TextView searchstatus;
    GoogleSearchesAdapter adapter;
    RecyclerView mylist;
    private ProgressDialog mprogress;
    private FrameLayout frameLayout_visible;
    private String searchQuery;
    private boolean pickLocationScr,droplocationScr;
    private Timer timer;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_search);
       // getIntentValues();

        initViews();
    }

    private void getIntentValues()
    {
        if(getIntent().getExtras()!=null) {
            searchQuery = (String) getIntent().getExtras().get("Query");
            pickLocationScr = getIntent().getBooleanExtra("PickFlag", true);
        }

    }


    private void initViews() {
        getIntentValues();
        toolbar= (Toolbar) findViewById(R.id.my_toolbar);
        inittoolbar(toolbar);
        searchedittext= (CustomEditText) findViewById(R.id.searchaddress);
        mylist= (RecyclerView) findViewById(R.id.SearchList);
        frameLayout_visible= (FrameLayout) findViewById(R.id.searchstatusframe);
        // searchedittext.setDrawableClickListener(new SearchPlacesListner());

        Log.d("Test log","dum log");
        searchedittext.addTextChangedListener(new SearchTextListner());
        searchstatus= (TextView) findViewById(R.id.searchstatusText);
        if(searchQuery!=null) {
            //getPlacesByTextSearch(searchQuery);
            getAutocompleteplaces(searchQuery+"+karachi");
        }
    }
    private void inittoolbar(Toolbar toolbar)
    {
        toolbar.setTitle("Search Location");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
              finish();
            }
        });


    }


    private class SearchPlacesListner implements CustomEditText.DrawableClickListener {
        @Override
        public void onClick(DrawablePosition target) {

           // getPlacesByTextSearch(searchedittext.getText().toString()+" in Karachi");
           // getPlacesByTextSearch(searchedittext.getText().toString()+" in New York");


        }

    }
    private void hideprogress(){
        mprogress.dismiss();


    }
    private void showProgress(String message){

        mprogress=ProgressDialog.show(PlaceSearch.this,"",message,false);
    }


    private void getAutocompleteplaces(String input){
        String key= MyConstants.Googlekey2;
        String types="geocode|establishment";
        showProgress("Searching...");
        GoogleClient.googleEndclient().getplacepridiction(input,types,key).enqueue(new Callback<AutoCompleteResponse>() {
            @Override
            public void onResponse(Call<AutoCompleteResponse> call, final Response<AutoCompleteResponse> response) {
                if(response.isSuccessful())
                {
                    if(response.body().getPredictions().size()>0) {

                        hideprogress();
                        if(searchstatus.getVisibility()==View.VISIBLE){
                            searchstatus.setVisibility(View.GONE);
                        }
                        mylist.setLayoutManager(new LinearLayoutManager(PlaceSearch.this));
                        adapter = new GoogleSearchesAdapter(PlaceSearch.this, response.body().getPredictions());
                        mylist.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        adapter.SetOnItemClickListner(new GoogleSearchesAdapter.OnItemClickListner() {
                            @Override
                            public void onItemClick(View view, int position) {
                                AutoCompleteResponse.Prediction place=response.body().getPredictions().get(position);
                               // PlaceDetailsResponse.Result place_dest= new PlaceDetailsResponse.Result();
                                getPlaceDetails(place.place_id);


//
                            }
                        });

                    }

                    else {
                        hideprogress();
                        searchstatus.setVisibility(View.VISIBLE);
                        // Toast.makeText(getApplicationContext(),response.body().getStatus(),Toast.LENGTH_SHORT).show();
                        adapter = new GoogleSearchesAdapter(PlaceSearch.this, response.body().getPredictions());
                        mylist.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                    }

                }
                if(response.errorBody()!=null){
                    hideprogress();
                    Toast.makeText(getApplicationContext(),"Error"+response.errorBody().toString(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AutoCompleteResponse> call, Throwable t) {
                hideprogress();
                Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();


            }
        });


    }


    private void getPlacesByTextSearch(String query){
       String type="bus_station|bank|mosque|hospitals";
        String keyword="establishment";
        String key= MyConstants.Googlekey2;
        showProgress("Searching...");

        GoogleClient.googleEndclient().getPlacesByText(query,type,keyword,key).enqueue(new Callback<GoogleAPiByText>() {
            @Override
            public void onResponse(Call<GoogleAPiByText> call, final Response<GoogleAPiByText> response) {
                if(response.isSuccessful())

                {
                    if (response.body().getResults().size() > 0)
                    {
                       //frameLayout_visible.setVisibility(View.GONE);

                        hideprogress();
                        if(searchstatus.getVisibility()==View.VISIBLE){
                            searchstatus.setVisibility(View.GONE);
                        }
                        mylist.setLayoutManager(new LinearLayoutManager(PlaceSearch.this));
                        //adapter = new GoogleSearchesAdapter(PlaceSearch.this, response.body().getResults());
                        mylist.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        adapter.SetOnItemClickListner(new GoogleSearchesAdapter.OnItemClickListner() {
                            @Override
                            public void onItemClick(View view, int position) {
                                GoogleAPiByText.Result place=response.body().getResults().get(position);
                                //Intent intent=new Intent(PlaceSearch.this,CustomPickLocation.class);
                                Intent intent = new Intent();
                                if(pickLocationScr) {
//                                    Intent intent = new Intent();
                                    intent.putExtra("SearchPlace", (Serializable) place);
                                    // setResult(RESULT_OK,intent);
                                    setResult(CustomPickLocation.RESULT_OK, intent);
                                    // setResult(CustomDropLocation.DropLocation_ResultsCode);

                                    //setResult(201,intent);
                                    pickLocationScr=false;
                                    finish();



                                }
                                else {

                                    intent.putExtra("SearchPlace", (Serializable) place);
                                    setResult(CustomDropLocation.RESULT_OK, intent);
                                    finish();
                                }

                            }
                        });

                    }

                    else
                    {

                        //frameLayout_visible.setVisibility(View.VISIBLE);
                        //mylist.setVisibility(View.INVISIBLE);
                        hideprogress();
                        searchstatus.setVisibility(View.VISIBLE);
                       // Toast.makeText(getApplicationContext(),response.body().getStatus(),Toast.LENGTH_SHORT).show();
                        //adapter = new GoogleSearchesAdapter(PlaceSearch.this, response.body().getResults());
                        mylist.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                    }
                }

                if(response.errorBody()!=null){
                    hideprogress();
                    Toast.makeText(getApplicationContext(),"Error"+response.errorBody().toString(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<GoogleAPiByText> call, Throwable t) {
                hideprogress();
                Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();

            }
        });




    }
    private void getPlaceDetails(String placesId)
    {
       // final PlaceDetailsResponse.Result[] placeDetailsResponse = new PlaceDetailsResponse.Result[1];

        String key=MyConstants.Googlekey2;
        GoogleClient.googleEndclient().getplaceDetails(placesId,key).enqueue(new Callback<PlaceDetailsResponse>() {

            @Override
            public void onResponse(Call<PlaceDetailsResponse> call, Response<PlaceDetailsResponse> response) {

                //placeDetailsResponse=response.body();

                 // placeDetailsResponse[0] =response.body().getResult();


                if(response.isSuccessful()){

                    PlaceDetailsResponse.Result placedeatils=response.body().getResult();
                    Intent intent = new Intent();
                    if(pickLocationScr){
                        intent.putExtra("SearchPlace", (Serializable) placedeatils);
                        setResult(CustomPickLocation.RESULT_OK, intent);
                        pickLocationScr=false;
                        finish();
                    }
                    else {

                        intent.putExtra("SearchPlace", (Serializable) placedeatils);
                                    setResult(CustomDropLocation.RESULT_OK, intent);
                                    finish();


                    }



                }
                if(response.errorBody()!=null){
                    Toast.makeText(getApplicationContext(),"Error"+response.errorBody().toString(),Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<PlaceDetailsResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();

            }

        });


    }

    private class SearchTextListner implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (timer != null) {
                timer.cancel();
            }

        }


        @Override
        public void afterTextChanged(Editable editable)
        {
            final Editable text=editable;
            timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    PlaceSearch.this.runOnUiThread(new TimerTask() {
                        @Override
                        public void run() {
                            if (text.length() >= 5) {
                                //getPlacesByTextSearch(searchedittext.getText().toString().trim() + " in karachi");
                               getAutocompleteplaces(searchedittext.getText().toString()+"+karachi");




                            }
                        }
                    });



                }
            },1000);






        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //this.overridePendingTransition(R.anim.transition_left_in, R.anim.transition_left_out);
    }


}
