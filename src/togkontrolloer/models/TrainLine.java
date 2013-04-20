package togkontrolloer.models;

import java.util.ArrayList;

public class TrainLine {

	private int id;
	private String name;
	private String destination;
	private String icon;
	private ArrayList<Station> stations;
	
	public TrainLine(int id, String name, String destination, String icon, ArrayList<Station> stations) {
		
		this.id = id;
		this.name = name;
		this.destination = destination;
		this.icon = icon;
		this.stations = stations;
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}

	public ArrayList<Station> getStations() {
		return stations;
	}

	public void setStations(ArrayList<Station> stations) {
		this.stations = stations;
	}
		
}
