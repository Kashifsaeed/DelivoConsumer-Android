package screens;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import com.attribe.delivo.app.R;
import com.squareup.picasso.Picasso;


public class ScreenRiderDetail extends FragmentActivity {


    private ImageView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_rider_detail);

        init();

    }

    private void init() {
        imageView = (ImageView)findViewById(R.id.rider_photo);

        Picasso.with(this).load("https://randomuser.me/api/portraits/men/77.jpg").into(imageView);
    }


}
