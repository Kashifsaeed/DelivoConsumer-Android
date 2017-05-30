package com.attribe.delivo.app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.attribe.delivo.app.R;
import com.attribe.delivo.app.models.response.AutoCompleteResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maaz on 8/23/2016.
 */
public class GoogleSearchesAdapter extends RecyclerView.Adapter<GoogleSearchesAdapter.MyViewHolder>{

    Context mContext;
    //private List<GoogleApi> searchesList;
    //private List<GoogleAPiByText.Result> searchesList=new ArrayList<GoogleAPiByText.Result>();
    private List<AutoCompleteResponse.Prediction> searchesList=new ArrayList<AutoCompleteResponse.Prediction>();

    OnItemClickListner orderItemClickListner;
    public GoogleSearchesAdapter(Context mContext, List<AutoCompleteResponse.Prediction> searchesList){
   // public GoogleSearchesAdapter(Context mContext, List<GoogleAPiByText.Result> searchesList) {
        this.mContext = mContext;
        this.searchesList = searchesList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView placesView,formatedaddress;
        public LinearLayout rowItemlayout;

        public MyViewHolder(View view) {
            super(view);

//            orderItemCount = (TextView) view.findViewById(R.id.orderCount);
            rowItemlayout= (LinearLayout) view.findViewById(R.id.rowLayout);
            placesView = (TextView) view.findViewById(R.id.placesView);
            formatedaddress = (TextView) view.findViewById(R.id.formatedadress);

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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.searches_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


//        holder.placesView.setText(""+searchesList.get(position).getName());
//        holder.formatedaddress.setText(""+searchesList.get(position).getFormatted_address());
          holder.placesView.setText(""+searchesList.get(position).getDescription());
       // holder.formatedaddress.setText(""+searchesList.get(position).getFormatted_address());





    }

    @Override
    public int getItemCount() {
        return searchesList.size();
    }

}
