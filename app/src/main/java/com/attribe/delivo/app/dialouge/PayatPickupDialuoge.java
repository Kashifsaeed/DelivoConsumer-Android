package com.attribe.delivo.app.dialouge;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.attribe.delivo.app.R;
import com.attribe.delivo.app.databinding.PayDialougeBinding;

/**
 * Author: Uzair Qureshi
 * Date:  7/6/17.
 * Description:
 */

public class PayatPickupDialuoge extends Dialog
{
    private PayDialougeBinding bindView;

    public PayatPickupDialuoge(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        bindView= DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.pay_dialouge, null, false);
        setContentView(bindView.getRoot());
       // initViews();
    }
}
