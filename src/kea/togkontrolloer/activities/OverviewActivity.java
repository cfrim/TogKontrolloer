package kea.togkontrolloer.activities;

import java.util.ArrayList;

import kea.togkontrolloer.R;

import kea.togkontrolloer.adapters.StationSpinnerAdapter;
import kea.togkontrolloer.adapters.TrainLineSpinnerAdapter;
import kea.togkontrolloer.async.MainSpotDownloadTask;
import kea.togkontrolloer.async.OverviewDownloadTask;
import kea.togkontrolloer.helpers.RequestHelp;
import kea.togkontrolloer.models.Spotting;

import kea.togkontrolloer.models.TrainLine;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class OverviewActivity extends Activity {
	
	private ListView trainlinesOverview; 
	private ArrayList<TrainLine> trainLines;
	private ArrayList<Spotting> spottings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        // Use custom styling on title bar 
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_overview);
        
        RequestHelp.setContext(this);
        
        if(RequestHelp.fileExists(RequestHelp.getFilenameTrainLines())){
        	setTrainLines(RequestHelp.getTrainLines(false));
        }
        
        if(RequestHelp.fileExists(RequestHelp.getFilenameSpottings())){
        	setSpottings(RequestHelp.getSpottings(false));
        }
        
        // GET DATA
        OverviewDownloadTask overviewDownloadTask = new OverviewDownloadTask(this);
        overviewDownloadTask.execute();
        
     // Find the ListView resource.   
        trainlinesOverview = (ListView) findViewById( R.id.trainlinesOverview );  
        
     // Create and populate a List of planet names.  
        String[] planets = new String[] { "Klampenborg", "Ballerup", "Hillerød", "Holte",  
                                          "Ny Ellebjerg", "Frederikssund", "Østerbro", "Nøreport", "Vesterport"};    
        ArrayList<TrainLine> planetList = new ArrayList<TrainLine>();  
       
        
        for(int i=0;i<planets.length; i++){
        	TrainLine tLine = new TrainLine(1, "", planets[i], "F.png", null);
        	planetList.add(tLine); 
        }
           
        // Create ArrayAdapter using the planet list.
        
          
        // Add more planets. If you passed a String[] instead of a List<String>   
        // into the ArrayAdapter constructor, you must not add more items.   
        // Otherwise an exception will occur.  
          
        // Set the ArrayAdapter as the ListView's adapter. 
        TrainLineListAdapter listAdapter = new TrainLineListAdapter(this, planetList);
        trainlinesOverview.setAdapter(listAdapter);
        
        trainlinesOverview.setOnItemClickListener(new OnItemClickListener() {

            	public void onItemClick(AdapterView<?> parent, View view,
            		      int position, long id) {

            		   Intent newActivity = new Intent(view.getContext(), SpottingOverviewActivity.class).
            				   putExtra("trainLineId", 5);     
            		   startActivity(newActivity);


            }
        });
        
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
        TextView tv = (TextView) findViewById(R.id.dynamicTitleView); 
        tv.setText("OVERSIGT"); 
		
		ImageButton spotBtn = (ImageButton) findViewById(R.id.spot);
		spotBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MainSpotActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(myIntent);
            }

        });
		
		ImageButton favoriteBtn = (ImageButton) findViewById(R.id.favorits);
        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), FavoriteActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(myIntent);
                
            }

        });
	}

	public ArrayList<TrainLine> getTrainLines() {
		return trainLines;
	}

	public void setTrainLines(ArrayList<TrainLine> trainLines) {
		this.trainLines = trainLines;
	}

	public ArrayList<Spotting> getSpottings() {
		return spottings;
	}

	public void setSpottings(ArrayList<Spotting> spottings) {
		this.spottings = spottings;
	}

}
