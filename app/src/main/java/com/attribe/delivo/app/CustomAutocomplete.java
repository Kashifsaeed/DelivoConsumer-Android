package com.attribe.delivo.app;

import Extras.MyConstants;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import models.response.AutoCompleteResponse;
import network.interfaces.GoogleClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomAutocomplete extends AppCompatActivity {
    private Button submit;
    private EditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_autocomplete);
        initViews();
    }

    private void initViews() {
        submit= (Button) findViewById(R.id.submitquery);
        input= (EditText) findViewById(R.id.inputquery);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              getAutocompleteplaces(input.getText().toString()+"+ karachi");
            }
        });
    }
    private void getAutocompleteplaces(String input){
        String key= MyConstants.Googlekey2;
        GoogleClient.googleEndclient().getplacepridiction(input,key).enqueue(new Callback<AutoCompleteResponse>() {
            @Override
            public void onResponse(Call<AutoCompleteResponse> call, Response<AutoCompleteResponse> response) {
                if(response.isSuccessful()){




                }
            }

            @Override
            public void onFailure(Call<AutoCompleteResponse> call, Throwable t) {

            }
        });


    }
}
