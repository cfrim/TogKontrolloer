package togkontrolloer.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import togkontrolloer.helpers.RequestMaker;

import dk.kea.togkontrolloer.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class MainSpotActivity extends Activity {
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Use custom styling on title bar 
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main_spot);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
        TextView tv = (TextView) findViewById(R.id.dynamicTitleView); 
        tv.setText("SPOTTING"); 
        
        ImageButton favoriteBtn = (ImageButton) findViewById(R.id.favorits);
        try{
        // TrainLineSpinner section

        	String result = RequestMaker.makeRequest("http://cfrimodt.dk/test/ticket-dodger/?do=getLines&sec=314bf797090f40e9cbf54909b4814a4c1679cf4c2aae390559c15248a0055c12");
	    
	    	JSONObject jResult = new JSONObject(result);
	    	
	    	JSONArray jOut = jResult.getJSONArray("out");
	    	
	    	Spinner trainLinesSpinner = (Spinner )findViewById(R.id.trainLinesSpinner);
	    	List<String> list = new ArrayList<String>();
	    	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
	    	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    	
	    	
	    	for(int i=0; i<jOut.length(); i++){
	    		JSONObject json_data = jOut.getJSONObject(i);
	    		list.add(json_data.getString("name")+" - "+json_data.getString("destination"));
	    	}
	    	
	    	trainLinesSpinner.setAdapter(dataAdapter);
	    }
	    catch(JSONException e){
	    	Log.e("log_tag", "Error trainline parsing data "+e.toString());
	    }
        
        
        
        //
        
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
}