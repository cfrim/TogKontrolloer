package kea.togkontrolloer.adapters;

import java.util.ArrayList;
import kea.togkontrolloer.R;
import kea.togkontrolloer.models.Station;
import kea.togkontrolloer.models.TrainLine;
import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

//The custom spinner adapters are created in order to be able to pass objects into the spinners instead of strings
//It is mainly taken from http://stackoverflow.com/questions/6562236/android-spinner-databind-using-array-list
public class TrainLineSpinnerAdapter extends BaseAdapter{

 /**
  * The internal data (the ArrayList with the Objects).
  */
 Activity activity;
 ArrayList<TrainLine> data = new ArrayList<TrainLine>();
 
 
 public TrainLineSpinnerAdapter(Activity activity, ArrayList<TrainLine> data){
     this.activity = activity;
     this.data.add(new TrainLine(0, "Ikke i tog", "", "", new ArrayList<Station>()));
     this.data.addAll(data);
 }


 /**
  * Returns the Size of the ArrayList
  */
 @Override
 public int getCount() {
     return data.size();
 }

 /**
  * Returns one Element of the ArrayList
  * at the specified position.
  */
 @Override
 public Object getItem(int position) {
     return data.get(position);
 }

 @Override
 public long getItemId(int position) {
     return position;
 }

 @Override
 public int getItemViewType(int position) {
 	return R.layout.list_row;
 }
 

 /**
  * Returns the View that is shown when a element was
  * selected.
  */
 @Override
 public View getView(int position, View convertView, ViewGroup parent) {
 	TextView v = new TextView(activity.getApplicationContext());
     v.setTextColor(Color.BLACK);
     if(data.get(position).getId() != 0){
    	v.setText(data.get(position).getName()+" - "+data.get(position).getDestination()); 
     }
     else{
    	 v.setText(data.get(position).getName());  
     }
     
     v.setPadding(10, 10, 10, 10);
     return v;
 }

	@Override
 public int getViewTypeCount() {
     return 1;
 }

 @Override
 public boolean hasStableIds() {
     return false;
 }

 @Override
 public boolean isEmpty() {
     return false;
 }

 @Override
 public void registerDataSetObserver(DataSetObserver observer) {
     // TODO Auto-generated method stub

 }

 @Override
 public void unregisterDataSetObserver(DataSetObserver observer) {
     // TODO Auto-generated method stub

 }

	@Override
	public View getDropDownView(int position, View convertView,
			ViewGroup parent) {
		  
	    
		return getView(position, convertView, parent);
	}



 /**
  * The Views which are shown in when the arrow is clicked
  * (In this case, I used the same as for the "getView"-method.
  */


}
