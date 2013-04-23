package kea.togkontrolloer.async;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import kea.togkontrolloer.activities.MainSpotActivity;
import kea.togkontrolloer.helpers.RequestHelp;

public class MainSpotPostTask extends AsyncTask<Void, Integer, Boolean> {
	
	private int userId;
	private int trainLineId;
	private int fromStationId;
	private int toStationId;
	private MainSpotActivity activity;
	public boolean doPost = false;
	private ProgressDialog pDialog;
	
	

	public MainSpotPostTask(MainSpotActivity activity, int trainLineId, int fromStationId, int toStationId){
		this.activity = activity;
		this.trainLineId = trainLineId;
		this.fromStationId = fromStationId;
		this.toStationId = toStationId;
	}
	

	@Override
	protected Boolean doInBackground(Void... params) {
		// TODO Auto-generated method stub
		boolean success = true;
		RequestHelp.setContext(activity);
		if(doPost){
			if(trainLineId == 0 && toStationId == 0){
				try{
					RequestHelp.postSpotting("http://cfrimodt.dk/test/ticket-dodger/?do=setSpottings&sec=314bf797090f40e9cbf54909b4814a4c1679cf4c2aae390559c15248a0055c12&userId="+this.userId+"&fromStationId="+this.fromStationId);
				}
				catch(Exception e){
					Log.e("Posting spotting", "Could not post station spotting");
				}
			}
			else{
				try{
					RequestHelp.postSpotting("http://cfrimodt.dk/test/ticket-dodger/?do=setSpottings&sec=314bf797090f40e9cbf54909b4814a4c1679cf4c2aae390559c15248a0055c12&userId="+this.userId+"&fromStationId="+this.fromStationId+"&toStationId="+this.toStationId+"&lineId="+this.trainLineId);

				}
				catch(Exception e){
					Log.e("Posting spotting", "Could not post train spotting");
				}
			}
		}
	
		publishProgress(100);
		return success;
	}


	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
		RequestHelp.setContext(activity);
		this.userId = RequestHelp.getUserId();
		if(RequestHelp.isConnected() && userId != 0){
			doPost = true;
			pDialog = new ProgressDialog(activity);
			
			pDialog.setMessage("Sender spotting");
			pDialog.setIndeterminate(false);
			pDialog.setMax(100);
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.show();
			
		}
		
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		pDialog.dismiss();
		Toast successToast = Toast.makeText(this.activity, "Spotting blev sendt", Toast.LENGTH_SHORT);
		successToast.show();
	}


	@Override
	protected void onProgressUpdate(Integer... progress) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(progress);
		pDialog.setProgress(progress[0]);
	}
	
	
}
