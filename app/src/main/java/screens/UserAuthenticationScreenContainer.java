package screens;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.attribe.delivo.app.R;
import fragments.UserAuthentication;

/**
 * Created by Maaz on 7/28/2016.
 */
public class UserAuthenticationScreenContainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_auth_screen_container);

        UserAuthentication userAuthentication = new UserAuthentication();
        FragmentManager mfragmentManager = getFragmentManager();
        mfragmentManager.beginTransaction().replace(R.id.auth_container, userAuthentication).commit();

    }
}
