package com.attribe.delivo.app.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
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
import com.attribe.delivo.app.databinding.DropDetailFragmentBinding;
import com.attribe.delivo.app.databinding.DropDetailsFragmentBinding;
import com.attribe.delivo.app.dialouge.AlertDialouge;
import com.attribe.delivo.app.eventbus.PickEvent;
import com.attribe.delivo.app.utils.SnackBars;
import com.attribe.delivo.app.utils.TimeUtility;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DropDetailFragment.OnDropDetailFragmentInteractionListner} interface
 * to handle interaction events.
 */
public class DropDetailFragment extends Fragment {
    DropDetailsFragmentBinding vBinding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private TextView reciver_name, reciever_contact, drop_address, drop_desc;
    private EditText drop_time, drop_date;
    private Button dropdetailbtn;
    private TimePickerDialog mTimePicker;
    private int pickHour, pickMin,pickTime;
    private OnDropDetailFragmentInteractionListner mListener;

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
        EventBus.getDefault().register(DropDetailFragment.this);
        getViewsReference();
        dropdetailbtn.setOnClickListener(new onDropDetailListner());
    }

    private void getViewsReference() {
        reciver_name = vBinding.recivePersonName;
        reciever_contact = vBinding.receivePersonContact;
        drop_address = vBinding.dropDetailAddress;
        drop_desc = vBinding.dropDescription;
        drop_time = vBinding.dropTime;
        drop_date = vBinding.dropDate;
        dropdetailbtn = vBinding.addDropdetailsBtn;
        drop_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDropTime();
            }
        });
        drop_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDropDate();
            }
        });

    }
//=============================================Helper Methods =======================================================//

    /**
     * this method will validate the input fields
     *
     * @return
     */
    private boolean isValidate() {
        if (reciver_name.getText().toString().trim().equals("")) {
            reciver_name.setError("missing field");
            return false;

        } else if (reciever_contact.getText().toString().trim().equals("")) {
            reciever_contact.setError("missing field");
            return false;

        } else if (drop_address.getText().toString().equals("")) {
            drop_address.setError("missing field");
            return false;

        } else if (drop_time.getText().toString().trim().equals("")) {
            drop_time.setError("missing field");
            return false;

        } else if (drop_date.getText().toString().trim().equals("")) {
            drop_date.setError("missing field");
            return false;

        } else if (drop_desc.getText().toString().trim().equals("")) {
            drop_desc.setError("missing field");
            return false;

        }
        return true;
    }


    private void setDropTime() {
        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selecthour, int selectminute) {
                String AM_PM = (selecthour < 12) ? "AM" : "PM";


                if (selecthour < pickHour || (selecthour == pickHour && selectminute < pickMin)) {
                    //SnackBars.showMessage(vBinding.getRoot(),"Drop Time must be 45min after");
                    AlertDialouge.showDialoge(getContext(), "Drop Time must be 45min greater then pick");
                } else {
                    String select_time = timePicker.getHour() + "" + ":" + timePicker.getMinute();
                    // pick_time_edittxt.setText("" + select_time);
                    drop_time.setText(select_time+"\u00A0"+AM_PM);
                }


            }
        }
                , hour, minute, false);
        mTimePicker.setTitle("Select Time");
        mTimePicker.updateTime(pickHour, pickMin);//thi will show default time in picker
        mTimePicker.show();

    }

    private void setDropDate() {
        Calendar mcurrentTime = Calendar.getInstance();
        int year = mcurrentTime.get(Calendar.YEAR);
        int month = mcurrentTime.get(Calendar.MONTH);
        int date = mcurrentTime.get(Calendar.DATE);
        DatePickerDialog mDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String date = String.valueOf(datePicker.getYear() + "/" + datePicker.getMonth() + "/" + datePicker.getDayOfMonth());
                //pick_date_edittxt.setText(date);
                drop_date.setText(date);

            }
        }, year, month, date);
        mDatePickerDialog.setTitle("Select Date");
        mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        mDatePickerDialog.show();

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPickEvent(PickEvent pickEvent) {
        if (pickEvent != null) {
            Date time = TimeUtility.addTime(pickEvent.getPick_time());
            Calendar cal = Calendar.getInstance();
            cal.setTime(time);
            pickHour = cal.get(Calendar.HOUR_OF_DAY);
            pickMin = cal.get(Calendar.MINUTE);
            String AM_PM = (pickHour < 12) ? "AM" : "PM";
            drop_time.setText(pickHour + "" + ":" + pickMin + AM_PM);


            if (pickEvent.isPicknow()) {

                drop_date.setText("" + pickEvent.getPick_date());
                drop_time.setEnabled(false);
                drop_date.setEnabled(false);
                drop_time.setAlpha(0.4f);
                drop_date.setAlpha(0.4f);


            }

        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     */

    public interface OnDropDetailFragmentInteractionListner {
        void dropDetailFragmentInteraction(String pp_name, String pp_contactno, String detail_address, String pick_time, String pick_date, String pick_desc);
    }

    private class onDropDetailListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (isValidate()) {
                mListener.dropDetailFragmentInteraction(reciver_name.getText().toString(), reciever_contact.getText().toString(), drop_address.getText().toString(), drop_time.getText().toString(), drop_date.getText().toString(), drop_desc.getText().toString());
            }

        }
    }
}
