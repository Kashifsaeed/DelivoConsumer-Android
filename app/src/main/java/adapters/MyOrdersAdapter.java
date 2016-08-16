package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.attribe.delivo.app.R;
import fragments.MyOrders;
import models.response.ConsumerOrders;

import java.util.List;

/**
 * Created by Maaz on 8/16/2016.
 */
public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyViewHolder>{

    Context mContext;
    private List<ConsumerOrders> ordersList;
    OnItemClickListner orderItemClickListner;

    public MyOrdersAdapter(Context mContext, List<ConsumerOrders> ordersList) {
        this.mContext = mContext;
        this.ordersList = ordersList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView orderItemCount,orderItemTitle;


        public MyViewHolder(View view) {
            super(view);

            orderItemCount = (TextView) view.findViewById(R.id.orderCount);
            orderItemTitle = (TextView) view.findViewById(R.id.orderTitle);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(orderItemClickListner != null){

                orderItemClickListner.onItemClick(v, getPosition());
            }
        }
    }

    public interface OnItemClickListner{

        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListner(final OnItemClickListner orderItemClickListner){

        this.orderItemClickListner = orderItemClickListner;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_row_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ConsumerOrders consumerOrders = ordersList.get(position);

        holder.orderItemCount.setText(""+ consumerOrders.getData().get(position));
        holder.orderItemTitle.setText(""+ consumerOrders.getData().get(position).getOrderid());

    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

}
