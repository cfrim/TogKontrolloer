package kea.togkontrolloer.async;

import java.util.ArrayList;

import kea.togkontrolloer.activities.SpottingOverviewActivity;
import kea.togkontrolloer.helpers.MathHelp;
import kea.togkontrolloer.helpers.RequestHelp;
import kea.togkontrolloer.models.Spotting;
import kea.togkontrolloer.models.TrainLine;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class SpottingOverviewDownloadTask extends AsyncTask<Void, Integer, Boolean> {
	
	private SpottingOverviewActivity activity;
	private boolean downloadTrainLines;
	private boolean downloadSpottings;
	private ProgressDialog pDialog;
	private ArrayList<TrainLine> fetchedLines;
	private ArrayList<Spotting> fetchedSpottings;
	
	public SpottingOverviewDownloadTask(SpottingOverviewActivity activity){
		this.activity = activity;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		
		boolean success = true;
		
		RequestHelp.getUserId();
		
		if(downloadTrainLines) fetchedLines = RequestHelp.getTrainLines(true);
		publishProgress(50);
		if(downloadSpottings) fetchedSpottings = RequestHelp.getSpottings(true);
		publishProgress(50);
		
		return success;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		
		if(downloadTrainLines){
			activity.setTrainLines(fetchedLines);
		}
		
		if(downloadSpottings){
			activity.setSpottings(fetchedSpottings);
		}
		
		if(downloadTrainLines || downloadSpottings) activity.updateList();
		
		pDialog.dismiss();
		
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		RequestHelp.setContext(activity);
		
		if(RequestHelp.isConnected() && (!RequestHelp.fileExists(RequestHelp.getFilenameTrainLines()) || MathHelp.getTimeDiff("now", RequestHelp.fileTimestamp(RequestHelp.getFilenameTrainLines())) >= 60 * 24 )){
			downloadTrainLines = true;
		}else{
			downloadTrainLines = false;
		}
		
		Log.i("download trainlines", String.valueOf(downloadTrainLines));
		
		if(RequestHelp.isConnected() && (!RequestHelp.fileExists(RequestHelp.getFilenameSpottings()) || MathHelp.getTimeDiff("now", RequestHelp.fileTimestamp(RequestHelp.getFilenameSpottings())) >= 5 )){
			downloadSpottings = true;
		}else{
			downloadSpottings = false;
		}
		
		if(activity.isDoRefresh()){
			activity.setDoRefresh(false);
			downloadTrainLines = true;
			downloadSpottings = true;
		}
		
		Log.i("download trainlines", String.valueOf(downloadTrainLines));
		
		pDialog = new ProgressDialog(activity);
		
		pDialog.setMessage("Henter data");
		pDialog.setIndeterminate(false);
		pDialog.setMax(100);
		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		
		if(downloadTrainLines || downloadSpottings) pDialog.show();
		
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		super.onProgressUpdate(progress);
		pDialog.setProgress(progress[0]);
	}

	
	
}
