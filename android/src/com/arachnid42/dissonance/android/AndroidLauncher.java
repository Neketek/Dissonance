package com.arachnid42.dissonance.android;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.arachnid42.dissonance.Dissonance;

public class AndroidLauncher extends AndroidApplication {
	private void initFullScreenMode(){
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
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
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.numSamples = 4;
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		initialize(new Dissonance(), config);
	}

	@Override
	protected void onResume() {
		super.onResume();
		resumeFullScreenMode();
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		return super.onMenuOpened(featureId, menu);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		System.out.println("SEARCH"); // Важное место, обработка некоторых событий должна происходить тут
		// 19 API  убирает fullscreen, если изменить звук!
		resumeFullScreenMode();
		//super.onKeyUp(keyCode, event);
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		System.out.println("SEARCH"); // Важное место, обработка некоторых событий должна происходить тут
		// 19 API  убирает fullscreen, если изменить звук!
		resumeFullScreenMode();
		//super.onKeyUp(keyCode, event);
		return true;
	}

}
