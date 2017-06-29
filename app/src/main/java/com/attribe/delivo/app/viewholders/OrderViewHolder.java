package com.attribe.delivo.app.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.attribe.delivo.app.R;

/**
 * Author: Uzair Qureshi
 * Date:  6/22/17.
 * Description:
 */

public class OrderViewHolder extends RecyclerView.ViewHolder {
    public TextView pick_address,drop_address;
    public OrderViewHolder(View itemView) {
        super(itemView);
        pick_address= (TextView) itemView.findViewById(R.id.pick_address);
        drop_address= (TextView) itemView.findViewById(R.id.drop_address);

    }
}
