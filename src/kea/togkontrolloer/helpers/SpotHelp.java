package kea.togkontrolloer.helpers;

import java.util.ArrayList;

import kea.togkontrolloer.models.*;

public class SpotHelp {

	public static boolean spotMatches(TrainLine trainLine, Spotting spotting){
		boolean match = true;
		
		if(spotting.getLines_id() != 0 && trainLine.getId() != spotting.getLines_id()){
			match = false;
		}
		
		ArrayList<Station> stations = trainLine.getStations();
		
		boolean found = false;
		
		int count = stations.size();
		for(int i = 0; i < count; i++){
			
			if(stations.get(i).getId() == spotting.getFrom_stations_id()){
				found = true;
				break;
			}
			
		}
		
		if(!found) match = false;
		
		return match;
	}
	
}
