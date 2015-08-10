package com.balakin.dissonance.android;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.*;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
//import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.balakin.dissonance.Dissonance;
import com.balakin.dissonance.utils.DissonanceConfig;

import org.json.JSONException;
import org.json.JSONObject;


public class AndroidLauncher extends AndroidApplication{
	private static final String ADD_UNIT_ID = "ca-app-pub-6930479996498829/4009222396";
	private DissonanceAds dissonanceAds = null;
	private DissonancePlayServices dissonancePlayServices = null;
	private void createDissonancePlayServices(){
		this.dissonancePlayServices = new DissonancePlayServices(this);
	}
	private void createGameView(){
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.numSamples = 4;
		dissonanceAds = new DissonanceAds(this,ADD_UNIT_ID);
		initialize(new Dissonance(dissonanceAds, dissonancePlayServices), config);
	}
	private void initFullScreenMode(){
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
	}
	private  void resumeFullScreenMode(){
		getWindow().getDecorView()
				.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
	}
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		//System.out.println("ASDAFSASF CREATE");
		super.onCreate(savedInstanceState);
		initFullScreenMode();
		createDissonancePlayServices();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		createGameView();
		dissonancePlayServices.tryToConnectToGooglePlayServices();
	}
	@Override
	protected void onResume() {
		super.onResume();
		resumeFullScreenMode();
	}
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		super.onKeyUp(keyCode, event);
		resumeFullScreenMode();
		return false;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		super.onKeyDown(keyCode, event);
		resumeFullScreenMode();
		return false;
	}
	@Override
	protected void onDestroy(){
		super.onDestroy();
		this.dissonancePlayServices.disconnectGoogleApiClient();
		this.dissonanceAds.unbindService(this);
	}
	@Override
	protected  void onStop(){
		super.onStop();
	}
	@Override
	protected void onActivityResult(int reqCode,int resCode,Intent data){
		//TODO:complete ads purchase security!!!
		super.onActivityResult(reqCode,resCode,data);
		if (reqCode == 1001) {
			if(resCode==RESULT_OK){
				DissonanceConfig.adsEnabled = false;
			}
		}
	}

}
