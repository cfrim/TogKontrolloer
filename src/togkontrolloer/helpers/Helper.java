package togkontrolloer.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Helper {

	public static double getDistance(double lat1, double lon1, double lat2, double lon2){
		double R = 6371;
		double dLat = Math.toRadians(lat2-lat1);
		double dLon = Math.toRadians(lon2-lon1);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		        Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		double d = R * c;
		
		return d;
	}
	
	public static long getTimeDiff(String d1, String d2){
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = format.parse(d1);
			date2 = format.parse(d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }    
		
		long diff = date2.getTime() - date1.getTime();
		
		/*
		long diffSeconds = diff / 1000 % 60;  
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000);
		long diffOnlySeconds = diff / 1000;
		long diffOnlyHours = diff / (60 * 60 * 1000);
		*/
		
		long diffOnlyMinutes = diff / (60 * 1000);
		
		return diffOnlyMinutes;
		
	}
	
}
