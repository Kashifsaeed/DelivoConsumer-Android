package com.attribe.delivo.app.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.attribe.delivo.app.R;
import com.attribe.delivo.app.databinding.PayDialougeBinding;
import com.attribe.delivo.app.databinding.PickDetailsFragmentBinding;
import com.attribe.delivo.app.databinding.PickFrafTestBinding;
import com.attribe.delivo.app.eventbus.PickEvent;
import com.attribe.delivo.app.interfaces.OnNextPageNavigation;
import com.attribe.delivo.app.utils.SnackBars;
import com.attribe.delivo.app.utils.TimeUtility;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class PickDetailsFragment extends Fragment {
    PickFrafTestBinding vBinding;
    // Calendar dateSelected = Calendar.getInstance();
    private EditText pickpersonname_edittxt,
            pick_contact_edittxt, pick_detailaddress_edittxt, pick_desc_edittxt;
    private TextView pick_date_edittxt, pick_time_edittxt;
    private Button add_pickdetails_btn;
    private CheckBox pay_pickup_check,picknow_check;
    private OnPickDetailFragmentInteractionListner mListener;
    private OnDetialToolbar mToolbarListner;
    private OnNextPageNavigation onNextPageNavigation;
    private int pickhour, pickmin;
    private boolean pay_at_pickup=false;
    private boolean picknow_flag=false;
    private float pickUpamount=0;
   private boolean isBefore=false;

    // private DatePickerDialog datePickerDialog;


    public PickDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        vBinding = DataBindingUtil.inflate(inflater, R.layout.pick_fraf_test, container, false);
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
        pay_pickup_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    PayPickUpDialoge dialoge=new PayPickUpDialoge(getContext());
                    pay_at_pickup=true;
                    dialoge.show();

                }
                else if (!compoundButton.isChecked()){
                    pay_at_pickup=false;

                }
            }
        });
        picknow_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    picknow_flag=true;
                    pick_time_edittxt.setText(""+TimeUtility.getLocalTime(System.currentTimeMillis()));
                    pick_date_edittxt.setText(""+TimeUtility.getLocalDate(System.currentTimeMillis()));
                    pick_time_edittxt.setEnabled(false);
                    pick_date_edittxt.setEnabled(false);
                    pick_date_edittxt.setAlpha(0.4f);
                    pick_time_edittxt.setAlpha(0.4f);
                }
                else if(!compoundButton.isChecked()){
                    pick_time_edittxt.setEnabled(true);
                    pick_date_edittxt.setEnabled(true);
                    pick_date_edittxt.setAlpha(1f);
                    pick_time_edittxt.setAlpha(1f);

                }


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
        pay_pickup_check=vBinding.payAtPickupcheck;
        picknow_check=vBinding.picknowCheck;
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

        final Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMin) {
//
                    String AM_PM = (selectedHour < 12) ? "AM" : "PM";
                    pickhour = timePicker.getHour();
                    pickmin = timePicker.getMinute();
                    String select_time = timePicker.getHour() + "" + ":" + timePicker.getMinute();
                    pick_time_edittxt.setText("" + select_time + "\u00A0" + AM_PM);

            }
        }
                , hour, minute, false);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();


    }

    private void setDate() {
        Calendar mcurrentTime = Calendar.getInstance();
        int year = mcurrentTime.get(Calendar.YEAR);
        int month = mcurrentTime.get(Calendar.MONTH);
        int date = mcurrentTime.get(Calendar.DATE);
        DatePickerDialog mDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String select_date = datePicker.getYear() + "/" + datePicker.getMonth() + "/" + datePicker.getDayOfMonth();
                pick_date_edittxt.setText("" + select_date);


            }
        }, year, month, date);
        mDatePickerDialog.setTitle("Select Date");
        mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
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
        void pickDetailFragmentInteraction(String pp_name, String pp_contactno, String detail_address, String pick_time, String pick_date, String pick_desc,boolean pay_at_pickup,float pickUpamount);
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
                        pick_desc_edittxt.getText().toString(),pay_at_pickup,pickUpamount);

                onNextPageNavigation.onPageChange(2);
                EventBus.getDefault().post(new PickEvent(pick_time_edittxt.getText().toString(),pick_date_edittxt.getText().toString(),picknow_flag));


            }

        }
    }

    class PayPickUpDialoge extends Dialog {
        private PayDialougeBinding bindView;

        public PayPickUpDialoge(Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setCancelable(false);
            setCanceledOnTouchOutside(false);
            bindView = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.pay_dialouge, null, false);
            setContentView(bindView.getRoot());
            bindView.pickConfirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!bindView.pickAmount.getText().toString().trim().equals("")) {
                        pickUpamount = Float.valueOf(bindView.pickAmount.getText().toString().trim());
                        // SnackBars.showMessage(vBinding.getRoot(), String.valueOf(pickUpamount));
                        dismiss();
                    }
                    else {
                        bindView.pickAmount.setError("Required");

                    }
                }
            });
            // initViews();
        }

    }
}
