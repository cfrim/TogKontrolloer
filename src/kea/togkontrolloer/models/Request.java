package kea.togkontrolloer.models;

import com.google.gson.JsonElement;

public class Request {

	private JsonElement out;
	private String error;
	private String func;
	private String timestamp;
	private String localTimestamp;
	
	public Request(){
		
	}
	
	public Request(JsonElement out, String error, String func, String timestamp) {
		this.out = out;
		this.error = error;
		this.func = func;
		this.timestamp = timestamp;
	}

	
	public Request(JsonElement out, String error, String func, String timestamp, String localTimestamp) {
		this.out = out;
		this.error = error;
		this.func = func;
		this.timestamp = timestamp;
		this.localTimestamp = localTimestamp;
	}
	
	public JsonElement getOut() {
		return out;
	}

	public void setOut(JsonElement out) {
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

	public String getLocalTimestamp() {
		return localTimestamp;
	}

	public void setLocalTimestamp(String localTimestamp) {
		this.localTimestamp = localTimestamp;
	}
	
}
