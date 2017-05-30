package com.attribe.delivo.app.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.attribe.delivo.app.R;
import com.attribe.delivo.app.databinding.PickDetailsFragmentBinding;
import com.attribe.delivo.app.interfaces.OnNextPageNavigation;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class PickDetailsFragment extends Fragment{
    PickDetailsFragmentBinding vBinding;
   // Calendar dateSelected = Calendar.getInstance();
    private EditText pickpersonname_edittxt,
            pick_contact_edittxt, pick_detailaddress_edittxt, pick_desc_edittxt;
    private TextView pick_date_edittxt, pick_time_edittxt;
    private Button add_pickdetails_btn;
    private OnPickDetailFragmentInteractionListner mListener;
    private OnDetialToolbar mToolbarListner;
    private OnNextPageNavigation onNextPageNavigation;
   // private DatePickerDialog datePickerDialog;


    public PickDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        vBinding = DataBindingUtil.inflate(inflater, R.layout.pick_details_fragment, container, false);
        initViews();
        return vBinding.getRoot();
    }

    //=============================================Initialize Views =======================================================//
    private void initViews() {
        getReference();
        //((OrderContainerActivity) getActivity()).mToolbar.setTitle("" + "Pick Details");


        add_pickdetails_btn.setOnClickListener(new onAddPickDetailsListner());
        pick_time_edittxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPickTime();
            }
        });
        pick_date_edittxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate();
            }
        });

    }

    private void getReference() {
        pickpersonname_edittxt = vBinding.pickPersonName;
        pick_contact_edittxt = vBinding.pickPersonContact;
        pick_desc_edittxt = vBinding.pickDescription;
        pick_detailaddress_edittxt = vBinding.pickDetailAddress;
        pick_time_edittxt = vBinding.pickTime;
        pick_date_edittxt = vBinding.pickDate;
        add_pickdetails_btn = vBinding.addPickdetailsBtn;
//


    }
    //=============================================Helper Methods =======================================================//

    /**
     * this method will validate the input fields
     *
     * @return
     */
    private boolean isValidate() {
        if (pickpersonname_edittxt.getText().toString().trim().equals("")) {
            pickpersonname_edittxt.setError("missing field");
            return false;

        } else if (pick_contact_edittxt.getText().toString().trim().equals("")) {
            pick_contact_edittxt.setError("missing field");
            return false;

        } else if (pick_detailaddress_edittxt.getText().toString().equals("")) {
            pick_detailaddress_edittxt.setError("missing field");
            return false;

        } else if
                (pick_time_edittxt.getText().toString().trim().equals("")) {
            pick_time_edittxt.setError("missing field");
            return false;

        } else if
                (pick_date_edittxt.getText().toString().trim().equals("")) {
            pick_date_edittxt.setError("missing field");
            return false;
        } else if (pick_desc_edittxt.getText().toString().trim().equals("")) {
            pick_desc_edittxt.setError("missing field");
            return false;

        }


        return true;
    }

    //
    private void setPickTime() {
        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {

                String select_time = timePicker.getHour() + "" + ":" + timePicker.getMinute();
                pick_time_edittxt.setText("" + select_time);


            }
        }
                , hour, minute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }
    private void setDate(){
        Calendar mcurrentTime = Calendar.getInstance();
        int year = mcurrentTime.get(Calendar.YEAR);
        int month = mcurrentTime.get(Calendar.MONTH);
        int date=mcurrentTime.get(Calendar.DATE);
       DatePickerDialog mDatePickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String select_date = datePicker.getYear() + "/"  + datePicker.getMonth()+"/"+datePicker.getDayOfMonth();
                pick_date_edittxt.setText("" + select_date);


            }
        },year,month,date);
        mDatePickerDialog.setTitle("Select Date");
        mDatePickerDialog.show();

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnPickDetailFragmentInteractionListner) context;
            onNextPageNavigation = (OnNextPageNavigation) context;

        } catch (ClassCastException e) {
            throw new RuntimeException(e.toString()
                    + " must implement OnPickDetailFragmentInteractionListner");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        // mToolbarListner.setTittle(""+"Pick Details");
        //  getActivity().getActionBar().setTitle("" + "Pick Details");


    }


    @Override
    public void onStart() {
        super.onStart();
    }


//    @Override
//    public void onClick(View view) {
//        int itemId = view.getId();
//
//        switch (itemId) {
//
//            case R.id.pick_time:
//                 setPickTime();
//                break;
//            case R.id.pick_date:
//                //setPickTime();
//                break;
//            default:
//                break;
//        }
//    }

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
    public interface OnPickDetailFragmentInteractionListner {
        void pickDetailFragmentInteraction(String pp_name, String pp_contactno, String detail_address, String pick_time, String pick_date, String pick_desc);
    }

    public interface OnDetialToolbar {
        void setTittle(String tittle);
    }

    private class onAddPickDetailsListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //  OrderCreate.getInstance().getTasks().add(new Task());
            if (isValidate()) {
                mListener.pickDetailFragmentInteraction(pickpersonname_edittxt.getText().toString(),
                        pick_contact_edittxt.getText().toString(),
                        pick_detailaddress_edittxt.getText().toString(),
                        pick_time_edittxt.getText().toString(), pick_date_edittxt.getText().toString(),
                        pick_desc_edittxt.getText().toString());

                       onNextPageNavigation.onPageChange(2);

            }

        }
    }


}
