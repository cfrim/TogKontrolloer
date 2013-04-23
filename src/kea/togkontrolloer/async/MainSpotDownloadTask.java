package kea.togkontrolloer.async;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Spinner;
import kea.togkontrolloer.R;
import kea.togkontrolloer.activities.MainSpotActivity;
import kea.togkontrolloer.adapters.StationSpinnerAdapter;
import kea.togkontrolloer.adapters.TrainLineSpinnerAdapter;
import kea.togkontrolloer.helpers.MathHelp;
import kea.togkontrolloer.helpers.RequestHelp;
import kea.togkontrolloer.models.Station;
import kea.togkontrolloer.models.TrainLine;

public class MainSpotDownloadTask extends AsyncTask<Void, Integer, Boolean> {
	
	private MainSpotActivity activity;
	private boolean downloadTrainLines;
	private boolean downloadStations;
	private ProgressDialog pDialog;
	private ArrayList<TrainLine> fetchedLines;
	private ArrayList<Station> fetchedStations;
	private int fetchedUserId;
	
	public MainSpotDownloadTask(MainSpotActivity activity){
		this.activity = activity;
	}
	
	@Override
	protected Boolean doInBackground(Void... params) {
		
		boolean success = true;
		
		RequestHelp.setContext(activity);
		
		fetchedUserId = RequestHelp.getUserId();
		
		if(downloadTrainLines || downloadStations){
			fetchedLines = RequestHelp.getTrainLines(true);
			fetchedStations = RequestHelp.getStations();
		}
		publishProgress(100);
		
		return success;
	}
	
	@Override
	protected void onPreExecute(){
		super.onPreExecute();
		
		if(RequestHelp.isConnected() && (!RequestHelp.fileExists(RequestHelp.getFilenameTrainLines()) || MathHelp.getTimeDiff("now", RequestHelp.fileTimestamp(RequestHelp.getFilenameTrainLines())) >= 60 * 24 )){
			downloadTrainLines = true;
		}else{
			downloadTrainLines = false;
		}
		
		if(RequestHelp.isConnected() && (!RequestHelp.fileExists(RequestHelp.getFilenameStations()) || MathHelp.getTimeDiff("now", RequestHelp.fileTimestamp(RequestHelp.getFilenameStations())) >= 60 * 24 )){
			downloadStations = true;
		}else{
			downloadStations = false;
		}
		
		
		pDialog = new ProgressDialog(activity);
		
		pDialog.setMessage("Downloader Info");
		pDialog.setIndeterminate(false);
		pDialog.setMax(100);
		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		
		if(downloadTrainLines || downloadStations) pDialog.show();

	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		super.onProgressUpdate(progress);
		pDialog.setProgress(progress[0]);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		pDialog.dismiss();
		
		if(downloadTrainLines || downloadStations){
			activity.setStations(fetchedStations);
			activity.setTrainLines(fetchedLines);
		
			Spinner trainLineSpinner = (Spinner) activity.findViewById(kea.togkontrolloer.R.id.trainLinesSpinner);
			TrainLineSpinnerAdapter trainLineAdapter = new TrainLineSpinnerAdapter(activity, activity.getTrainLines());
			trainLineSpinner.setAdapter(trainLineAdapter);
			
			Spinner stationSpinner = (Spinner) activity.findViewById(kea.togkontrolloer.R.id.fromStationsSpinner);
        	StationSpinnerAdapter stationAdapter = new StationSpinnerAdapter(activity, activity.getStations());
        	stationSpinner.setAdapter(stationAdapter);
		}
		
	}
	
}
