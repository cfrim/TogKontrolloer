package kea.togkontrolloer.helpers;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import kea.togkontrolloer.models.*;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class RequestHelp {
	
	private static Context context;
	private static Gson gson = new Gson();
	
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
		
		File file;
		try{
			file = context.getFileStreamPath(filename);
			return file.exists();
		}catch(Exception e){
			Log.e("fileExists", e.toString());
			return false;
		}
		
	}
	
	public static String fileTimestamp(String filename){
		
		String file = getLocalJSON(filename);
		
		Request request = getRequest(file);
	
		return request.getTimestamp();
		
	}
	
	/*public static String getServerJSON(String url){
	
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
			
			Log.e("getServerJSON", result);
			
			urlConnection.disconnect();
			
			
		} catch (MalformedURLException e) {
			Log.e("getServerJSON", e.toString());
		} catch (IOException e) {
			Log.e("getServerJSON", e.toString());
		}
		
		return result;
		
	}*/
	
	public static String getServerJSON(String url){
		try {
	        URL u = new URL(url);
	        HttpURLConnection c = (HttpURLConnection) u.openConnection();
	        c.setRequestMethod("GET");
	        c.setRequestProperty("Content-length", "0");
	        c.setUseCaches(false);
	        c.setAllowUserInteraction(false);
	        c.setConnectTimeout(50000);
	        c.setReadTimeout(50000);
	        c.connect();
	        int status = c.getResponseCode();

	        switch (status) {
	            case 200:
	            case 201:
	                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
	                StringBuilder sb = new StringBuilder();
	                String line;
	                while ((line = br.readLine()) != null) {
	                    sb.append(line+"\n");
	                }
	                br.close();
	                return sb.toString();
	        }

	    } catch (MalformedURLException ex) {
	    	Log.e("getServerJSON", ex.toString());
	    } catch (IOException ex) {
	        Log.e("getServerJSON", ex.toString());
	    }
	    return null;
	}
	
	public static boolean setLocalJSON(String filename, String content){
		
		boolean success = true;
		
		FileOutputStream file;
		try {
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
			Date date1 = new Date();
			
			String localTimestamp = format.format(date1);
			
			Log.d("setLocalJSON", localTimestamp);
			
			Request request = gson.fromJson(content, Request.class);
			
			request.setLocalTimestamp(localTimestamp);
			
			content = gson.toJson(request);
			
			file = context.openFileOutput(filename, Context.MODE_PRIVATE);
			file.write(content.getBytes());
			file.close();
		} catch (FileNotFoundException e) {
			success = false;
			Log.e("setLocalJSON", e.toString());
		} catch (IOException e) {
			success = false;
			Log.e("setLocalJSON", e.toString());
		}catch (JsonSyntaxException e) {
			Log.e("setLocalJSON", e.toString());
		}
			
		
		return success;
		
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
			Log.e("getLocalJSON", e.toString());
		} catch (IOException e) {
			Log.e("getLocalJSON", e.toString());
		}
		
		return result;
		
	}
	
	public static Request getRequest(String jsonRequest){
		
		Request request;
		try {
			JsonReader reader = new JsonReader(new StringReader(jsonRequest));
			reader.setLenient(true);
			request = gson.fromJson(reader, Request.class);
		} catch (JsonSyntaxException e) {
			Log.e("getRequest", e.toString());
			request = new Request();
		}
		
		return request;
		
	}

	public static ArrayList<Station> getStations(){
		
		String filename = "stations.json";
		ArrayList<Station> stations = new ArrayList<Station>();
		
		String requestJSON;
		
		if(isConnected() && (!fileExists(filename) || MathHelp.getTimeDiff("now", fileTimestamp(filename)) >= 60 * 24 )){
			
			requestJSON = getServerJSON("http://cfrimodt.dk/test/ticket-dodger/?do=getStations&sec=314bf797090f40e9cbf54909b4814a4c1679cf4c2aae390559c15248a0055c12");
			setLocalJSON(filename, requestJSON);
			
		}else{
			
			requestJSON = getLocalJSON(filename);
			
		}
		
		
		if(requestJSON != ""){
			
			Request request = getRequest(requestJSON);
			
			try {
				JsonArray stationsJSON = request.getOut().getAsJsonArray();
				
				int count = stationsJSON.size();
				for(int i = 0; i < count; i++){
					
					Station station = gson.fromJson(stationsJSON.get(i), Station.class);
					
					stations.add(station);
					
				}
				
				
			} catch (JsonSyntaxException e) {
				
				Log.e("getStations", e.toString());
			}
			
			
		}
		
		return stations;
		
	}
	
	
	public static ArrayList<TrainLine> getTrainLines(){
		
		String filename = "trainlines.json";
		ArrayList<TrainLine> trainLines = new ArrayList<TrainLine>();
		String requestJSON;
		
		/*if(isConnected() && (!fileExists(filename) || MathHelp.getTimeDiff("now", fileTimestamp(filename)) >= 60 * 24 )){
			
			requestJSON = getServerJSON("http://cfrimodt.dk/test/ticket-dodger/?do=getLines&sec=314bf797090f40e9cbf54909b4814a4c1679cf4c2aae390559c15248a0055c12");
			setLocalJSON(filename, requestJSON);
			requestJSON = getLocalJSON(filename);
			Log.e("requestJSON", "bob: "+requestJSON);
			
		}else{
			
			requestJSON = getLocalJSON(filename);
			//bo
			
		}*/
		
		requestJSON = getServerJSON("http://cfrimodt.dk/test/ticket-dodger/?do=getLines&sec=314bf797090f40e9cbf54909b4814a4c1679cf4c2aae390559c15248a0055c12");
		setLocalJSON(filename+"2", requestJSON);
		
		
		if(requestJSON != ""){
			
			Request request = getRequest(requestJSON);
			
			try {
				JsonArray linesJSON = request.getOut().getAsJsonArray();
				
				int count = linesJSON.size();
				for(int i = 0; i < count; i++){
					
					TrainLine trainLine = gson.fromJson(linesJSON.get(i), TrainLine.class);
					
					trainLines.add(trainLine);
					
				}
				
				
			} catch (JsonSyntaxException e) {
				
				Log.e("getTrainLines", e.toString());
			}
			
			
		}
		
		return trainLines;
		
	}
	
}
