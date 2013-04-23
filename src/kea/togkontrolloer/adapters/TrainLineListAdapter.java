package kea.togkontrolloer.adapters;
 
import java.util.ArrayList;

import kea.togkontrolloer.R;
import kea.togkontrolloer.models.TrainLine;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.TextView;
 
public class TrainLineListAdapter extends BaseAdapter{
 
    private Activity activity;
    private ArrayList<TrainLine> data;
    private static LayoutInflater inflater=null;
 
    public TrainLineListAdapter(Activity a, ArrayList<TrainLine> d) {
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
        if(convertView==null) vi = inflater.inflate(R.layout.list_row, null);
 
        TextView title = (TextView)vi.findViewById(R.id.title); // title
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image);
        TrainLine trainLine;
        trainLine = data.get(position);
 
        // Setting all values in listview
        //title.setText(item.title);
        title.setText(trainLine.getDestination());
        title.setTextColor(Color.WHITE);
        
        
        if(trainLine.getIcon().equals("A.png")){
        	thumb_image.setImageResource(R.drawable.a);
        }
        else if(trainLine.getIcon().equals("B.png")){
        	thumb_image.setImageResource(R.drawable.b);
        }
        else if(trainLine.getIcon().equals("BX.png")){
        	thumb_image.setImageResource(R.drawable.bx);
        }
        else if(trainLine.getIcon().equals("C.png")){
        	thumb_image.setImageResource(R.drawable.c);
        }
        else if(trainLine.getIcon().equals("E.png")){
        	thumb_image.setImageResource(R.drawable.e);
        }
        else if(trainLine.getIcon().equals("F.png")){
        	thumb_image.setImageResource(R.drawable.f);
        }
        Log.i("Trainline icon outside", trainLine.getIcon());
        //imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        return vi;
    }
}