package com.attribe.delivo.app.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import com.attribe.delivo.app.PicknDropLocations;
import com.attribe.delivo.app.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Maaz on 8/11/2016.
 */
public class ScreenRiderDetail extends Fragment {

    private View view;
    private ImageView imageView;
    Button backToMain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_rider_detail, container, false);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setHasOptionsMenu(true);

        init(view);
        return view;

    }

    private void init(View view) {
        imageView = (ImageView)view.findViewById(R.id.rider_photo);
        backToMain = (Button) view.findViewById(R.id.backToMain);
        backToMain.setOnClickListener(new BackToMainListner());

        Picasso.with(getActivity()).load("https://randomuser.me/api/portraits/men/77.jpg").into(imageView);
    }


    private class BackToMainListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getActivity(), PicknDropLocations.class);
            startActivity(intent);
        }
    }
}


