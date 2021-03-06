package kea.togkontrolloer.helpers;

import java.io.BufferedReader;
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

import kea.togkontrolloer.models.Favorite;
import kea.togkontrolloer.models.Request;
import kea.togkontrolloer.models.Spotting;
import kea.togkontrolloer.models.Station;
import kea.togkontrolloer.models.TrainLine;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

public class RequestHelp {
	
	private static Context context;
	private static Gson gson = new Gson();
	private static String filenameTrainLines = "trainlines.json";
	private static String filenameStations = "stations.json";
	private static String filenameUser = "user.json";
	private static String filenameSpottings = "spottings.json";
	private static String filenameFavorites = "favorites.json";
	
	public static void setContext(Context context){
		RequestHelp.context = context;
	}
	
	public static boolean isConnected(){
		
		boolean isConnected = false;
		
		ConnectivityManager cm =
		        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		
		if(activeNetwork != null){
			isConnected = activeNetwork.isConnected();
		}
		
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
	
		return request.getLocalTimestamp();
		
	}
	
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
	
	
	public static String postSpotting(String url){
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
	
	
	public static boolean setFavoritesLocalJSON(String filename, String content){
		
		boolean success = true;
		
		FileOutputStream file;
		try {
			file = context.openFileOutput(filename, Context.MODE_PRIVATE);
			file.write(content.getBytes());
			file.close();
		} catch (FileNotFoundException e) {
			success = false;
			Log.e("setLocalFavoriteJSON", e.toString());
		} catch (IOException e) {
			success = false;
			Log.e("setLocalFavoriteJSON", e.toString());
		}catch (JsonSyntaxException e) {
			Log.e("setLocalFavoriteJSON", e.toString());
		}
			

		return success;
		
	}
	
	
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
	
	public static String getDefaultJSON(String filename){
		
		String result = "";
		
		InputStream file;
		
		try {
			file = context.getResources().getAssets().open(filename);
			StringBuffer fileContent = new StringBuffer("");
			byte[] buffer = new byte[1024];
			
			while(file.read(buffer) != -1){
				fileContent.append(new String(buffer));
			}
			
			result = fileContent.toString();
		} catch (IOException e) {
			Log.e("getDefaultJSON", e.toString());
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
		
		if(isConnected() && (!fileExists(filenameStations) || MathHelp.getTimeDiff("now", fileTimestamp(filenameStations)) >= 60 * 24 )){
			
			return getStations(true);
			
		}else{
			
			return getStations(false);
			
		}
		
	}

	public static ArrayList<Station> getStations(boolean getOnline){
		
		ArrayList<Station> stations = new ArrayList<Station>();
		
		String requestJSON;
		
		if(getOnline){
			
			requestJSON = getServerJSON("http://cfrimodt.dk/togkontrolloer/?do=getStations&sec=314bf797090f40e9cbf54909b4814a4c1679cf4c2aae390559c15248a0055c12");
			setLocalJSON(filenameStations, requestJSON);
			
		}else if(fileExists(filenameStations)){
			
			requestJSON = getLocalJSON(filenameStations);
			
		}else{
			
			requestJSON = getDefaultJSON(filenameStations);
			
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
		
		if(isConnected() && (!fileExists(filenameTrainLines) || MathHelp.getTimeDiff("now", fileTimestamp(filenameTrainLines)) >= 60 * 24 )){
			
			return getTrainLines(true);
			
		}else{
			
			return getTrainLines(false);
			
		}
		
	}
	
	public static ArrayList<TrainLine> getTrainLines(boolean getOnline){
		
		ArrayList<TrainLine> trainLines = new ArrayList<TrainLine>();
		String requestJSON;
		
		if(getOnline){
			
			requestJSON = getServerJSON("http://cfrimodt.dk/togkontrolloer/?do=getLines&sec=314bf797090f40e9cbf54909b4814a4c1679cf4c2aae390559c15248a0055c12");
			setLocalJSON(filenameTrainLines, requestJSON);
			
		}else if(fileExists(filenameTrainLines)){
			
			requestJSON = getLocalJSON(filenameTrainLines);
			
		}else{
			
			requestJSON = getDefaultJSON(filenameTrainLines);
			
		}
		
		
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
	
	public static void AddRemoveFavorites(int id, boolean addRemove){
		ArrayList<Favorite> out = new ArrayList<Favorite>();
				
		if(addRemove){
			if(!fileExists(getFilenameFavorites())){
				Favorite newfavorite = new Favorite(id);
				out.add(newfavorite);
				String content = gson.toJson(out);
				setFavoritesLocalJSON(getFilenameFavorites(), content);
			}
			else{
				String requestJSON = RequestHelp.getLocalJSON(getFilenameFavorites());
				
				JsonArray aExistingFavs;
				Favorite newfavorite = new Favorite(id);
				JsonReader reader = new JsonReader(new StringReader(requestJSON));
				reader.setLenient(true);
				Log.i("Favorites json", reader.toString());
				aExistingFavs = gson.fromJson(reader, JsonArray.class);
				
				boolean alreadyAdded = false;
				for(int i=0; i<aExistingFavs.size(); i++){
					Favorite oExistingFav = gson.fromJson(aExistingFavs.get(i), Favorite.class);
					out.add(oExistingFav);
					if(newfavorite.getId()==(int)oExistingFav.getId()){
						alreadyAdded = true;
					}
				}
				
				if(!alreadyAdded){
					out.add(newfavorite);
				}
				String content = gson.toJson(out);
				setFavoritesLocalJSON(getFilenameFavorites(), content);
				Log.i("Updated json", content);
			}
		}
		else{
			if(fileExists(getFilenameFavorites())){
				
				int tLineId = id;
				
				String requestJSON = RequestHelp.getLocalJSON(getFilenameFavorites());
				
				ArrayList<Favorite> list = new ArrayList<Favorite>();
				
				JsonArray aExistingFavs;
				
				JsonReader reader = new JsonReader(new StringReader(requestJSON));
				
				reader.setLenient(true);
				
				int positionOfObject = 0;
				
				if(requestJSON != ""){
					aExistingFavs = gson.fromJson(reader, JsonArray.class);
					for(int i=0; i<aExistingFavs.size(); i++){
						Favorite oExistingFav = gson.fromJson(aExistingFavs.get(i), Favorite.class);
						list.add(oExistingFav);
						if(oExistingFav.getId() == tLineId){
							positionOfObject = i;
						}
					}
					list.remove(positionOfObject);
					String content = gson.toJson(list);
					setFavoritesLocalJSON(getFilenameFavorites(), content);
				}
			}
		}
	}
	
	public static ArrayList<Favorite> getFavorites(){
		String requestJSON;
		ArrayList<Favorite> favoriteTrainLines = new ArrayList<Favorite>();
		// Check if favorites file exists
		if(!fileExists(getFilenameFavorites())){
			return null;
		}
		else{
			requestJSON = RequestHelp.getLocalJSON(getFilenameFavorites());
			if(requestJSON != ""){
				
				try{
					JSONArray favoritesTrainLinesJSON = new JSONArray(requestJSON);
					int count = favoritesTrainLinesJSON.length();
					for(int i = 0; i < count; i++){
						JSONObject favoriteObject = favoritesTrainLinesJSON.getJSONObject(i);
						
						Favorite favorite = new Favorite(favoriteObject.getInt("id")); 
						favoriteTrainLines.add(favorite);
					}
					
					return favoriteTrainLines;
				}
				catch(Exception e){
					Log.e("Favorites get JSON array", e.toString());
				}
				
				
			}
			
			return null;
		}

	}
	
	public static int getUserId(){
		JsonObject userId;
		String requestJSON = "";
		
		if(fileExists(filenameUser)){
			requestJSON = getLocalJSON(filenameUser);
		}
		else{
			if(isConnected()){
				requestJSON = getServerJSON("http://cfrimodt.dk/togkontrolloer/?do=getUser&sec=314bf797090f40e9cbf54909b4814a4c1679cf4c2aae390559c15248a0055c12");
				setLocalJSON(filenameUser, requestJSON);
			}
		}
		
		if(requestJSON == ""){
			return 0;
		}else{
			Request request = getRequest(requestJSON);
			JsonElement jOut = request.getOut();
			userId = jOut.getAsJsonObject();
			
			return userId.get("id").getAsInt();
		}
	}
		
	public static ArrayList<Spotting> getSpottings(){
		
		if(isConnected() && (!fileExists(filenameSpottings) || MathHelp.getTimeDiff("now", fileTimestamp(filenameSpottings)) >= 5 )){
			
			return getSpottings(true);
			
		}else{
			
			return getSpottings(false);
			
		}
		
	}
	
	public static ArrayList<Spotting> getSpottings(boolean getOnline){
		
		ArrayList<Spotting> spottings = new ArrayList<Spotting>();
		
		String requestJSON;
		
		if(getOnline){
			
			requestJSON = getServerJSON("http://cfrimodt.dk/togkontrolloer/?do=getSpottings&sec=314bf797090f40e9cbf54909b4814a4c1679cf4c2aae390559c15248a0055c12");
			setLocalJSON(filenameSpottings, requestJSON);
			
		}else{
			
			requestJSON = getLocalJSON(filenameSpottings);
			
		}
		
		if(requestJSON != ""){
			
			Request request = getRequest(requestJSON);
			try{
				
				JsonArray spottingsJSON = request.getOut().getAsJsonArray();
				
				int count = spottingsJSON.size();
				for(int i = 0; i < count; i++){
					
					Spotting spotting = gson.fromJson(spottingsJSON.get(i), Spotting.class);
					
					spottings.add(spotting);
					
				}
				
			}catch(JsonSyntaxException e){
				Log.e("getSpottings", e.toString());
			}
			
		}
		
		return spottings;
	}

	public static String getFilenameTrainLines() {
		return filenameTrainLines;
	}

	public static void setFilenameTrainLines(String filenameTrainLines) {
		RequestHelp.filenameTrainLines = filenameTrainLines;
	}

	public static String getFilenameStations() {
		return filenameStations;
	}

	public static void setFilenameStations(String filenameStations) {
		RequestHelp.filenameStations = filenameStations;
	}

	public static String getFilenameSpottings() {
		return filenameSpottings;
	}

	public static void setFilenameSpottings(String filenameSpottings) {
		RequestHelp.filenameSpottings = filenameSpottings;
	}

	public static String getFilenameUser() {
		return filenameUser;
	}

	public static void setFilenameUser(String filenameUser) {
		RequestHelp.filenameUser = filenameUser;
	}
	
	public static String getFilenameFavorites() {
		return filenameFavorites;
	}

	public static void setFilenameFavorites(String filenameFavorites) {
		RequestHelp.filenameFavorites = filenameFavorites;
	}
	
}
