package kea.togkontrolloer.helpers;

import java.util.ArrayList;
import kea.togkontrolloer.models.*;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class RequestHelp {
	
	private static Context context;
	
	public static void setContext(Context context){
		RequestHelp.context = context;
	}
	
	public static boolean isConnected(){
		
		ConnectivityManager cm =
		        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork.isConnectedOrConnecting();
		return isConnected;
	}

	public static ArrayList<Station> getStations(){
		
		ArrayList<Station> stations = new ArrayList<Station>();
		
		
		
		return stations;
		
	}
	
}
