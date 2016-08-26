package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.attribe.delivo.app.R;
import models.response.GoogleApi;

import java.util.List;

/**
 * Created by Maaz on 8/23/2016.
 */
public class GoogleSearchesAdapter extends RecyclerView.Adapter<GoogleSearchesAdapter.MyViewHolder>{

    Context mContext;
    private List<GoogleApi> searchesList;
    OnItemClickListner orderItemClickListner;

    public GoogleSearchesAdapter(Context mContext, List<GoogleApi> searchesList) {
        this.mContext = mContext;
        this.searchesList = searchesList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView placesView;

        public MyViewHolder(View view) {
            super(view);

//            orderItemCount = (TextView) view.findViewById(R.id.orderCount);
            placesView = (TextView) view.findViewById(R.id.placesView);
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


        holder.placesView.setText(""+searchesList.get(position).getResults().get(position).getName());

    }

    @Override
    public int getItemCount() {
        return searchesList.size();
    }

}
