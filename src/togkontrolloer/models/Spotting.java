package togkontrolloer.models;

public class Spotting {
	
	private int id;
	private int userId;
	private Station fromStation;
	private String date;
	private TrainLine trainLine;
	private Station toStation;
	
	public Spotting(int id, int userId, Station fromStation, String date) {
		this.id = id;
		this.userId = userId;
		this.fromStation = fromStation;
		this.date = date;
	}

	public Spotting(int id, int userId, Station fromStation, String date,
			TrainLine trainLine, Station toStation) {
		this.id = id;
		this.userId = userId;
		this.fromStation = fromStation;
		this.date = date;
		this.trainLine = trainLine;
		this.toStation = toStation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Station getFromStation() {
		return fromStation;
	}

	public void setFromStation(Station fromStation) {
		this.fromStation = fromStation;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public TrainLine getTrainLine() {
		return trainLine;
	}

	public void setTrainLine(TrainLine trainLine) {
		this.trainLine = trainLine;
	}

	public Station getToStation() {
		return toStation;
	}

	public void setToStation(Station toStation) {
		this.toStation = toStation;
	}
	
}
