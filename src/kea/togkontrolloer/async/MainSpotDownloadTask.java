package kea.togkontrolloer.async;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Spinner;
import kea.togkontrolloer.activities.MainSpotActivity;
import kea.togkontrolloer.adapters.TrainLineSpinnerAdapter;
import kea.togkontrolloer.helpers.RequestHelp;
import kea.togkontrolloer.models.Station;
import kea.togkontrolloer.models.TrainLine;

public class MainSpotDownloadTask extends AsyncTask<Void, Integer, Boolean> {
	
	private MainSpotActivity activity;
	private ProgressDialog pDialog;
	private ArrayList<TrainLine> fetchedLines;
	private ArrayList<Station> fetchedStations;
	
	public MainSpotDownloadTask(MainSpotActivity activity){
		this.activity = activity;
	}
	
	@Override
	protected Boolean doInBackground(Void... params) {
		
		boolean success = true;
		
		RequestHelp.setContext(activity);
		
		fetchedLines = RequestHelp.getTrainLines();
		publishProgress(50);
		fetchedStations = RequestHelp.getStations();
		publishProgress(50);
		
		return false;
	}
	
	@Override
	protected void onPreExecute(){
		super.onPreExecute();
		
		pDialog = new ProgressDialog(activity);
		
		pDialog.setMessage("Downloader Info");
		pDialog.setIndeterminate(false);
		pDialog.setMax(100);
		pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pDialog.show();

	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		super.onProgressUpdate(progress);
		pDialog.setProgress(progress[0]);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		pDialog.hide();
		
		activity.setStations(fetchedStations);
		activity.setTrainLines(fetchedLines);
		
		Spinner trainLineSpinner = (Spinner) activity.findViewById(kea.togkontrolloer.R.id.trainLinesSpinner);
		TrainLineSpinnerAdapter trainLineAdapter = new TrainLineSpinnerAdapter(activity, activity.getTrainLines());
		trainLineSpinner.setAdapter(trainLineAdapter);
		// bob
		
	}
	
}
