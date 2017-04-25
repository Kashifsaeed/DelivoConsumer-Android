package screens;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.attribe.delivo.app.R;
import com.attribe.delivo.app.databinding.SelectOptionActivityBinding;

public class SelectOptionScreen extends AppCompatActivity implements View.OnClickListener {
    //SelectOptionScreenActivityBinding
 private  SelectOptionActivityBinding parent_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //
        //
      parent_view=  DataBindingUtil.setContentView(SelectOptionScreen.this,R.layout.select_option_activity);
        setEventListners();
    }

    private void setEventListners() {
        parent_view.moneyLayout.setOnClickListener(this);
        parent_view.foodItemLayout.setOnClickListener(this);
        parent_view.billLayout.setOnClickListener(this);
        parent_view.otherLayout.setOnClickListener(this);
        parent_view.parcelLayout.setOnClickListener(this);
        parent_view.movieTicketLayout.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
       int view_id= view.getId();
        if(view_id == R.id.food_item_layout|| view_id==R.id.parcel_layout||
                view_id==R.id.bill_layout|| view_id==R.id.money_layout||
                view_id==R.id.movie_ticket_layout|| view_id==R.id.other_layout)
        {
            startActivity(new Intent(SelectOptionScreen.this,CustomPickLocation.class));
        }

    }
}
