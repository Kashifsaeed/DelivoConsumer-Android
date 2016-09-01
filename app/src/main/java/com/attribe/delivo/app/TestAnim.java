package com.attribe.delivo.app;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class TestAnim extends AppCompatActivity {
    private Toolbar mtoolbar;
    private AppBarLayout appbarlinear;
    private Button expand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_anim);
        appbarlinear= (AppBarLayout) findViewById(R.id.appbarlinear);
        expand= (Button) findViewById(R.id.expand);
        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appbarlinear.setExpanded(false);
            }
        });
        setUpToolbar();


    }

    private void setUpToolbar() {
        mtoolbar= (Toolbar) findViewById(R.id.toolbar);
        mtoolbar.setTitle("My Custom Bar");
    }
}
