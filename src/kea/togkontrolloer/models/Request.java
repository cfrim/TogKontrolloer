package kea.togkontrolloer.models;

public class Request {

	private String out;
	private String error;
	private String func;
	private String timestamp;
	
	public Request(String out, String error, String func, String timestamp) {
		this.out = out;
		this.error = error;
		this.func = func;
		this.timestamp = timestamp;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
}
