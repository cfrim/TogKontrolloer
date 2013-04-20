package kea.togkontrolloer.helpers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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
	
	public static String makeRequest(String url){
		
		String result = "";
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try{
			HttpResponse response = httpClient.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			if(statusLine.getStatusCode() == HttpStatus.SC_OK){
				HttpEntity entity = response.getEntity();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				entity.writeTo(out);
		        out.close();
		        result = out.toString();
			}
		}catch (ClientProtocolException e) {
		    // handle exception
		} catch (IOException e) {
		    // handle exception
		}
		
		return result;
		
	}

	public static ArrayList<Station> getStations(){
		
		ArrayList<Station> stations = new ArrayList<Station>();
		
		return stations;
		
	}
	
}
