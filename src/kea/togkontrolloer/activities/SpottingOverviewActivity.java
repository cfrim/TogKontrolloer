package kea.togkontrolloer.activities;

import java.util.ArrayList;
import java.util.Arrays;

import kea.togkontrolloer.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class SpottingOverviewActivity extends Activity {

	private ListView spottingOverview ;  
	private ArrayAdapter<String> listAdapter ;  
	ImageButton favoritBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        // Use custom styling on title bar 
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_spotting_overview);
        
     // Find the ListView resource.   
        spottingOverview = (ListView) findViewById( R.id.spottingOverview );  
        
     // Create and populate a List of planet names.  
        String[] planets = new String[] { "Klampenborg", "Charlottenlund"};    
        ArrayList<String> planetList = new ArrayList<String>();  
        planetList.addAll( Arrays.asList(planets) );  
          
        // Create ArrayAdapter using the planet list.  
        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, planetList);  
          
        // Add more planets. If you passed a String[] instead of a List<String>   
        // into the ArrayAdapter constructor, you must not add more items.   
        // Otherwise an exception will occur.  
        listAdapter.add( "Hellerup" );  
          
        // Set the ArrayAdapter as the ListView's adapter.  
        spottingOverview.setAdapter( listAdapter );
        
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
        TextView tv = (TextView) findViewById(R.id.dynamicTitleView); 
        tv.setText("OVERSIGT - Spottings på stationer"); 
        // Back button
        ImageButton backBtn = (ImageButton) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	finish();
            }

        });

         
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
              
        // Favorit button
        favoritBtn = (ImageButton) findViewById(R.id.favoritBtn);
        favoritBtn.setOnClickListener(favoritBtnHandler);
	}

		View.OnClickListener favoritBtnHandler = new View.OnClickListener(){
		public void onClick(View v) {
			ImageButton favoritBtn = (ImageButton)findViewById(R.id.favoritBtn);
			
			if (favoritBtn.getTag().toString().equals("Add"))
				{
				favoritBtn.setImageResource(R.drawable.ic_favorit_added);
				favoritBtn.setTag("No_add");
				}
			
			else if (favoritBtn.getTag().toString().equals("No_add"))
			{
				favoritBtn.setImageResource(R.drawable.ic_favorit_empty);
				favoritBtn.setTag("Add");
				}	
		}
    };
}