package kea.togkontrolloer.activities;

import java.util.ArrayList;

import kea.togkontrolloer.R;
import kea.togkontrolloer.adapters.TrainLineListAdapter;
import kea.togkontrolloer.async.OverviewDownloadTask;
import kea.togkontrolloer.helpers.RequestHelp;
import kea.togkontrolloer.helpers.SpotHelp;
import kea.togkontrolloer.models.Favorite;
import kea.togkontrolloer.models.OverviewListItem;
import kea.togkontrolloer.models.Spotting;
import kea.togkontrolloer.models.TrainLine;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class OverviewActivity extends Activity {
	
	// FIELDS
	
	private boolean showFavorites;
	private ListView trainlinesOverview;
	private ArrayList<Favorite> favoriteTrainLines;
	private ArrayList<TrainLine> trainLines;
	private ArrayList<Spotting> spottings;
	private ArrayList<OverviewListItem> listItems;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Getting the passed information from the buttom buttons either true or false depending on press favorites of or overview 
		Bundle bundle = getIntent().getExtras();
		showFavorites = bundle.getBoolean("showFavorites");
        // Use custom styling on title bar 
		
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_overview);
        
        // getting the list view from 
        trainlinesOverview = (ListView) findViewById( R.id.trainlinesOverview );
        
        // Prepare the RequestHelp class, so it can use the activity's context to
        // check if there is a network connection and other misc. things that need
        // an activity's context. (IE. checking if files exist).
        RequestHelp.setContext(this);
        
        // If the "favorites.json" file exists, get the favorites from it.
        if(RequestHelp.fileExists(RequestHelp.getFilenameFavorites())){
        	favoriteTrainLines = RequestHelp.getFavorites();
        }
        
        // Always get trainlines locally, either from a previously downloaded 
        // "trainlines.json" or the default one included in the apk.
        setTrainLines(RequestHelp.getTrainLines(false));
        
        // Load saved spottings if they exist.
        if(RequestHelp.fileExists(RequestHelp.getFilenameSpottings())){
        	setSpottings(RequestHelp.getSpottings(false));
        }
        
        // This method takes care of updating and displaying the lists correctly.
        updateList();
        
        // This is an AsyncTask that takes care of downloading trainlines and
        // spottings from the network. we have to do it here, since network
        // access is not allowed in the main activity in newer android versions.
        OverviewDownloadTask overviewDownloadTask = new OverviewDownloadTask(this);
        overviewDownloadTask.execute();

        trainlinesOverview.setOnItemClickListener(new OnItemClickListener() {

            	public void onItemClick(AdapterView<?> parent, View view,
            		      int position, long id) {

            		  
            		       		   
            		   Log.i("position", String.valueOf(position));
            		   	
            		   	   // get object from clicked item
            			   OverviewListItem listItem;
            			   listItem = (OverviewListItem) parent.getItemAtPosition(position);
            			   
            			   // Preparation for new activity start
            			   Intent newActivity = new Intent(view.getContext(), SpottingOverviewActivity.class);
            			   
            			   // Pass boolean to detect if the item is clicked from "Favorites"
            			   newActivity.putExtra("showFavorites", showFavorites);
            			   // Pass line id on the selected train line object 
            		   	   newActivity.putExtra("line_id", listItem.getTrainLine().getId());
            		   	   startActivity(newActivity);


            }
        });
        
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
        TextView tv = (TextView) findViewById(R.id.dynamicTitleView);
        
        if(showFavorites) tv.setText("FAVORITTER");
        else tv.setText("OVERSIGT");
		
		ImageButton spotBtn = (ImageButton) findViewById(R.id.spot);
		ImageButton overviewBtn = (ImageButton) findViewById(R.id.overview);
		ImageButton favoriteBtn = (ImageButton) findViewById(R.id.favorits);
		
		spotBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MainSpotActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(myIntent);
            }

        });
		
		if(showFavorites){
			favoriteBtn.setBackgroundColor(getResources().getColor(R.color.SelectedColor));
		overviewBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), OverviewActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                myIntent.putExtra("showFavorites", false);
                startActivity(myIntent);
            }
        });
		}
		else{
			overviewBtn.setBackgroundColor(getResources().getColor(R.color.SelectedColor));
        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), OverviewActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                myIntent.putExtra("showFavorites", true);
                startActivity(myIntent);
                
                
            }

        });
		}
		
		
        
        
	}
	
	
	
	@Override
	protected void onStart() {
		super.onStart();
		
		RequestHelp.setContext(this);
        if(RequestHelp.fileExists(RequestHelp.getFilenameFavorites())){
        	favoriteTrainLines = RequestHelp.getFavorites();
        }
        
        updateList();
		
	}



	public void updateList(){
		
		ArrayList<OverviewListItem> tempListItems = new ArrayList<OverviewListItem>();
		
		if(showFavorites){
			
			int count;
			if(favoriteTrainLines != null) count = favoriteTrainLines.size();
			else count = 0;
			for(int i = 0; i < count; i++){
				
				Favorite thisFavorite = favoriteTrainLines.get(i);
				TrainLine tempTrainLine;
				
				int count2 = trainLines.size();
				for(int i2 = 0; i2 < count2; i2++){
					
					if(thisFavorite.getId() == trainLines.get(i2).getId()){
						
						tempTrainLine = trainLines.get(i2);
						
						ArrayList<Spotting> tempSpottings = new ArrayList<Spotting>();
						
						if(spottings != null) tempSpottings = SpotHelp.spotMatches(tempTrainLine, spottings);
						
						tempListItems.add(new OverviewListItem(tempTrainLine, tempSpottings));
						
						break;
						
					}
					
				}

				
			}
			
		}else{
			
			int count = trainLines.size();
			for(int i = 0; i < count; i++){
				
				TrainLine tempTrainLine = trainLines.get(i);
				ArrayList<Spotting> tempSpottings = new ArrayList<Spotting>();
				
				if(spottings != null) tempSpottings = SpotHelp.spotMatches(tempTrainLine, spottings);
				
				tempListItems.add(new OverviewListItem(tempTrainLine, tempSpottings));
				
			}
			
		}
		
		
		// Adapting array list of overviewListItems into ListView
		listItems = tempListItems;
		
		TrainLineListAdapter tAdapter = new TrainLineListAdapter(this, listItems);
		trainlinesOverview.setAdapter(tAdapter);
		
	}

	public ArrayList<TrainLine> getTrainLines() {
		return trainLines;
	}

	public void setTrainLines(ArrayList<TrainLine> trainLines) {
		this.trainLines = trainLines;
		// TODO Remove this adapter stuff
		
		
	}

	public ArrayList<Spotting> getSpottings() {
		return spottings;
	}

	public void setSpottings(ArrayList<Spotting> spottings) {
		this.spottings = spottings;
	}

	public ArrayList<Favorite> getFavoriteTrainLines() {
		return favoriteTrainLines;
	}

	public void setFavoriteTrainLines(ArrayList<Favorite> favoriteTrainLines) {
		this.favoriteTrainLines = favoriteTrainLines;
	}

}
