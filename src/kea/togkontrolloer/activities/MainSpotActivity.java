package kea.togkontrolloer.activities;

import java.util.ArrayList;

import kea.togkontrolloer.async.MainSpotDownloadTask;
import kea.togkontrolloer.helpers.RequestHelp;
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
	
	private ArrayList<Station> stations;
	private ArrayList<TrainLine> trainLines;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
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