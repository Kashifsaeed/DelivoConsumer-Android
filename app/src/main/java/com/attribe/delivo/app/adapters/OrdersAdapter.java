package com.attribe.delivo.app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.attribe.delivo.app.R;
import com.attribe.delivo.app.models.response.MyOrders;
import com.attribe.delivo.app.viewholders.OrderViewHolder;

/**
 * Author: Uzair Qureshi
 * Date:  6/22/17.
 * Description:
 */

public class OrdersAdapter extends RecyclerView.Adapter<OrderViewHolder> {
    private MyOrders orders;
    private Context context;


    public OrdersAdapter(MyOrders orders, Context context) {
        this.orders = orders;
        this.context = context;
    }


    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_layout,parent,false);
        OrderViewHolder holder=new OrderViewHolder(itemView);


        return holder;
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        MyOrders.Datum datum=orders.getData().get(position);
        if(datum!=null){
            holder.pick_address.setText(""+datum.getTasks().get(0).getNearby());
            holder.drop_address.setText(""+datum.getTasks().get(1).getNearby());
        }



    }



    @Override
    public int getItemCount() {
        return orders.getData().size();
    }
}
