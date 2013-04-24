package kea.togkontrolloer.helpers;

import java.util.ArrayList;

import kea.togkontrolloer.models.*;

public class SpotHelp {

	public static ArrayList<Spotting> spotMatches(TrainLine trainLine, ArrayList<Spotting> spottings){
		
		ArrayList<Spotting> relevantSpottings = new ArrayList<Spotting>();
		
		int spotting_count = spottings.size();
		
		for(int spotting_i = 0; spotting_i < spotting_count; spotting_i++){
			
			boolean match = true;
			
			Spotting spotting = spottings.get(spotting_i);
			
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
			
			if(match) relevantSpottings.add(spotting);
		
		}
		
		return relevantSpottings;
	}
	
}
