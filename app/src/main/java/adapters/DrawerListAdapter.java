package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.attribe.delivo.app.R;

/**
 * Created by Uzair on 9/28/16.
 */
public class DrawerListAdapter extends BaseAdapter {

    Context mcontext;


    String TITLES[] = {"Home","My Orders","Profile Settings","SignOut"};

    int ICONS[] = { android.R.drawable.star_on,
            android.R.drawable.star_on,
            android.R.drawable.star_on,
            android.R.drawable.star_on,
            R.drawable.signout,
           };

    public DrawerListAdapter(Context mcontext) {
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public String getItem(int i) {
        return TITLES[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public int getICONS(int i) {
        return ICONS[i];
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.nav_drawer_list_item, viewGroup, false);
        LinearLayout main_row_layout= (LinearLayout) view.findViewById(R.id.list_main_layout);
        TextView tittle= (TextView) view.findViewById(R.id.rowText);
        ImageView title_icon= (ImageView) view.findViewById(R.id.rowIcon);
        tittle.setText( getItem(position));
        title_icon.setBackgroundResource(getICONS(position));
        main_row_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mcontext,""+getItem(position).toString(),Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
