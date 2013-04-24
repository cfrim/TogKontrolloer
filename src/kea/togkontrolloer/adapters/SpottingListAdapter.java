package kea.togkontrolloer.adapters;
 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import kea.togkontrolloer.R;
import kea.togkontrolloer.models.Spotting;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class SpottingListAdapter extends BaseAdapter{
 
    private Activity activity;
    private ArrayList<Spotting> data;
    private static LayoutInflater inflater=null;
 
    public SpottingListAdapter(Activity a, ArrayList<Spotting> d) {
        this.activity = a;
        this.data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return data.get(position);
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null) vi = inflater.inflate(R.layout.list_row_spotting, null);
        
        TextView title = (TextView)vi.findViewById(R.id.title); // title
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image);
        TextView stationAndTime = (TextView) vi.findViewById(R.id.artist);
        TextView spotCount = (TextView) vi.findViewById(R.id.spotalert);
        
        Spotting spotting;
        spotting = data.get(position);
        
        title.setText(spotting.getStationName1());
        thumb_image.setImageResource(R.drawable.s_tog);
        
        String subline = "";
        if(spotting.getStationName2() != null && spotting.getStationName2() != spotting.getStationName1()){
        	subline += spotting.getStationName2()+" - ";
        }
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    	
    	String timeOnly = "";
    	Date created_at;
    	try {
			created_at = df.parse(spotting.getCreated_at());
			timeOnly = sdf.format(created_at);
		} catch (ParseException e) {
			Log.e("SpottingListAdapter", e.toString());
		}
    	if(timeOnly != "") subline += timeOnly;
    	
    	stationAndTime.setText(subline);
    	
    	spotCount.setText(String.valueOf(spotting.getCount()));
        
        //imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        return vi;
    }
}