package com.attribe.delivo.app.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.attribe.delivo.app.R;
import com.attribe.delivo.app.databinding.DropDetailFragmentBinding;
import com.attribe.delivo.app.databinding.DropDetailsFragmentBinding;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DropDetailFragment.OnDropDetailFragmentInteractionListner} interface
 * to handle interaction events.

 */
public class DropDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private TextView reciver_name, reciever_contact, drop_address, drop_desc ;
    private EditText drop_time,drop_date;
    private Button dropdetailbtn;


    private OnDropDetailFragmentInteractionListner mListener;
    DropDetailsFragmentBinding vBinding;

    public DropDetailFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vBinding = DataBindingUtil.inflate(inflater, R.layout.drop_details_fragment, container, false);
         initViews();
        return vBinding.getRoot();
    }
    //=============================================Initialize Views =======================================================//

    private void initViews() {
        getViewsReference();
        dropdetailbtn.setOnClickListener(new onDropDetailListner());
    }

    private void getViewsReference() {
        reciver_name=vBinding.recivePersonName;
        reciever_contact=vBinding.receivePersonContact;
        drop_address=vBinding.dropDetailAddress;
        drop_desc=vBinding.dropDescription;
        drop_time=vBinding.dropTime;
        drop_date=vBinding.dropDate;
        dropdetailbtn=vBinding.addDropdetailsBtn;

    }
//=============================================Helper Methods =======================================================//

    /**
     * this method will validate the input fields
     * @return
     */
    private boolean isValidate()
    {
        if(reciver_name.getText().toString().trim().equals(""))
        {
            reciver_name.setError("missing field");
            return false;

        }
        else if(reciever_contact.getText().toString().trim().equals(""))
        {
            reciever_contact.setError("missing field");
            return false;

        }
        else if(drop_address.getText().toString().equals(""))
        {
            drop_address.setError("missing field");
            return false;

        }
        else if(drop_time.getText().toString().trim().equals(""))
        {
            drop_time.setError("missing field");
            return false;

        }
        else if(drop_date.getText().toString().trim().equals(""))
        {
            drop_date.setError("missing field");
            return false;

        }
        else if(drop_desc.getText().toString().trim().equals(""))
        {
            drop_desc.setError("missing field");
            return false;

        }
        return true;
    }








    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDropDetailFragmentInteractionListner) {
            mListener = (OnDropDetailFragmentInteractionListner) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     */

    public interface OnDropDetailFragmentInteractionListner
    {
        void dropDetailFragmentInteraction(String pp_name, String pp_contactno, String detail_address, String pick_time, String pick_date, String pick_desc);
    }

    private class onDropDetailListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(isValidate()){
                mListener.dropDetailFragmentInteraction(reciver_name.getText().toString(),reciever_contact.getText().toString(),drop_address.getText().toString(),drop_time.getText().toString(), drop_date.getText().toString(),drop_desc.getText().toString());
            }

        }
    }
}
