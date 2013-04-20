package kea.togkontrolloer.helpers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class RequestMaker {

	public static String makeRequest(String url){
		String result = "";
		try {
		       try{
		    	   HttpClient httpclient = new DefaultHttpClient();
		    	   HttpPost httppost = new HttpPost(url);
		    	   HttpResponse response = httpclient.execute(httppost);
		    	   HttpEntity entity = response.getEntity();
		    	   InputStream webs = entity.getContent();
		    	   try{
		    		   BufferedReader reader = new BufferedReader(new InputStreamReader(webs, "iso-8859-1"),8);
		    		   StringBuilder sb = new StringBuilder();
		    		   String line = null;
		    		   while((line = reader.readLine()) != null){
		    			   sb.append(line + "\n"); 
		    		   }
		    		   webs.close();
		    		   result=sb.toString();
		    		   
		    	   }catch(Exception e){
		    		   Log.e("log_tag", "Error converting result "+e.toString());
		    	   }
		       }catch(Exception e){
		    		   Log.e("log_tag", "Error httpConnection "+e.toString());
		    	   }
		      
		       
	}
    catch(Exception e){
    	Log.e("ERROR", "ERROR IN CODE: " + e.toString());
    	e.printStackTrace();
    }
	 
	return result;		
	
}
}
