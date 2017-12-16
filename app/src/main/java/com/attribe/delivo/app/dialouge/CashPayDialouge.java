//package com.attribe.delivo.app.dialouge;
//
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.attribes.delvoagent.app.R;
//import com.attribes.delvoagent.app.bal.DeliveryOrderBal;
//import com.attribes.delvoagent.app.common.Requests_HashKeys;
//import com.attribes.delvoagent.app.constants.Constants;
//import com.attribes.delvoagent.app.constants.ShowMessaage;
//import com.attribes.delvoagent.app.interfaces.DialogeCallback;
//import com.attribes.delvoagent.app.interfaces.ResponseCallBack;
//import com.attribes.delvoagent.app.interfaces.onCashUpdate;
//import com.attribes.delvoagent.app.models.responsebody.DeliveryOrder;
//import com.attribes.delvoagent.app.models.responsebody.Tasks;
//import com.attribes.delvoagent.app.screens.OrdersDetails;
//
//
///**
// * Author: Uzair Qureshi
// * Date:  3/31/17.
// * Description:
// */
//
//public class CashPayDialouge extends Dialog {
//    private Button amount_paid_btn;
//    private EditText amount_paid_edittxt;
//    private TextView tobe_paid_amount;
//    private int order_id;
//    private float estimatedpay;
//    private OrdersDetails ordersDetails;
//    private ProgressDialog progressDialog;
//    private Tasks tasks;
//    private onCashUpdate onCashUpdatelistner;
//
//    public CashPayDialouge(OrdersDetails ordersDetails, int orderid, float estimated_pay)
//    {
//        super(ordersDetails);
//        this.order_id=orderid;
//        this.estimatedpay=estimated_pay;
//        this.ordersDetails = ordersDetails;
//    }
//    public CashPayDialouge(OrdersDetails ordersDetails, Tasks tasks, onCashUpdate onCashUpdate)
//    {
//        super(ordersDetails);
//        //this.order_id=orderid;
//        this.order_id=tasks.getOrder_id();
//        this.tasks=tasks;
//        this.ordersDetails = ordersDetails;
//        //onCashUpdate=onCashUpdatelistner;
//        onCashUpdatelistner=onCashUpdate;
//    }
//
//
//    protected CashPayDialouge(Context context, boolean cancelable, OnCancelListener cancelListener) {
//        super(context, cancelable, cancelListener);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setCancelable(false);
//        setCanceledOnTouchOutside(false);
//        setContentView(R.layout.pay_dialouge);
//        initViews();
//
//    }
//    //================================================== Helper Methods  ============================================================//
//
//    private void initViews()
//    {
//        amount_paid_btn= (Button) findViewById(R.id.paid_amount_btn);
//        amount_paid_edittxt= (EditText) findViewById(R.id.paid_amount_edttxt);
//        tobe_paid_amount= (TextView) findViewById(R.id.tobe_paid_amount);
//        tobe_paid_amount.setText(""+tasks.getPay_at_pickup_amount());
//        //amount_paid_edittxt.setText(0);
//        amount_paid_btn.setOnClickListener(new OnPayedCashListner());
//
//    }
//
//    /**
//     * this method will update to server that  the amount which is payed at pickup
//     * @param orderid
//     * @param paid_amount
//     */
//    private void updateOrderAmount(int orderid, final int paid_amount)
//    {
//
//             showProgress("loading..");
//             DeliveryOrderBal.updateOrderAmount(this.getContext(), orderid, Requests_HashKeys.getorderpayment_hashkey(Constants.CASH_AT_PICKUP, paid_amount), new ResponseCallBack<DeliveryOrder>()
//            {
//                @Override
//                public void onSuccess(DeliveryOrder response) {
//                    hideProgress();
//
//                  //  Toast.makeText(getContext(), response.getMeta().getMessage(), Toast.LENGTH_SHORT).show();
//                    //EventBus.getDefault().post(new UpdateCashEvent(response.getData().getOrder().getTotal()));
//                    ordersDetails.ordersdetails.setTotal(response.getData().getOrder().getTotal());
//                    dismiss();
//                    AlertDialouge.showConfirmation(getContext(), ShowMessaage.PAYMENT_SUCCESSFULL + "\u00A0" + paid_amount + "\u00A0" + "Rs.");
//                    onCashUpdatelistner.onUpdate();
//
//                }
//
//                @Override
//                public void onFailure(String message) {
//                    hideProgress();
//                    AlertDialouge.showError(getContext(), message);
//
//
//                }
//            });
//
//
//
//    }
//    private void showProgress(String message){
//        progressDialog= ProgressDialog.show(getContext(),"",message,false);
//    }
//
//    private void hideProgress(){progressDialog.dismiss();}
//
////============================================ Click Listners =================================================================//
//
//    private class OnPayedCashListner implements View.OnClickListener
//    {
//        @Override
//        public void onClick(View view)
//        {
//           if(!amount_paid_edittxt.getText().toString().trim().isEmpty())
//           {
//
//               AlertDialouge.showConfirmationLister(getContext(), ShowMessaage.PAYMENT_CONFIRMATION + "\u00A0" + Integer.parseInt(amount_paid_edittxt.getText().toString().trim()) + "\u00A0" + "Rs" + "\u00A0" + "?", new DialogeCallback() {
//                   @Override
//                   public void yesCall() //executes when press yes button
//                   {
//                       updateOrderAmount(order_id, Integer.parseInt(amount_paid_edittxt.getText().toString().trim()));//update order amount when rider press yes
//                   }
//
//                   @Override
//                   public void noCall()//executes when press no button
//                   {
//
//                   }
//               });
//
//
//
//           }
//            else {
//               Toast.makeText(getContext(),"Please enter paid amount", Toast.LENGTH_SHORT).show();
//           }
//
//        }
//    }
//}
