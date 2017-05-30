package com.attribe.delivo.app.screens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.attribe.delivo.app.Extras.AppConstants;
import com.attribe.delivo.app.R;
import com.attribe.delivo.app.adapters.GoogleSearchesAdapter;
import com.attribe.delivo.app.databinding.PlaceSearchActivityBinding;
import com.attribe.delivo.app.fragments.DropLocationFragment;
import com.attribe.delivo.app.fragments.PickLocationFragment;
import com.attribe.delivo.app.interfaces.OnPlaceSearchListner;
import com.attribe.delivo.app.models.response.AutoCompleteResponse;
import com.attribe.delivo.app.models.response.PlaceDetailsResponse;
import com.attribe.delivo.app.network.GoogleClient;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceSearchActivity extends AppCompatActivity implements OnPlaceSearchListner {
    private PlaceSearchActivityBinding databindinig;
    private ProgressDialog mprogress;
    private GoogleSearchesAdapter adapter;
    private Timer timer;
    private String searchQuery;
    private boolean pickLocationScr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databindinig = DataBindingUtil.setContentView(PlaceSearchActivity.this, R.layout.place_search_activity);

        initViews();

    }

    private void initViews() {
        getIntentValues();
        inittoolbar(databindinig.toolbarMain.myToolbar);
        databindinig.searchaddress.addTextChangedListener(new SearchTextListner());
        if(searchQuery!=null) {
            //getPlacesByTextSearch(searchQuery);
            getAutocompleteplaces(searchQuery+"+karachi");
        }
    }

    private void getIntentValues() {
        if(getIntent().getExtras()!=null) {
            searchQuery = (String) getIntent().getExtras().get("Query");
            pickLocationScr = getIntent().getBooleanExtra("Flag", true);
        }
    }

    private void inittoolbar(Toolbar toolbar) {
        toolbar.setTitle("Search Location");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void hideprogress() {
        mprogress.dismiss();


    }

    private void showProgress(String message) {

        mprogress = ProgressDialog.show(PlaceSearchActivity.this, "", message, false);
    }


    private void getAutocompleteplaces(String input) {
        String key = AppConstants.Googlekey2;
        String types = "geocode|establishment";
        showProgress("Searching...");
        GoogleClient.googleEndclient().getplacepridiction(input, types, key).enqueue(new Callback<AutoCompleteResponse>() {
            @Override
            public void onResponse(Call<AutoCompleteResponse> call, final Response<AutoCompleteResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getPredictions().size() > 0) {

                        hideprogress();
                        if (databindinig.searchstatusText.getVisibility() == View.VISIBLE) {
                            databindinig.searchstatusText.setVisibility(View.GONE);
                        }
                        databindinig.SearchList.setLayoutManager(new LinearLayoutManager(PlaceSearchActivity.this));
                        adapter = new GoogleSearchesAdapter(PlaceSearchActivity.this, response.body().getPredictions());
                        databindinig.SearchList.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        adapter.SetOnItemClickListner(new GoogleSearchesAdapter.OnItemClickListner() {
                            @Override
                            public void onItemClick(View view, int position) {
                                AutoCompleteResponse.Prediction place = response.body().getPredictions().get(position);
                                // PlaceDetailsResponse.Result place_dest= new PlaceDetailsResponse.Result();
                                 getPlaceDetails(place.place_id);


//
                            }
                        });

                    } else {
                        hideprogress();
                        databindinig.searchstatusText.setVisibility(View.VISIBLE);
                        // Toast.makeText(getApplicationContext(),response.body().getStatus(),Toast.LENGTH_SHORT).show();
                        adapter = new GoogleSearchesAdapter(PlaceSearchActivity.this, response.body().getPredictions());
                        databindinig.SearchList.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                    }

                }
                if (response.errorBody() != null) {
                    hideprogress();
                    Toast.makeText(getApplicationContext(), "Error" + response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AutoCompleteResponse> call, Throwable t) {
                hideprogress();
                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();


            }
        });


    }

    private void getPlaceDetails(String placesId) {
        // final PlaceDetailsResponse.Result[] placeDetailsResponse = new PlaceDetailsResponse.Result[1];

        String key = AppConstants.Googlekey2;
        GoogleClient.googleEndclient().getplaceDetails(placesId, key).enqueue(new Callback<PlaceDetailsResponse>() {

            @Override
            public void onResponse(Call<PlaceDetailsResponse> call, Response<PlaceDetailsResponse> response) {

                //placeDetailsResponse=response.body();

                // placeDetailsResponse[0] =response.body().getResult();


                if (response.isSuccessful()) {

                    PlaceDetailsResponse.Result placedeatils = response.body().getResult();
                    Intent intent = new Intent();
                    if(pickLocationScr){
                    intent.putExtra("SearchPlace", (Serializable) placedeatils);
                    setResult(PickLocationFragment.PICK_RESULTSOK, intent);
                    // pickLocationScr=false;
                    finish();
                    }
                    else {

                        intent.putExtra("SearchPlace", (Serializable) placedeatils);
                        setResult(DropLocationFragment.DROP_RESULTSOK, intent);
                        finish();


                    }


                }
                if (response.errorBody() != null) {
                    Toast.makeText(getApplicationContext(), "Error" + response.errorBody().toString(), Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<PlaceDetailsResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();

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
                    PlaceSearchActivity.this.runOnUiThread(new TimerTask() {
                        @Override
                        public void run() {
                            if (text.length() >= 5) {
                                //getPlacesByTextSearch(searchedittext.getText().toString().trim() + " in karachi");
                                getAutocompleteplaces(databindinig.searchaddress.getText().toString()+"+karachi");




                            }
                        }
                    });



                }
            },1000);






        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode==200||resultCode==200){
//            //do something
//        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPlaceSearceInteractionListner(String place) {
        getAutocompleteplaces(place);
    }
}
