package kea.togkontrolloer.async;

import java.util.ArrayList;

import com.google.gson.JsonArray;

import kea.togkontrolloer.activities.FavoriteActivity;
import kea.togkontrolloer.helpers.RequestHelp;
import kea.togkontrolloer.models.Request;
import kea.togkontrolloer.models.TrainLine;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class FavoritesTask extends AsyncTask<Void, Integer, Boolean> {
	FavoriteActivity activity;
	ArrayList<TrainLine> fetchedFavoriteTrainLines;
	private ProgressDialog pDialog;
	public FavoritesTask(FavoriteActivity activity){
		this.activity = activity;
	}
	
	@Override
	protected Boolean doInBackground(Void... arg0) {
		
		boolean success = true;

		RequestHelp.setContext(activity);
		
		fetchedFavoriteTrainLines = RequestHelp.getFavorites();
		Log.i("favorites task", "got favorites");
		
		return success;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		activity.setFavoriteTrainLines(fetchedFavoriteTrainLines);
		pDialog.dismiss();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pDialog = new ProgressDialog(activity);
		
		pDialog.setMessage("Henter dine favoritter");
		pDialog.setIndeterminate(false);
		pDialog.setMax(100);
		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pDialog.show();
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(progress);
		pDialog.setProgress(progress[0]);
	}

}
