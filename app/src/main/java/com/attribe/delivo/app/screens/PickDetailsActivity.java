package com.attribe.delivo.app.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.attribe.delivo.app.BaseActivity;
import com.attribe.delivo.app.R;

public class PickDetailsActivity extends BaseActivity {
   // protected FrameLayout frameLayout;
    private Button add_pickdetails_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_pick_details);
        getLayoutInflater().inflate(R.layout.activity_pick_details, frameLayout);
        add_pickdetails_btn= (Button) findViewById(R.id.add_pickdetails_btn);
        add_pickdetails_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PickDetailsActivity.this,AddDropLocation.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.overridePendingTransition(R.anim.transition_left_in, R.anim.transition_left_out);

    }
}
