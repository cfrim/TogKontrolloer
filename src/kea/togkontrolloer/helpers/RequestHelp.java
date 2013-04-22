package kea.togkontrolloer.helpers;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

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
	
	public static boolean fileExists(String filename){
		
		File file = context.getFileStreamPath(filename);
		
		return file.exists();
		
	}
	
	public static String fileTimestamp(String filename){
		
		String file = getLocalJSON(filename);
		
		Request request = getRequest(file);
	
		return request.getTimestamp();
		
	}
	
	public static String getServerJSON(String url){
	
		String result = "";
		HttpURLConnection urlConnection;
		try{
			
			URL oUrl = new URL(url);
			urlConnection = (HttpURLConnection) oUrl.openConnection();
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			StringBuffer streamContent = new StringBuffer("");
			byte[] buffer = new byte[1024];
			
			while(in.read(buffer) != -1){
				streamContent.append(new String(buffer));
			}
			
			result = streamContent.toString();
			
			urlConnection.disconnect();
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	/*public static String getServerJSON(String url){
		
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
		
	}*/
	
	public static String getLocalJSON(String filename){
		
		String result = "";
		
		FileInputStream file;
		try{
			file = context.openFileInput(filename);
			StringBuffer fileContent = new StringBuffer("");
			byte[] buffer = new byte[1024];
			
			while(file.read(buffer) != -1){
				fileContent.append(new String(buffer));
			}
			
			result = fileContent.toString();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	public static Request getRequest(String jsonRequest){
		
		Request request;
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jsonRequest);
			String out = jsonObject.getString("out");
			String error = jsonObject.getString("error");
			String func = jsonObject.getString("func");
			String timestamp = jsonObject.getString("timestamp");
			request = new Request(out, error, func, timestamp);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request = new Request();
		}
		
		return request;
		
	}

	public static ArrayList<Station> getStations(){
		
		ArrayList<Station> stations = new ArrayList<Station>();
		
		if(isConnected()){
			
			
			
		}
		
		return stations;
		
	}
	
}
