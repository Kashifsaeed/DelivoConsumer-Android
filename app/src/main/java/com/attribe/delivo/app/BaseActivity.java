package com.attribe.delivo.app;

import com.attribe.delivo.app.adapters.DrawerListAdapter;

import android.support.annotation.LayoutRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.FrameLayout;
import android.widget.ListView;

public class BaseActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    protected ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    protected Toolbar toolbar;
    DrawerListAdapter drawerListAdapter;
    protected FrameLayout frameLayout;
    protected static int position;
    private static boolean isLaunch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        init();
    }

    private void init()
    {
        frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        toolbar= (Toolbar) findViewById(R.id.my_toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        drawerListAdapter=new DrawerListAdapter(getApplicationContext());
        createDrawer();



    }

    private void createDrawer()
    {
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null)
        {

            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.hello_world, R.string.hello_world)
        {
            public void onDrawerClosed(View view)
            {
                //getActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView)
            {
              //  getActionBar().setTitle("Menu");
                invalidateOptionsMenu();
                drawerList.setAdapter(drawerListAdapter);
            }
        };
            //drawerToggle.setDrawerIndicatorEnabled(true);
            drawerLayout.setDrawerListener(drawerToggle);
          //  drawerToggle.syncState();
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setHomeButtonEnabled(true);
    }
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pick_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      //for enable clicking the nav icon
        if (item != null && item.getItemId() == R.id.menu_btn) {
            if (drawerLayout.isDrawerOpen(Gravity.RIGHT))
            {
                drawerLayout.closeDrawer(Gravity.RIGHT);
            } else
            {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        }
        return false;
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        createDrawer();
    }
}
