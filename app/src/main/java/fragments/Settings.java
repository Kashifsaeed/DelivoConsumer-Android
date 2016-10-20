package fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.attribe.delivo.app.R;

/**
 * Created by Maaz on 7/22/2016.
 */
public class Settings extends Fragment {

    private View view;
    private EditText profile_fieldName,profile_fieldFullName,profile_fieldEmail,profile_fieldNumber;
    private Button buttonDone, buttonCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view=inflater.inflate(R.layout.fragment_settings, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setHasOptionsMenu(true);

        init(view);
        return view;

    }

    private void init(View view) {

        profile_fieldName = (EditText)view.findViewById(R.id.profile_name);
        profile_fieldFullName = (EditText) view.findViewById(R.id.profile_fullname);
        profile_fieldEmail = (EditText) view.findViewById(R.id.profile_email);
        profile_fieldNumber = (EditText)view.findViewById(R.id.profile_number);
        buttonDone = (Button)view.findViewById(R.id.profile_done);

        buttonDone.setOnClickListener(new DoneProfileListener());
    }

    private class DoneProfileListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }
}
