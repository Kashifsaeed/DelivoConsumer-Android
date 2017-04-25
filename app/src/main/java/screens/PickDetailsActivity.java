package screens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.attribe.delivo.app.BaseActivity;
import com.attribe.delivo.app.R;

public class PickDetailsActivity extends BaseActivity {
   // protected FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_pick_details);
        getLayoutInflater().inflate(R.layout.activity_pick_details, frameLayout);
    }
}
