package utils;

import android.app.*;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import com.attribe.delivo.app.R;
import screens.ProgressView;

/**
 * Created by Sabih Ahmed on 15-Jul-16.
 */
public class TextProgressView extends DialogFragment{

    private final String TAG = TextProgressView.this.getClass().getSimpleName();
    private AlertDialog alertDialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.text_progress, null));

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return alertDialog;
    }

    @Override
    public void show(FragmentManager manager, String tag) {

        if(this.isAdded())
        {
            return; //or return false/true, based on where you are calling from
        }
        FragmentTransaction ft = manager.beginTransaction();
        try {
            ft.add(this, TAG);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        /*try catch is used to tackle the crash
         when user close the app and some thing
         running in background of app*/
        try {
            ft.commitAllowingStateLoss();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        }
        catch (IllegalStateException e)
        {
            super.dismissAllowingStateLoss();
        }
    }
}
