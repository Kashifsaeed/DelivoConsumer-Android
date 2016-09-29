package adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.attribe.delivo.app.R;

/**
 * Created by Maaz on 7/22/2016.
 */
public class NavDrawerAdapter extends BaseAdapter {

    private Context context;
    private String[] drawerItemList={"Home","My Orders","Profile Settings","SignOut"};
    private int[] delivoImages={android.R.drawable.star_on,android.R.drawable.star_on,android.R.drawable.star_on,android.R.drawable.star_on,android.R.drawable.star_on,android.R.drawable.star_on};
    ;


    public NavDrawerAdapter(Context context, int[] delivoImages, String[] drawerItemList) {
        this.context = context;
        this.delivoImages = delivoImages;
        this.drawerItemList = drawerItemList;
    }


    @Override
    public int getCount() {

        return drawerItemList.length;
    }

    @Override
    public Object getItem(int position) {

        return drawerItemList.length;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.nav_drawer_list_item, null);

            viewHolder = new ViewHolder();
            viewHolder.iv = (ImageView)convertView.findViewById(R.id.rowIcon);
            viewHolder.tv = (TextView)convertView.findViewById(R.id.rowText);

            convertView.setTag(viewHolder);
        }

        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.iv.setBackgroundResource(delivoImages[position]);
        viewHolder.tv.setText(drawerItemList[position]);

        return convertView;

    }

    public Typeface setCustomFont(String fontName) {
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/"+fontName);

        return custom_font;
    }

    private static class ViewHolder{


        TextView tv;
        ImageView iv;

    }

}
