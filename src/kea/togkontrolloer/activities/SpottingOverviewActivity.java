package kea.togkontrolloer.activities;

import java.util.ArrayList;
import java.util.Arrays;

import kea.togkontrolloer.R;
import kea.togkontrolloer.adapters.TrainLineListAdapter;
import kea.togkontrolloer.helpers.RequestHelp;
import kea.togkontrolloer.models.TrainLine;
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

	private boolean showFavorites;
	private int line_id;
	private ListView spottingOverview ;  
	private ArrayAdapter<String> listAdapter ;  
	ImageButton favoritBtn;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle bundle = getIntent().getExtras();
		showFavorites = bundle.getBoolean("showFavorites");
		line_id = bundle.getInt("line_id");
		
        // Use custom styling on title bar 
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_spotting_overview);

     // Find the ListView resource.   
        spottingOverview = (ListView) findViewById( R.id.spottingOverview );  
        
     // Create and populate a List of planet names.  
        String[] planets = new String[] {"Charlottenlund"};    
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
    
}