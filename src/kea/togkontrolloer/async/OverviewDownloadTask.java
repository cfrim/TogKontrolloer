package kea.togkontrolloer.async;

import java.util.ArrayList;

import kea.togkontrolloer.activities.OverviewActivity;
import kea.togkontrolloer.helpers.MathHelp;
import kea.togkontrolloer.helpers.RequestHelp;
import kea.togkontrolloer.models.Spotting;
import kea.togkontrolloer.models.TrainLine;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class OverviewDownloadTask extends AsyncTask<Void, Integer, Boolean> {
	
	private OverviewActivity activity;
	private boolean downloadTrainLines;
	private boolean downloadSpottings;
	private ProgressDialog pDialog;
	private ArrayList<TrainLine> fetchedLines;
	private ArrayList<Spotting> fetchedSpottings;
	
	public OverviewDownloadTask(OverviewActivity activity){
		this.activity = activity;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		// Prepare RequestHelp
		RequestHelp.setContext(activity);
		
		// Check if we can and should download trainlines
		if(RequestHelp.isConnected() && (!RequestHelp.fileExists(RequestHelp.getFilenameTrainLines()) || MathHelp.getTimeDiff("now", RequestHelp.fileTimestamp(RequestHelp.getFilenameTrainLines())) >= 60 * 24 )){
			downloadTrainLines = true;
		}else{
			downloadTrainLines = false;
		}
		
		// Check if we can and should download spottings
		if(RequestHelp.isConnected() && (!RequestHelp.fileExists(RequestHelp.getFilenameSpottings()) || MathHelp.getTimeDiff("now", RequestHelp.fileTimestamp(RequestHelp.getFilenameSpottings())) >= 5 )){
			downloadSpottings = true;
		}else{
			downloadSpottings = false;
		}
		
		// Create a progress dialog
		pDialog = new ProgressDialog(activity);
		
		pDialog.setMessage("Henter data");
		pDialog.setIndeterminate(false);
		pDialog.setMax(100);
		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		
		// If we're downloading, inform the user by showing the progress dialog.
		if(downloadTrainLines || downloadSpottings) pDialog.show();
		
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		
		boolean success = true;
		
		// Setup the user as soon as possible.
		RequestHelp.getUserId();
		
		// Download if we need to download
		
		if(downloadTrainLines) fetchedLines = RequestHelp.getTrainLines(true);
		publishProgress(50);
		
		if(downloadSpottings) fetchedSpottings = RequestHelp.getSpottings(true);
		publishProgress(50);
		
		return success;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		
		// If we've downloaded anything, transfer it to the activity and 
		// update the list in the activity.
		
		if(downloadTrainLines){
			activity.setTrainLines(fetchedLines);
		}
		
		if(downloadSpottings){
			activity.setSpottings(fetchedSpottings);
		}
		
		if(downloadTrainLines || downloadSpottings) activity.updateList();
		
		// Remove the dialog so the user can resume using the app.
		pDialog.dismiss();
		
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		super.onProgressUpdate(progress);
		pDialog.setProgress(progress[0]);
	}

	
	
}
