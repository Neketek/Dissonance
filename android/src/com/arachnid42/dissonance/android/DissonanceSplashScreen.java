package com.arachnid42.dissonance.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by neketek on 18.07.15.
 */
public class DissonanceSplashScreen extends Activity implements Animation.AnimationListener{
    private static final long SPLASH_TIMER = 1000;
    private ImageView splashImage = null;
    private Animation fadeIn = null;
    private Animation fadeOut = null;
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
    protected  void onCreate(Bundle saved){
        super.onCreate(saved);
        initFullScreenMode();
        setContentView(R.layout.splash);
        splashImage = (ImageView)findViewById(R.id.imageLogo);
        fadeIn = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(this,R.anim.fade_out);
        fadeIn.setAnimationListener(this);
        fadeOut.setAnimationListener(this);
        splashImage.startAnimation(fadeIn);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }
    @Override
    public void onAnimationEnd(Animation animation) {
        if(animation!=fadeOut) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    splashImage.startAnimation(fadeOut);
                }
            }, SPLASH_TIMER);
            return;
        }
        splashImage.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(DissonanceSplashScreen.this,AndroidLauncher.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
