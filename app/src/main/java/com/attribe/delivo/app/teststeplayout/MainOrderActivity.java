package com.attribe.delivo.app.teststeplayout;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.attribe.delivo.app.R;
import com.attribe.delivo.app.fragments.DropLocationFragment;
import com.attribe.delivo.app.fragments.PickLocationFragment;

public class MainOrderActivity extends AppCompatActivity
        implements  PickLocationFragment.OnFragmentInteractionListener,DropLocationFragment.OnFragmentInteractionListener {
    private Button step_one_btn, step_two_btn, step_three_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_order_activity);
       // initViews(savedInstanceState);



    }




    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
