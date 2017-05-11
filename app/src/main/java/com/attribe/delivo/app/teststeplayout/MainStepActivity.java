package com.attribe.delivo.app.teststeplayout;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.attribe.delivo.app.R;
import com.attribe.delivo.app.databinding.MainStepActivityBinding;

import com.attribe.delivo.app.adapters.PagerAdapter;
import com.attribe.delivo.app.fragments.StepFour;
import com.attribe.delivo.app.fragments.PickLocationFragment;
import com.attribe.delivo.app.fragments.DropLocationFragment;
import com.attribe.delivo.app.fragments.PickDetailFragment;

public class MainStepActivity extends AppCompatActivity implements

        PickDetailFragment.OnPickDetailFragmentInteractionListner,
        DropLocationFragment.OnDropLocationFragmentInteractionListener,
        StepFour.OnFragmentInteractionListener,View.OnClickListener,
        PickLocationFragment.OnPickLocationFragmentInteractionListner
    {

    public MainStepActivityBinding databinding;
    public ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databinding = DataBindingUtil.setContentView(MainStepActivity.this, R.layout.main_step_activity);//set root view
        databinding.mainpager.setAdapter(new PagerAdapter(getSupportFragmentManager(), this));
        databinding.steplayout.steponeView.setOnClickListener(this);
        databinding.steplayout.steptwoView.setOnClickListener(this);
        databinding.steplayout.stepthreeView.setOnClickListener(this);
        databinding.steplayout.stepfourView.setOnClickListener(this);


    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        mViewPager = databinding.mainpager;

    }

        @Override
        public void onClick(View view) {
            int itemId=view.getId();
            switch (itemId)
            {
                case R.id.stepone_view:
                    databinding.mainpager.setCurrentItem(0,true);
                    break;

                case R.id.steptwo_view:
                    databinding.mainpager.setCurrentItem(1,true);
                    break;

                case R.id.stepthree_view:
                    databinding.mainpager.setCurrentItem(2,true);
                    break;
                case R.id.stepfour_view:
                    databinding.mainpager.setCurrentItem(3,true);
                    break;
                default:
                    break;




            }
        }

        @Override
        public void OnPickLocationFragmentInteraction(String picklocation)
        {

        }

        @Override
        public void pickDetailFragmentInteraction(String pp_name, String pp_contactno, String detail_address, String pick_time, String pick_date, String pick_desc) {

        }

        @Override
        public void onDropLocationFragmentInteraction(String droplocation_name) {

        }
    }
