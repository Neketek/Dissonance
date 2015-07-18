package com.arachnid42.dissonance.android;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.arachnid42.dissonance.utils.DissonanceAdsController;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by neketek on 14.07.15.
 */
public class DissonanceAds implements DissonanceAdsController{
    private static final int SHOW_ADS_RATE = 3;
    private InterstitialAd interstitialAd = null;
    private AdRequest.Builder builder = null;
    private AdListener addListener = null;
    private AdRequest adRequest = null;
    private Handler adsControlHandler = null;
    private int counter = 0;
    private void createAdsControlHandler(){
        adsControlHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                interstitialAd.show();
            }
        };
    }
    private void loadAdd(){
        interstitialAd.loadAd(adRequest);
    }
    private void createAddListener(){
        addListener = new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                loadAdd();
            }
        };
    }
    private void createAdRequest(){
        builder.addTestDevice("32341DECEF412FA50C26D39113288952");//TODO:REMOVE TEST DEVICE
        builder.setGender(AdRequest.GENDER_MALE);
        adRequest = builder.build();
    }
    private void createBuilder(){
        this.builder = new AdRequest.Builder();
    }
    private void createInterstitialAd(Context context,String adUnitId){
        this.interstitialAd = new InterstitialAd(context);
        this.interstitialAd.setAdUnitId(adUnitId);
        this.interstitialAd.setAdListener(addListener);
    }
    public DissonanceAds(Context context,String adUnitId){
        createBuilder();
        createAdRequest();
        createAddListener();
        createInterstitialAd(context, adUnitId);
        createAdsControlHandler();
        loadAdd();
    }
    @Override
    public void showAds() {
        if(counter>0&&counter%SHOW_ADS_RATE==0)
            this.adsControlHandler.sendEmptyMessage(0);
        counter++;
    }

    @Override
    public boolean isAdsAvailable() {
        return this.interstitialAd.isLoaded();
    }
}
