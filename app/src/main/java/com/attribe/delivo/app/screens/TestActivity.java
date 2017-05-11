package com.attribe.delivo.app.screens;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.attribe.delivo.app.R;

import com.attribe.delivo.app.models.response.GoogleApi;
import com.attribe.delivo.app.network.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Button searchBtn = (Button) findViewById(R.id.Searchbtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RestClient.getGoogleApiInterface().getLocationSearches("24.965114,2067.067230","100","AIzaSyBJvlD3dqnz42r9obhEClc2dEJAdXt9IK8").enqueue(new Callback<GoogleApi>() {
                    @Override
                    public void onResponse(Call<GoogleApi> call, Response<GoogleApi> response) {

                        // GoogleSearchesAdapter googleSearchesAdapter = new GoogleSearchesAdapter(TestActivity.class,response.body().getResults().);
                        // RecyclerView recyclerView = recyclerView = (RecyclerView)findViewById(R.id.listSearches);
                        Toast.makeText(TestActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<GoogleApi> call, Throwable t) {

                    }
                });

            }
        });


    }

}
