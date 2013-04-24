package kea.togkontrolloer.activities;

import java.util.ArrayList;
import java.util.Arrays;

import kea.togkontrolloer.R;
import kea.togkontrolloer.adapters.SpottingListAdapter;
import kea.togkontrolloer.adapters.TrainLineListAdapter;
import kea.togkontrolloer.async.OverviewDownloadTask;
import kea.togkontrolloer.async.SpottingOverviewDownloadTask;
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
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SpottingOverviewActivity extends Activity {

	private boolean showFavorites;
	private boolean isFavorite = false;
	private int line_id;
	private ListView spottingOverview;
	private ArrayAdapter<String> listAdapter;
	private ArrayList<Favorite> favoriteTrainLines;
	private ArrayList<TrainLine> trainLines;
	private ArrayList<Spotting> spottings;
	private ArrayList<OverviewListItem> listItems;
	private TrainLine selectedTrainLine;
	private ArrayList<Spotting> relevantSpottings;
	private boolean doRefresh = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle bundle = getIntent().getExtras();
		showFavorites = bundle.getBoolean("showFavorites");
		line_id = bundle.getInt("line_id");
		
		RequestHelp.setContext(this);
		
        // Use custom styling on title bar 
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_spotting_overview);

     // Find the ListView resource.   
        spottingOverview = (ListView) findViewById( R.id.spottingOverview );  
        
        
        if(RequestHelp.fileExists(RequestHelp.getFilenameFavorites())){
        	favoriteTrainLines = RequestHelp.getFavorites();
        }
        
        Log.i("localget", "inside get trainlines");
        setTrainLines(RequestHelp.getTrainLines(false));
        
        if(RequestHelp.fileExists(RequestHelp.getFilenameSpottings())){
        	Log.i("localget", "inside get spottings");
        	setSpottings(RequestHelp.getSpottings(false));
        }
        
        updateList();
        
        // GET DATA
        SpottingOverviewDownloadTask spottingOverviewDownloadTask = new SpottingOverviewDownloadTask(this);
        spottingOverviewDownloadTask.execute();
        
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_back);
        TextView tv = (TextView) findViewById(R.id.dynamicTitleView); 
        tv.setText("SPOTTINGS"); 
        // Back button
        ImageButton backBtn = (ImageButton) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	finish();
            }

        });

         
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
		
		ImageButton refreshBtn = (ImageButton) findViewById(R.id.refreshBtn);
		
		final SpottingOverviewActivity tempActivity = this;
		refreshBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Log.i("Activity", tempActivity.toString());
				
				doRefresh = true;
				
				SpottingOverviewDownloadTask refreshSpottings = new SpottingOverviewDownloadTask(tempActivity);
				refreshSpottings.execute();
				
			}
		});
              
        // Favorit button
        ImageButton favoritBtn = (ImageButton) findViewById(R.id.favoritBtn);
        
        if(isFavorite){
        	favoritBtn.setImageResource(R.drawable.ic_favorit_added);
			favoritBtn.setTag("No_add");
        }else{
        	favoritBtn.setImageResource(R.drawable.ic_favorit_empty);
			favoritBtn.setTag("Add");
        }
        
        favoritBtn.setOnClickListener(favoritBtnHandler);
	}

		View.OnClickListener favoritBtnHandler = new View.OnClickListener(){
		public void onClick(View v) {
			ImageButton favoritBtn = (ImageButton)findViewById(R.id.favoritBtn);
			
			if (favoritBtn.getTag().toString().equals("Add"))
				{
				favoritBtn.setImageResource(R.drawable.ic_favorit_added);
				favoritBtn.setTag("No_add");

		        RequestHelp.AddRemoveFavorites(line_id, true);
				}
			
			else if (favoritBtn.getTag().toString().equals("No_add"))
			{
				favoritBtn.setImageResource(R.drawable.ic_favorit_empty);
				favoritBtn.setTag("Add");
				
		        RequestHelp.AddRemoveFavorites(line_id, false);
				}	
		}
    };
    
    	public void updateList(){
    		
    		int count = trainLines.size();
    		for(int i = 0; i < count; i++){
    			
    			TrainLine trainLine = trainLines.get(i);
    			
    			if(trainLine.getId() == line_id){
    				
    				if(favoriteTrainLines != null){
    					int count2 = favoriteTrainLines.size();
        				for(int i2 = 0; i2 < count2; i2++){
        					
        					if(trainLine.getId() == favoriteTrainLines.get(i2).getId())
        					{
        						isFavorite = true;
        						break;
        					}
        					
        				}
    				}
    				
    				selectedTrainLine = trainLine;
    				relevantSpottings = new ArrayList<Spotting>(); 
    				if(spottings != null) relevantSpottings = SpotHelp.spotMatches(selectedTrainLine, spottings);
    				
    				TextView titleView = (TextView) findViewById(R.id.title);
    	    		titleView.setText(selectedTrainLine.getDestination());
    	    		
    	    		ImageView thumb_image = (ImageView) findViewById(R.id.list_image);
    	    		if(selectedTrainLine.getIcon().equals("A.png")){
    	            	thumb_image.setImageResource(R.drawable.a);
    	            }
    	            else if(selectedTrainLine.getIcon().equals("B.png")){
    	            	thumb_image.setImageResource(R.drawable.b);
    	            }
    	            else if(selectedTrainLine.getIcon().equals("BX.png")){
    	            	thumb_image.setImageResource(R.drawable.bx);
    	            }
    	            else if(selectedTrainLine.getIcon().equals("C.png")){
    	            	thumb_image.setImageResource(R.drawable.c);
    	            }
    	            else if(selectedTrainLine.getIcon().equals("E.png")){
    	            	thumb_image.setImageResource(R.drawable.e);
    	            }
    	            else if(selectedTrainLine.getIcon().equals("F.png")){
    	            	thumb_image.setImageResource(R.drawable.f);
    	            }
    	            else if(selectedTrainLine.getIcon().equals("H.png")){
    	            	thumb_image.setImageResource(R.drawable.h);
    	            }
    	    		
    	    		SpottingListAdapter spotAdapter = new SpottingListAdapter(this, relevantSpottings);
    	    		spottingOverview.setAdapter(spotAdapter);
    				
    				break;
    			}
    			
    		}
    		
    	}

		public ArrayList<Favorite> getFavoriteTrainLines() {
			return favoriteTrainLines;
		}

		public void setFavoriteTrainLines(ArrayList<Favorite> favoriteTrainLines) {
			this.favoriteTrainLines = favoriteTrainLines;
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

		public ArrayList<OverviewListItem> getListItems() {
			return listItems;
		}

		public void setListItems(ArrayList<OverviewListItem> listItems) {
			this.listItems = listItems;
		}

		public boolean isDoRefresh() {
			return doRefresh;
		}

		public void setDoRefresh(boolean doRefresh) {
			this.doRefresh = doRefresh;
		}
    
}