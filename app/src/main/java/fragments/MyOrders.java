package fragments;

import adapters.MyOrdersAdapter;
import android.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.attribe.delivo.app.R;
import models.response.ConsumerOrders;
import network.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

import static android.R.attr.fragment;

/**
 * Created by Maaz on 7/22/2016.
 */
public class MyOrders extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private MyOrdersAdapter myordersAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view=inflater.inflate(R.layout.fragment_orders, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setHasOptionsMenu(true);
        initViews(view);
        getAllOrders();
        return view;

    }

    private void initViews(View view) {

        recyclerView = (RecyclerView)view.findViewById(R.id.listOrders);
    }

    private void getAllOrders() {

        RestClient.getAuthRestAdapter().getUserOrders(new Callback<ArrayList<ConsumerOrders>>() {
            @Override
            public void onResponse(Call<ArrayList<ConsumerOrders>> call, Response<ArrayList<ConsumerOrders>> response) {


                if(response.body() != null){
                    myordersAdapter = new MyOrdersAdapter(getActivity(),response.body());
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(myordersAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ConsumerOrders>> call, Throwable t) {

            }
        });
    }
}
