package kea.togkontrolloer.activities;

import java.util.ArrayList;

import kea.togkontrolloer.R;
import kea.togkontrolloer.adapters.TrainLineListAdapter;
import kea.togkontrolloer.models.TrainLine;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class FavoriteActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        // Use custom styling on title bar 
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_favorite);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
        TextView tv = (TextView) findViewById(R.id.dynamicTitleView); 
        tv.setText("FAVORITTER"); 
        
		
    	
    	ListView favoriteListView = (ListView)findViewById(R.id.favoritesList);
    	
    	ArrayList<TrainLine> favoriteList = new ArrayList<TrainLine>();
        
    	TrainLine theItemObject = new TrainLine(1, "", "Klampenborg", "F.png", null);
        favoriteList.add(theItemObject);
        
        TrainLine theItemObject1 = new TrainLine(1, "", "Lortenborg", "F.png", null);
        favoriteList.add(theItemObject1);
        
        
        TrainLineListAdapter adapter = new TrainLineListAdapter(this, favoriteList);
     
        favoriteListView.setAdapter(adapter);
		
        
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

}
