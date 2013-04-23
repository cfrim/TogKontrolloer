package kea.togkontrolloer.models;

public class Spotting {
	
	private int id;
	private int users_id;
	private int from_stations_id;
	private String created_at;
	private int trust_points;
	private String stationName1;
	private int count;
	private int lines_id;
	private int to_stations_id;
	private String stationName2;
	
	public Spotting(int id, int users_id, int from_stations_id,
			String created_at, int trust_points, String stationName1, int count) {
		this.id = id;
		this.users_id = users_id;
		this.from_stations_id = from_stations_id;
		this.created_at = created_at;
		this.trust_points = trust_points;
		this.stationName1 = stationName1;
		this.count = count;
	}

	public Spotting(int id, int users_id, int from_stations_id,
			String created_at, int trust_points, String stationName1,
			int count, int lines_id, int to_stations_id, String stationName2) {
		this.id = id;
		this.users_id = users_id;
		this.from_stations_id = from_stations_id;
		this.created_at = created_at;
		this.trust_points = trust_points;
		this.stationName1 = stationName1;
		this.count = count;
		this.lines_id = lines_id;
		this.to_stations_id = to_stations_id;
		this.stationName2 = stationName2;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUsers_id() {
		return users_id;
	}

	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}

	public int getFrom_stations_id() {
		return from_stations_id;
	}

	public void setFrom_stations_id(int from_stations_id) {
		this.from_stations_id = from_stations_id;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public int getTrust_points() {
		return trust_points;
	}

	public void setTrust_points(int trust_points) {
		this.trust_points = trust_points;
	}

	public String getStationName1() {
		return stationName1;
	}

	public void setStationName1(String stationName1) {
		this.stationName1 = stationName1;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getLines_id() {
		return lines_id;
	}

	public void setLines_id(int lines_id) {
		this.lines_id = lines_id;
	}

	public int getTo_stations_id() {
		return to_stations_id;
	}

	public void setTo_stations_id(int to_stations_id) {
		this.to_stations_id = to_stations_id;
	}

	public String getStationName2() {
		return stationName2;
	}

	public void setStationName2(String stationName2) {
		this.stationName2 = stationName2;
	}
	
	
	
}
