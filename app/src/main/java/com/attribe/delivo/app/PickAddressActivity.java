package com.attribe.delivo.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import utils.HeightAnimation;

public class PickAddressActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Button confirm_pick;
    EditText picklocation,pickdes_address;
    private View hiddenPanel;
    private MapView mapview;
    private GoogleMap mMap;
    LinearLayout mLayoutTab;
    LinearLayout expandmainlayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_address);
        initMap(savedInstanceState);
        initViews();
    }

    private void initViews() {
        mLayoutTab = (LinearLayout)findViewById(R.id.opitonaladdresslayout);
        expandmainlayout = (LinearLayout)findViewById(R.id.expandlayout);

        confirm_pick= (Button) findViewById(R.id.confirmPick);
        picklocation= (EditText) findViewById(R.id.pickmyaddress);
        hiddenPanel=findViewById(R.id.opitonaladdresslayout);
        confirm_pick.setOnClickListener(new ConfirmPickListner());
        expandOrCollapse(hiddenPanel,"collaspe");


    }
    private void initMap(Bundle savedInstance){

        mapview= (MapView) findViewById(R.id.mymapview);
        mapview.onCreate(savedInstance);
        mapview.getMapAsync(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapview.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapview.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapview.onLowMemory();
    }
    private void downUpLayout(){
        boolean isClicked=false;


        if(isClicked){
            isClicked = true;
            mLayoutTab.animate()
                    .translationYBy(120)
                    .translationY(0)
                    .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));

        }else{
            isClicked = false;
            mLayoutTab.animate()
                    .translationYBy(0)
                    .translationY(120)
                    .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
        }





    }
    public static void animateViewFromBottomToTop(final View view){

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
               // final int expandlayoutheigt=expandmainlayout.getHeight();

                final int TRANSLATION_Y = view.getHeight();
                view.setTranslationY(TRANSLATION_Y);
                view.setVisibility(View.GONE);
                view.animate()
                        .translationYBy(-TRANSLATION_Y)
                        .setDuration(500)
                        .setStartDelay(200)
                        .setListener(new AnimatorListenerAdapter() {

                            @Override
                            public void onAnimationStart(final Animator animation) {

                                view.setVisibility(View.VISIBLE);
                            }
                        })
                        .start();
            }
        });
    }
    public void expandOrCollapse(final View v,String exp_or_colpse) {
       // TranslateAnimation anim = null;
        ScaleAnimation anim=null;
        if(exp_or_colpse.equals("expand"))
        {
            //anim = new TranslateAnimation(0.0f, 0.0f, -v.getHeight(), 0.0f);
             anim = new ScaleAnimation(1, 1, 0, 1);

            v.setVisibility(View.VISIBLE);
        }
        else{
            //anim = new TranslateAnimation(0.0f, 0.0f, 0.0f, -v.getHeight());
            anim = new ScaleAnimation(1, 1, 1, 0);
            Animation.AnimationListener collapselistener= new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    v.setVisibility(View.GONE);
                }
            };

            anim.setAnimationListener(collapselistener);
        }

        // To Collapse
        //

        anim.setDuration(300);
        anim.setInterpolator(new AccelerateInterpolator(0.5f));
        v.startAnimation(anim);
    }

    private boolean isPanelShown() {

        return hiddenPanel.getVisibility() == View.VISIBLE;
    }
    public void slideUpDown() {
        if (!isPanelShown()) {
            // Show the panel
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
        }
        else {
            // Hide the Panel
            Animation bottomDown = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.GONE);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
    }


    private class ConfirmPickListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {
           // slideUpDown();
           // expandOrCollapse(hiddenPanel,"expand");
            mLayoutTab.setVisibility(View.VISIBLE);
            //animateViewFromBottomToTop(mLayoutTab);
            HeightAnimation heightAnim = new HeightAnimation(mLayoutTab,expandmainlayout.getHeight()+300,true);
            heightAnim.setDuration(1000);

            view.startAnimation(heightAnim);

            //downUpLayout();



        }
    }
}
