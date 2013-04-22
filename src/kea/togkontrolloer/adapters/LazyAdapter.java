package kea.togkontrolloer.adapters;
 
import java.util.ArrayList;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;
 
public class LazyAdapter extends BaseAdapter{
 
    private Activity activity;
    private ArrayList<CustomListView> data;
    private static LayoutInflater inflater=null;
 
    public LazyAdapter(Activity a, ArrayList<CustomListView> d) {
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
            vi = inflater.inflate(R.layout.test_list_item, null);
 
        TextView title = (TextView)vi.findViewById(R.id.title); // title
        //TextView artist = (TextView)vi.findViewById(R.id.artist); // artist name
        //TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
        //ImageView thumb_image=(ImageView)vi.findViewById(R.id.); // thumb image
 
        CustomListView item = new CustomListView();
        item = data.get(position);
 
        // Setting all values in listview
        title.setText(item.title);
        //imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        return vi;
    }
}