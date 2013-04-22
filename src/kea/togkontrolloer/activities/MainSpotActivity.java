package kea.togkontrolloer.activities;

import java.util.ArrayList;

import kea.togkontrolloer.adapters.StationSpinnerAdapter;
import kea.togkontrolloer.async.MainSpotDownloadTask;
import kea.togkontrolloer.helpers.RequestHelp;
import kea.togkontrolloer.models.Station;
import kea.togkontrolloer.models.TrainLine;

import kea.togkontrolloer.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.util.Log;
import android.view.View;

import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class MainSpotActivity extends Activity {
	
	private ArrayList<Station> stations;
	private ArrayList<TrainLine> trainLines;


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final Activity activity;
        activity = this;
        // REQUEST FEATURES
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        
        // SET CONTENT VIEW
        setContentView(R.layout.activity_main_spot);
        
        // SET WINDOW TITLE
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
        final TextView tv = (TextView) findViewById(R.id.dynamicTitleView); 
        tv.setText("SPOTTING");
        
        
        // GET DATA
        MainSpotDownloadTask mainSpotDownloadTask = new MainSpotDownloadTask(this);
        mainSpotDownloadTask.execute();
        
        
        // TrainlineSpinner On item selected
        // Get GUI elements
        final TextView fromStationText = (TextView)findViewById(R.id.fromStationText);
        final TextView toStationText = (TextView)findViewById(R.id.toStationText);
        
        Spinner trainLinesSpinner = (Spinner)findViewById(R.id.trainLinesSpinner);
        final Spinner fromStationsSpinner = (Spinner)findViewById(R.id.fromStationsSpinner);
        final Spinner toStationsSpinner = (Spinner)findViewById(R.id.toStationsSpinner);
        
        trainLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				try{
					TrainLine t;
					t = (TrainLine) parent.getItemAtPosition(position);
					// If the selected trainline id is 0 (NOT SELECTED)
					if(t.getId() == 0){
						toStationText.setVisibility(View.GONE);
						toStationsSpinner.setVisibility(View.GONE);
						fromStationText.setText("Vælg station");
						
						// Get all stations
						ArrayList<Station> stationsList = RequestHelp.getStations();
						StationSpinnerAdapter stationsSpinnerAdapter = new StationSpinnerAdapter(activity, stationsList);
						fromStationsSpinner.setAdapter(stationsSpinnerAdapter);
						
					}
					else{
						toStationText.setVisibility(View.VISIBLE);
						toStationsSpinner.setVisibility(View.VISIBLE);
						fromStationText.setText("Fra station:");
					}
				}
				catch(Exception e){
					Log.e("trainline select", e.toString());
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        
        // NAVIGATION EVENTS
        ImageButton favoriteBtn = (ImageButton) findViewById(R.id.favorits);
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
		
		
		
	}

    
}