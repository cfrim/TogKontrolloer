package kea.togkontrolloer.models;

import java.util.ArrayList;

public class OverviewListItem {

	private TrainLine trainLine;
	private ArrayList<Spotting> spottings;
	
	public OverviewListItem(TrainLine trainLine, ArrayList<Spotting> spottings) {
		this.trainLine = trainLine;
		this.spottings = spottings;
	}

	public TrainLine getTrainLine() {
		return trainLine;
	}

	public void setTrainLine(TrainLine trainLine) {
		this.trainLine = trainLine;
	}

	public ArrayList<Spotting> getSpottings() {
		return spottings;
	}

	public void setSpottings(ArrayList<Spotting> spottings) {
		this.spottings = spottings;
	}
	
}
