package com.balakin.dissonance.android;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

import com.android.vending.billing.IInAppBillingService;
import com.balakin.dissonance.utils.DissonanceConfig;
import com.balakin.dissonance.utils.DissonanceResources;
import com.balakin.dissonance.utils.DissonanceState;
import com.balakin.dissonance.utils.androidControllers.DissonanceAdsController;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

/**
 * Created by neketek on 14.07.15.
 */
public class DissonanceAds implements DissonanceAdsController{
    private static final String COM_ANDROID_VENDING = "com.android.vending";
    private static final String SKU = "no_ads";
    private static final String TYPE = "inapp";
    private Activity gameActivity;
    private String packageName = null;
    private IInAppBillingService inAppBillingService = null;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            inAppBillingService = IInAppBillingService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceConnection = null;
        }
    };
    private static final int SHOW_ADS_RATE = 4;
    private InterstitialAd interstitialAd = null;
    private AdRequest.Builder builder = null;
    private AdListener addListener = null;
    private AdRequest adRequest = null;
    private Handler adsControlHandler = null;
    private int counter = 0;
    private boolean adsLoaded;

    private void buyDisablingAds(){
        if(serviceConnection==null)
            return;
        try {
            Bundle buyBundle = inAppBillingService.getBuyIntent(3, packageName, SKU, TYPE, "000000");
            PendingIntent pendingIntent = buyBundle.getParcelable("BUY_INTENT");
            gameActivity.startIntentSenderForResult(pendingIntent.getIntentSender(),1001,new Intent(),0,0,0);
        } catch (RemoteException e) {
            System.out.println("BUY SERVICE EXCEPTION REMOTE");
        } catch (IntentSender.SendIntentException e) {
            System.out.println("BUY SERVICE EXCEPTION SEND");
        }
    }
    private void checkAdsEnabled(){
        try {
            Bundle ownedItems = inAppBillingService.getPurchases(3, packageName, TYPE, null);
            int response = ownedItems.getInt("RESPONSE_CODE");
            if(response==0){
                ArrayList<String> ownedSkus =
                        ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                if(ownedItems.containsKey(SKU))
                    DissonanceConfig.adsEnabled=false;
                else
                    DissonanceConfig.adsEnabled = true;
            }
        } catch (RemoteException e) {
            if(!DissonanceConfig.adsEnabled)
                return;
            else
                DissonanceConfig.adsEnabled = true;
        }

    }
    private void createAdsControlHandler(){
        adsControlHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                switch(msg.what){
                    case 0:
                        showAdvertising();
                        break;
                    case 1:
                        buyDisablingAds();
                        break;
                    case 2:
                        checkAdsEnabled();
                        break;
                    case 3:
                        loadAdd();
                        break;
                }
            }
        };
    }
    private void showAdvertising(){
        interstitialAd.show();
    }
    private void loadAdd(){
        interstitialAd.loadAd(adRequest);
    }
    private void createAddListener(){
        addListener = new AdListener() {
            @Override
            public void onAdClosed() {
                adsLoaded=false;
                super.onAdClosed();
                loadAdd();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adsLoaded = true;
               // System.out.println("ADS LOAD");
            }
        };
    }
    private void createAdRequest(){
    //    builder.addTestDevice("32341DECEF412FA50C26D39113288952");//TODO:REMOVE TEST DEVICE
       // builder.setGender(AdRequest.GENDER_MALE);
        builder.tagForChildDirectedTreatment(true);
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
    private void bindBillingService(Activity activity){
        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage(COM_ANDROID_VENDING);
        activity.bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        packageName = activity.getPackageName();
    }
    public DissonanceAds(Activity context,String adUnitId){
        createBuilder();
        createAdRequest();
        createAddListener();
        createInterstitialAd(context, adUnitId);
        createAdsControlHandler();
        loadAdd();
        bindBillingService(context);
        gameActivity = context;
    }
    public void unbindService(Activity activity){
        activity.unbindService(serviceConnection);
    }
    @Override
    public boolean showAds() {
        if((counter>0&&counter>=SHOW_ADS_RATE)){
            if(adsLoaded)
                adsControlHandler.sendEmptyMessage(0);
            else {
                adsControlHandler.sendEmptyMessage(3);
                return  false;
            }
            counter = 0;
            return true;
        }
        if(DissonanceResources.getDissonanceState().isGameFailed()
                &&DissonanceResources.getDissonanceLogic().getGameStageData().getScore()>5){
            counter+=2;

        }else {
            counter++;
        }
        return false;
    }
    @Override
    public boolean isAdsAvailable() {
        return this.interstitialAd.isLoaded();
    }
    @Override
    public void purchase() {
        adsControlHandler.sendEmptyMessage(1);
    }
    @Override
    public void checkAdsStatus(){
        adsControlHandler.sendEmptyMessage(2);
    }
}
