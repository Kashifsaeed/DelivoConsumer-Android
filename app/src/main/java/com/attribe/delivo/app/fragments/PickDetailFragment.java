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

import com.attribe.delivo.app.R;
import com.attribe.delivo.app.databinding.PickDetailFragmentBinding;
import com.attribe.delivo.app.models.response.OrderCreate;
import com.attribe.delivo.app.models.response.Task;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class PickDetailFragment extends Fragment {
    private EditText pickpersonname_edittxt,
            pick_contact_edittxt, pick_detailaddress_edittxt,
            pick_time_edittxt,
            pick_date_edittxt, pick_desc_edittxt;
    private Button add_pickdetails_btn;

    private OnPickDetailFragmentInteractionListner mListener;

     PickDetailFragmentBinding vBinding;


    public PickDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        vBinding = DataBindingUtil.inflate(inflater, R.layout.pick_detail_fragment, container, false);
        initViews();
        return vBinding.getRoot();
    }
//=============================================Initialize Views =======================================================//
    private void initViews() {
        getReference();
        add_pickdetails_btn.setOnClickListener(new onAddPickDetailsListner());

    }

    private void getReference()
    {
        pickpersonname_edittxt=vBinding.pickPersonName;
        pick_contact_edittxt=vBinding.pickPersonContact;
        pick_desc_edittxt =vBinding.pickDescription;
        pick_detailaddress_edittxt=vBinding.pickDetailAddress;
        pick_time_edittxt=vBinding.pickDate;
        add_pickdetails_btn=vBinding.addPickdetailsBtn;

    }
    //=============================================Helper Methods =======================================================//


    private boolean isValidate()
    {
        if(pickpersonname_edittxt.getText().toString().trim().equals(""))
        {
            pickpersonname_edittxt.setError("missing field");
            return false;

        }
        else if(pick_contact_edittxt.getText().toString().trim().equals(""))
        {
            pick_contact_edittxt.setError("missing field");
            return false;

        }
        else if(pick_detailaddress_edittxt.getText().toString().equals(""))
        {
            pick_detailaddress_edittxt.setError("missing field");
            return false;

        }
        else if(pick_time_edittxt.getText().toString().trim().equals(""))
        {
            pick_time_edittxt.setError("missing field");
            return false;

        }
        else if(pick_desc_edittxt.getText().toString().trim().equals(""))
        {
            pick_desc_edittxt.setError("missing field");
            return false;

        }





        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPickDetailFragmentInteractionListner) {
            mListener = (OnPickDetailFragmentInteractionListner) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPickDetailFragmentInteractionListner");
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
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnPickDetailFragmentInteractionListner
    {
        void pickDetailFragmentInteraction(String pp_name, String pp_contactno, String detail_address, String pick_time, String pick_date, String pick_desc);
    }
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }

    private class onAddPickDetailsListner implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
          //  OrderCreate.getInstance().getTasks().add(new Task());
           if(isValidate())
           {
               mListener.pickDetailFragmentInteraction(pickpersonname_edittxt.getText().toString(),
                       pick_contact_edittxt.getText().toString(),
                       pick_detailaddress_edittxt.getText().toString(),
                       pick_time_edittxt.getText().toString(),pick_time_edittxt.getText().toString(),
                       pick_desc_edittxt.getText().toString());

           }

        }
    }


}
