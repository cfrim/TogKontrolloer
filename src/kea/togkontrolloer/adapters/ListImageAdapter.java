package kea.togkontrolloer.adapters;
 
import java.util.ArrayList;

import kea.togkontrolloer.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.TextView;
 
public class ListImageAdapter extends BaseAdapter{
 
    private Activity activity;
    private ArrayList<CustomListViewItem> data;
    private static LayoutInflater inflater=null;
 
    public ListImageAdapter(Activity a, ArrayList<CustomListViewItem> d) {
        this.activity = a;
        this.data=d;
        this.inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);
 
        TextView title = (TextView)vi.findViewById(R.id.title); // title
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image);
        CustomListViewItem item;
        item = data.get(position);
 
        // Setting all values in listview
        //title.setText(item.title);
        title.setText(item.title);
        thumb_image.setImageResource(item.image);
        //imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        return vi;
    }
}