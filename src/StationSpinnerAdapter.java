import java.util.ArrayList;

import kea.togkontrolloer.models.Station;
import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;




public class StationSpinnerAdapter implements SpinnerAdapter{

    /**
     * The internal data (the ArrayList with the Objects).
     */
	Activity activity;
    ArrayList<Station> data;
    
    public StationSpinnerAdapter(Activity activity, ArrayList<Station> data){
        this.data = data;
        this.activity = activity;
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
    	return android.R.layout.simple_spinner_dropdown_item;
    }
    

    /**
     * Returns the View that is shown when a element was
     * selected.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	TextView v = new TextView(activity.getApplicationContext());
        v.setTextColor(Color.BLACK);
        v.setText(data.get(position).name);
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
		
		
		    LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    convertView = vi.inflate(android.R.layout.simple_spinner_dropdown_item, null);
		  
	    
		return getView(position, convertView, parent);
	}



    /**
     * The Views which are shown in when the arrow is clicked
     * (In this case, I used the same as for the "getView"-method.
     */
   

}