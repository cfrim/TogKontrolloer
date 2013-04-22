package kea.togkontrolloer.activities;

import java.util.ArrayList;
import java.util.Arrays;

import kea.togkontrolloer.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class OverviewActivity extends Activity {
	
	private ListView trainlinesOverview ;  
	private ArrayAdapter<String> listAdapter ;  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        // Use custom styling on title bar 
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_overview);
        
     // Find the ListView resource.   
        trainlinesOverview = (ListView) findViewById( R.id.trainlinesOverview );  
        
     // Create and populate a List of planet names.  
        String[] planets = new String[] { "Mercury", "Venus", "Earth", "Mars",  
                                          "Jupiter", "Saturn", "Uranus", "Neptune"};    
        ArrayList<String> planetList = new ArrayList<String>();  
        planetList.addAll( Arrays.asList(planets) );  
          
        // Create ArrayAdapter using the planet list.  
        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, planetList);  
          
        // Add more planets. If you passed a String[] instead of a List<String>   
        // into the ArrayAdapter constructor, you must not add more items.   
        // Otherwise an exception will occur.  
        listAdapter.add( "Ceres" );  
        listAdapter.add( "Pluto" );  
        listAdapter.add( "Haumea" );  
        listAdapter.add( "Makemake" );  
        listAdapter.add( "Eris" );  
          
        // Set the ArrayAdapter as the ListView's adapter.  
        trainlinesOverview.setAdapter( listAdapter );    
        
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.overview, menu);
		return true;
	}

}
