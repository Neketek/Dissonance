package com.arachnid42.dissonance.android;
import android.app.ActionBar;
import android.bluetooth.le.AdvertiseSettings;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;
//import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.arachnid42.dissonance.Dissonance;
import com.google.android.gms.maps.internal.k;


public class AndroidLauncher extends AndroidApplication{
	private static final String ADD_UNIT_ID = "ca-app-pub-6930479996498829/8429199192"; //TODO:DON'T FORGET TO USE MY OWN ID
	private DissonanceAds dissonanceAds = null;
	private View gameView = null;
	private RelativeLayout layout = null;
	private Animation fadeIn = null;
	private Animation fadeOut = null;
	private void loadAnimations(){
		this.fadeIn = AnimationUtils.loadAnimation(this,R.anim.fade_in);
		this.fadeOut = AnimationUtils.loadAnimation(this,R.anim.fade_out);
	}
	private void createGameView(){
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.numSamples = 4;
		dissonanceAds = new DissonanceAds(this,ADD_UNIT_ID);
		gameView = initializeForView(new Dissonance(dissonanceAds),config);
	}
	private void createLayout(){
		layout = new RelativeLayout(this);
		layout.setPadding(0, 0, 0, 0);
		layout.addView(gameView);
		setContentView(layout);
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
		super.onCreate(savedInstanceState);
		initFullScreenMode();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		createGameView();
		createLayout();
	}

	@Override
	protected void onResume() {
		super.onResume();
		resumeFullScreenMode();
	}
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		super.onKeyUp(keyCode,event);
		resumeFullScreenMode();
		return false;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		super.onKeyDown(keyCode,event);
		resumeFullScreenMode();
		return false;
	}

}
