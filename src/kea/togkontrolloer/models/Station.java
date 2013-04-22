package kea.togkontrolloer.models;

public class Station{
	
	public int id;
	public String name;
	public double lat;
	public double lon;
	public int order;

	public Station(int id, String name, double lat, double lon){
		
		this.id = id;
		this.name = name;
		this.lat = lat;
		this.lon = lon;
		
	}
	
	public Station(int id, String name, double lat, double lon, int order){
		
		this.id = id;
		this.name = name;
		this.lat = lat;
		this.lon = lon;
		this.order = order;
		
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

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}
	
	public int getOrder(){
		return order;
	}
	
	public void setOrder(int order){
		this.order = order;
	}
	
}
