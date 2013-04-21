package kea.togkontrolloer.activities;

import java.util.ArrayList;

import kea.togkontrolloer.helpers.RequestMaker;
import kea.togkontrolloer.models.Station;
import kea.togkontrolloer.models.TrainLine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kea.togkontrolloer.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class MainSpotActivity extends Activity {
	

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Use custom styling on title bar 
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main_spot);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
        final TextView tv = (TextView) findViewById(R.id.dynamicTitleView); 
        tv.setText("SPOTTING");
        
        ImageButton favoriteBtn = (ImageButton) findViewById(R.id.favorits);
        
        Spinner trainLinesSpinner = (Spinner)findViewById(R.id.trainLinesSpinner);
	    ArrayList<TrainLine> trainLines = new ArrayList<TrainLine>();
	    
	    

	    
	   // ArrayAdapter<TrainLine> dataAdapter = new ArrayAdapter<TrainLine>(this, android.R.layout.simple_spinner_item, trainLines);
	   // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    String result; 
	    JSONObject jResult;
	    final JSONArray jOut;
	    
	    try{
        // TrainLineSpinner section
	    	
	    	// Get trainLines and stations
        	result = RequestMaker.makeRequest("http://cfrimodt.dk/test/ticket-dodger/?do=getLines&sec=314bf797090f40e9cbf54909b4814a4c1679cf4c2aae390559c15248a0055c12");
	    
	    	jResult = new JSONObject(result);
	    	
	    	jOut = jResult.getJSONArray("out");
	    	int id;
	    	String name;
	    	for(int i=0; i<jOut.length(); i++){
	    		JSONObject json_data = jOut.getJSONObject(i);

	    		id = json_data.getInt("id");
	    		name = json_data.getString("name") + " - " + json_data.getString("destination");
	    		TrainLine theTrainLine = new TrainLine(name, id);
	    		trainLines.add(theTrainLine);
	    		
	    	}
	    	TrainLineSpinnerAdapter adapter = new TrainLineSpinnerAdapter(trainLines);
	    	
	    	trainLinesSpinner.setAdapter(adapter);
	    	
	    	trainLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

	    		@Override
	    		public void onItemSelected(AdapterView<?> parent, View arg1, int position, long arg3) {
	    			Spinner fromStationsSpinner = (Spinner)findViewById(R.id.fromStationsSpinner);
	    		    Spinner toStationsSpinner = (Spinner)findViewById(R.id.toStationsSpinner);
	    		    ArrayList<Station> stations = new ArrayList<Station>();
	    			TrainLine t = (TrainLine) parent.getItemAtPosition(position);
	    			Station station;
	    			// Get the selected trainline's ID
	    			int theSelectedTrainLineId = t.getId();
	    			for(int i=0; i<jOut.length(); i++){
	    				
	    				try {
							if(jOut.getJSONObject(i).getInt("id") == theSelectedTrainLineId){
								try {
									JSONArray aStations = jOut.getJSONObject(i).getJSONArray("stations");
									for(int a=0; i<aStations.length(); a++){
										JSONObject stationObject = aStations.getJSONObject(a);
										int stId = stationObject.getInt("id");
										String stName = stationObject.getString("name");
										double stLat = stationObject.getDouble("lat");
										double stLog = stationObject.getDouble("lon");
										station = new Station(stId, stName, stLat, stLog);
										stations.add(station);
										Log.i("Added station Id:", String.valueOf(station.getId()));
										Log.i("Added station Name:", station.getName());
										Log.i("Added station Latitude:", String.valueOf(station.getLat()));
										Log.i("Added station Longitude:", String.valueOf(station.getLon()));
										
									}
									break;
									
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								// If the train line and stations has been found, we don't want to loop through more objects 
								
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    				
	    			}
	    			// Setting adapter stations objects
					StationSpinnerAdapter adapter2 = new StationSpinnerAdapter(stations);
					// Adding train line stations to spinners
					fromStationsSpinner.setAdapter(adapter2);
					toStationsSpinner.setAdapter(adapter2);
					Log.i("INFO", "Adapters setted");
	    		}

	    		@Override
	    		public void onNothingSelected(AdapterView<?> arg0) {
	    			// TODO Auto-generated method stub
	    			
	    		}
	        	  
	    	});

	    	
	    }
	    catch(JSONException e){
	    	Log.e("log_tag", "Error trainline parsing data "+e.toString());
	    }
	    
        
      
        //
        
        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), FavoriteActivity.class);
                //  No animations between activities
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(myIntent);
                
            }

        });
        
        ImageButton overviewBtn = (ImageButton) findViewById(R.id.overview);
		overviewBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), OverviewActivity.class);
                //  No animations between activities
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(myIntent);
            }

        });
        
    }
    
    // The custom spinner adapters are created in order to be able to pass objects into the spinners instead of strings
    // It is mainly taken from http://stackoverflow.com/questions/6562236/android-spinner-databind-using-array-list
    public class TrainLineSpinnerAdapter implements SpinnerAdapter{

        /**
         * The internal data (the ArrayList with the Objects).
         */
        ArrayList<TrainLine> data;
        
        public TrainLineSpinnerAdapter(ArrayList<TrainLine> data){
            this.data = data;
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
        	TextView v = new TextView(getApplicationContext());
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
			
			
			    LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			    convertView = vi.inflate(android.R.layout.simple_spinner_dropdown_item, null);
			  
		    
			return getView(position, convertView, parent);
		}



        /**
         * The Views which are shown in when the arrow is clicked
         * (In this case, I used the same as for the "getView"-method.
         */
       

    }

    public class StationSpinnerAdapter implements SpinnerAdapter{

        /**
         * The internal data (the ArrayList with the Objects).
         */
        ArrayList<Station> data;
        
        public StationSpinnerAdapter(ArrayList<Station> data){
            this.data = data;
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
        	TextView v = new TextView(getApplicationContext());
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
			
			
			    LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			    convertView = vi.inflate(android.R.layout.simple_spinner_dropdown_item, null);
			  
		    
			return getView(position, convertView, parent);
		}



        /**
         * The Views which are shown in when the arrow is clicked
         * (In this case, I used the same as for the "getView"-method.
         */
       

    }

    
}