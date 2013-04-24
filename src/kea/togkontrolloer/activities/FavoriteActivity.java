package kea.togkontrolloer.activities;

import java.util.ArrayList;

import kea.togkontrolloer.R;
import kea.togkontrolloer.adapters.TrainLineListAdapter;
import kea.togkontrolloer.async.FavoritesTask;
import kea.togkontrolloer.helpers.RequestHelp;
import kea.togkontrolloer.models.Station;
import kea.togkontrolloer.models.TrainLine;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class FavoriteActivity extends Activity {

    private ListView favoriteListView;	
	private ArrayList<TrainLine> favoriteTrainLines;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        // Use custom styling on title bar 
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_favorite);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
        TextView tv = (TextView) findViewById(R.id.dynamicTitleView); 
        tv.setText("FAVORITTER"); 
        
		
        favoriteListView = (ListView)findViewById(R.id.favoritesList);
    	
        TrainLine newFavorite = new TrainLine(4, "F", "Havdrup", "F.png", null);
        RequestHelp.AddRemoveFavorites(newFavorite, true);
        
        TrainLine newFavorite1 = new TrainLine(5, "H", "Test", "H.png", null);
        RequestHelp.AddRemoveFavorites(newFavorite1, false);
        
        FavoritesTask getFavoritesTask = new FavoritesTask(this);
        getFavoritesTask.execute();
		
        
		ImageButton spotBtn = (ImageButton) findViewById(R.id.spot);
		spotBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MainSpotActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(myIntent);
            }
        });
		
		 ImageButton overviewBtn = (ImageButton) findViewById(R.id.overview);
			overviewBtn.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	                Intent myIntent = new Intent(view.getContext(), OverviewActivity.class);
	                myIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	                startActivity(myIntent);
	            }
	        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.favorite, menu);
		return true;
	}
	
	 // GETTERS & SETTERS
    public ArrayList<TrainLine> getFavoriteTrainLines() {
		return favoriteTrainLines;
	}
    
	public void setFavoriteTrainLines(ArrayList<TrainLine> favoriteTrainLines) {
		this.favoriteTrainLines = favoriteTrainLines;
		
		if(this.favoriteTrainLines != null){
			TrainLineListAdapter adapter = new TrainLineListAdapter(this, this.favoriteTrainLines);
			favoriteListView.setAdapter(adapter);
			Log.i("Setting favorites", "Are fetched and setted");
		}
		else {
			favoriteListView.setVisibility(View.GONE);
			TextView test = new TextView(this);
			test.setText("Du har pt. ingen favoritter");
			test.setTextColor(Color.WHITE);
			Log.i("Setting favorites", "No favorites");
		}
        
	}

}
