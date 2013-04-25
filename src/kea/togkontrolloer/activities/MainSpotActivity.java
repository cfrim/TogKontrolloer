package kea.togkontrolloer.activities;

import java.util.ArrayList;

import kea.togkontrolloer.R;
import kea.togkontrolloer.adapters.StationSpinnerAdapter;
import kea.togkontrolloer.adapters.TrainLineSpinnerAdapter;
import kea.togkontrolloer.async.MainSpotDownloadTask;
import kea.togkontrolloer.async.MainSpotPostTask;
import kea.togkontrolloer.helpers.RequestHelp;
import kea.togkontrolloer.models.Station;
import kea.togkontrolloer.models.TrainLine;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class MainSpotActivity extends Activity {
	
	// FIELDS
	
	private ArrayList<Station> stations;
	private ArrayList<TrainLine> trainLines;
	public boolean IsInTrain = false;
	public MainSpotActivity activity;
	

	// ONCREATE EVENT
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Create a class-wide reference to the activity itself.
        activity = this;
        
        // Request the feature to have a custom title
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        
        // Set the content view
        setContentView(R.layout.activity_main_spot);
        
        // Set the window title
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
        final TextView tv = (TextView) findViewById(R.id.dynamicTitleView);
        tv.setText("SPOTTING");
        
        // Get GUI elements
        final TextView fromStationText = (TextView)findViewById(R.id.fromStationText);
        final Spinner trainLinesSpinner = (Spinner)findViewById(R.id.trainLinesSpinner);
        final Spinner fromStationsSpinner = (Spinner)findViewById(R.id.fromStationsSpinner);
        
        // Set the context of RequestHelp
        RequestHelp.setContext(this);
        
        // update the Train lines spinner with local data
        setTrainLines(RequestHelp.getTrainLines(false));
        
    	TrainLineSpinnerAdapter trainLineAdapter = new TrainLineSpinnerAdapter(this, trainLines);
    	trainLinesSpinner.setAdapter(trainLineAdapter);
        
    	// update the stations spinner with local data
        setStations(RequestHelp.getStations(false));
        
        StationSpinnerAdapter stationAdapter = new StationSpinnerAdapter(this, stations);
        fromStationsSpinner.setAdapter(stationAdapter);
        
        // Get data online through a AsyncTask that runs in the background
        MainSpotDownloadTask mainSpotDownloadTask = new MainSpotDownloadTask(this);
        mainSpotDownloadTask.execute();
        
        // GUI EVENTS
        
        // onItemSelected trainLineSpinner
        trainLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        	
			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				
				try{
					TrainLine t;
					t = (TrainLine) parent.getItemAtPosition(position);
					
					// If the selected train line id is 0 (NOT SELECTED)
					if(t.getId() == 0){
						IsInTrain = false;
						fromStationText.setText("V\u00E6lg station");
						
						// Get all stations
						ArrayList<Station> stationsList = RequestHelp.getStations();
						// Set adapter
						StationSpinnerAdapter stationsSpinnerAdapter = new StationSpinnerAdapter(activity, stationsList);
						fromStationsSpinner.setAdapter(stationsSpinnerAdapter);
						
					}
					else{
						IsInTrain = true;
						fromStationText.setText("Fra station");
						
						// update stations spinner to show only the selected trainline's stations
						ArrayList<Station> trainLineStationsList = t.getStations();
						StationSpinnerAdapter stationsSpinnerAdapter = new StationSpinnerAdapter(activity, trainLineStationsList);
						fromStationsSpinner.setAdapter(stationsSpinnerAdapter);

					}
				}
				catch(Exception e){
					Log.e("trainline select", e.toString());
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO why is this here?
			}
        	
        });
        
        // OnClick SPOT
        ImageButton spotButton = (ImageButton) findViewById(R.id.addSpot);
        spotButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TrainLine selectedTrainLine = (TrainLine) trainLinesSpinner.getSelectedItem();
				Station selectedFromStation = (Station) fromStationsSpinner.getSelectedItem();
				int trainLineId = selectedTrainLine.getId();
				int fromStationId = selectedFromStation.getId();
				Station toStation = null;
				int toStationId = 0;
				
				if(IsInTrain){
					ArrayList<Station> selectedTrainLineStations = selectedTrainLine.getStations();
					// Iteration through stations of the selected trainline in order to get 
					for (int i = 0; i <= selectedTrainLineStations.size()-1; i++) {
					    if (selectedTrainLineStations.get(i).getId() == fromStationId){
					    	if(i == selectedTrainLineStations.size()-1){
					    		// Setting toStation to be the same as fromStation, if the last station is selected
					    		toStation = selectedFromStation;	
					    	}
					    	else{
					    		// Setting toStation to be the next station on the list
					    		toStation = selectedTrainLineStations.get(i+1);
					    	}
					    	break;  
					    }
					}
					// Setting toStationId
					toStationId = toStation.getId();
					MainSpotPostTask spotPostTask = new MainSpotPostTask(activity, trainLineId, fromStationId, toStationId);
					spotPostTask.execute();
				}
				else{
					trainLineId = 0;
					// Make spotting ************************************	
					MainSpotPostTask spotPostTask = new MainSpotPostTask(activity, trainLineId, fromStationId, toStationId);
					spotPostTask.execute();
				}
				
				
			}
		});
        
        
        // NAVIGATION EVENTS
        ImageButton favoriteBtn = (ImageButton) findViewById(R.id.favorits);
        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), OverviewActivity.class);
                //  No animations between activities
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                myIntent.putExtra("showFavorites", true);
                
                startActivity(myIntent);
                
            }

        });
        
        ImageButton overviewBtn = (ImageButton) findViewById(R.id.overview);
		overviewBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), OverviewActivity.class);
                //  No animations between activities
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                myIntent.putExtra("showFavorites", false);
                startActivity(myIntent);
            }
        });
		
        
    }
	
	
    // GETTERS & SETTERS
    public ArrayList<Station> getStations() {
		return stations;
	}
    
	public ArrayList<TrainLine> getTrainLines() {
		return trainLines;
	}
	
	public void setStations(ArrayList<Station> stations) {
		this.stations = stations;
	}

	public void setTrainLines(ArrayList<TrainLine> trainLines) {
		this.trainLines = trainLines;
	}
	
	
	// ACTIVITY METHODS
	public void updateContent(){
		
		// TODO this isn't used...
		
	}

    
}