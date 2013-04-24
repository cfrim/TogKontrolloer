package kea.togkontrolloer.activities;

import kea.togkontrolloer.R;
import kea.togkontrolloer.R.layout;
import android.content.Intent;
import kea.togkontrolloer.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;

public class SplashActivity extends Activity {

	private long ms=0;
	private long splashTime=2000;
	private boolean splashActive = true;
	private boolean paused=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	//Hides the titlebar
	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.splash);
	Thread mythread = new Thread() {
	public void run() {
		try {
			while (splashActive && ms < splashTime) {
			if(!paused)
			ms=ms+100;
			sleep(100);
			}
		} catch(Exception e) {}
	finally {
	Intent intent = new Intent(SplashActivity.this, MainSpotActivity.class);
	startActivity(intent);
	}
	}
	};
		mythread.start();
	}
	}