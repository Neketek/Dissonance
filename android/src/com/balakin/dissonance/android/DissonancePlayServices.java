package com.balakin.dissonance.android;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.balakin.dissonance.Dissonance;
import com.balakin.dissonance.logic.GameStageData;
import com.balakin.dissonance.utils.DissonanceConfig;
import com.balakin.dissonance.utils.DissonanceResources;
import com.balakin.dissonance.utils.DissonanceState;
import com.balakin.dissonance.utils.androidControllers.DissonancePSController;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.games.leaderboard.Leaderboards;

/**
 * Created by neketek on 21.07.15.
 */
public class DissonancePlayServices implements
        DissonancePSController,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        DialogInterface.OnCancelListener
{
    public static final int THAT_IS_YOU_EAGLE_EYE = 0;
    public static final int THIS_WEIRD_SHAPES = 1;
    public static final int THIS_WEIRD_COLORS = 2;
    public static final int YOU_ARE_TOUGH = 3;
    public static final int YOU_ARE_PLAYING_BETTER_THAN_CREATOR = 5;
    private GoogleApiClient.Builder builder = null;
    private GoogleApiClient googleApiClient = null;
    private Activity gameActivity = null;
    private boolean connectionActive = false;
    private Intent leaderBoardIntent = null;
    private boolean appClosed = false;
    private String leaderBoardId = null;
    private boolean scoreSynchronized = false;
    private boolean isPlayServicesAvailable = false;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(googleApiClient==null){
                if(!googleApiClient.isConnected()&&!googleApiClient.isConnecting()){
                    connectConnectToGoogleApiClient();
                }
            }
            try {
                launchLeaderBoardByIntent();
            }catch(Exception e){
                connectConnectToGoogleApiClient();
            }
        }
    };
    private boolean internetConnection;

    private void loadLeaderBoardScore(){
        scoreSynchronized = true;
        Games.Leaderboards.loadCurrentPlayerLeaderboardScore(googleApiClient,
                leaderBoardId,
                LeaderboardVariant.TIME_SPAN_ALL_TIME,
                LeaderboardVariant.COLLECTION_PUBLIC).setResultCallback(new ResultCallback<Leaderboards.LoadPlayerScoreResult>() {
            @Override
            public void onResult(Leaderboards.LoadPlayerScoreResult loadPlayerScoreResult) {
                if (loadPlayerScoreResult != null && loadPlayerScoreResult.getScore() != null) {
                    if (loadPlayerScoreResult.getScore().getRawScore() > DissonanceConfig.maxScore) {
                        DissonanceConfig.maxScore = (int) loadPlayerScoreResult.getScore().getRawScore();
                    }
                }
            }
        });

    }
    private void launchLeaderBoardByIntent(){
        leaderBoardIntent = Games.Leaderboards.getLeaderboardIntent(googleApiClient,leaderBoardId);
        gameActivity.startActivityForResult(leaderBoardIntent, 1);
    }
    private void createLeaderBoardIntent(){
        if(leaderBoardIntent!=null)
            return;
        leaderBoardIntent = Games.Leaderboards.getLeaderboardIntent(googleApiClient,gameActivity.getString(R.string.leaderboard_dissonance));
    }
    private Dialog createErrorDialog(int errorCode){
        return  GooglePlayServicesUtil.getErrorDialog(errorCode,
                gameActivity, 0, this);
    }
    private boolean isGoogleApiClientCreationRequired(){
       return this.googleApiClient == null;
    }
    private void createGoogleApiClientBuilder(){
        this.builder = new GoogleApiClient.Builder(gameActivity);
    }
    private void configureGoogleApiClientBuilder(){
      //  this.builder.addScope(Plus.SCOPE_PLUS_LOGIN);
        this.builder.addApi(Games.API);
        this.builder.addScope(Games.SCOPE_GAMES);
        this.builder.addConnectionCallbacks(this);
        this.builder.addOnConnectionFailedListener(this);
    }
    private void createGoogleApiClient(){
        this.googleApiClient = this.builder.build();
    }
    private void connectConnectToGoogleApiClient(){
        if(internetConnection)
            this.googleApiClient.connect();
        else {
            checkInternetConnection();
            if(internetConnection)
                this.googleApiClient.connect();
        }
    }
    private String getAchievementStringId(int id){
        String s = gameActivity.getString(id);
       /// System.out.println("ACHIEVEMENT:"+s);
        return s;
    }
    public void tryToConnectToGooglePlayServices(){
        if(isGoogleApiClientCreationRequired()){
            createGoogleApiClientBuilder();
            configureGoogleApiClientBuilder();
            createGoogleApiClient();
        }
        if(!googleApiClient.isConnected()&&!googleApiClient.isConnecting())
            connectConnectToGoogleApiClient();
    }
    public DissonancePlayServices(Activity gameActivity){
        this.gameActivity = gameActivity;
        this.leaderBoardId = gameActivity.getString(R.string.leaderboard_dissonance);
        this.checkPlayServices();
    }
    @Override
    public void onConnected(Bundle bundle) {
        connectionActive = true;
        createLeaderBoardIntent();
        //TODO:disable system out
       //System.out.println("PLAY SERVICES CONNECTED");
    }

    @Override
    public void onConnectionSuspended(int i) {
      // System.out.println("PLAY SERVICES SUSPEND");
        connectionActive = false;
        tryToConnectToGooglePlayServices();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        connectionActive = false;
            try {
                if(!appClosed&&connectionResult.getErrorCode()==ConnectionResult.SIGN_IN_REQUIRED) {
                    connectionResult.startResolutionForResult(gameActivity, 9000);
                }else
                    return;
            } catch (IntentSender.SendIntentException e) {
                return;
            }
        //TODO:  а надо ли это?
    }
    @Override
    public void onCancel(DialogInterface dialog) {
        tryToConnectToGooglePlayServices();
    }
    public void disconnectGoogleApiClient(){
        appClosed = true;
        if(googleApiClient!=null&&(googleApiClient.isConnected()||googleApiClient.isConnecting())){
            googleApiClient.disconnect();
            googleApiClient = null;
            builder = null;
            return;
        }
    }

    @Override
    public void showLeaderBoard() {
        if(!isPlayServicesAvailable)
            return;
        this.handler.sendEmptyMessage(0);
    }

    @Override
    public void submitPlayerScore(int value) {
       if(!isPlayServicesAvailable)
           return;
        if(googleApiClient!=null&&googleApiClient.isConnected())
             Games.Leaderboards.submitScore(googleApiClient,leaderBoardId,value);
    }
    public boolean isAppClosed() {
        return appClosed;
    }
    @Override
    public void manageAchievements(){
        if(googleApiClient==null||!googleApiClient.isConnected())
            return;
      //  System.out.println("ACHIEVEMENT SYSTEM STARTS MANAGEMENT");
        DissonanceState state = DissonanceResources.getDissonanceState();
        GameStageData gameStageData = DissonanceResources.getDissonanceLogic().getGameStageData();
        if(DissonanceConfig.maxScore>=70)
            Games.Achievements.unlock(
                    googleApiClient,getAchievementStringId(R.string.achievement_you_are_tough));
        if(DissonanceConfig.maxScore>=40)
            Games.Achievements.unlock(
                    googleApiClient,getAchievementStringId(R.string.achievement_you_are_playing_better_than_creator));
        if(!state.isGameFailed())
            return;
        if(gameStageData.getScore()<10)
            Games.Achievements.increment(
                    googleApiClient, getAchievementStringId(R.string.achievement_that_is_you_eagle_eye),1);
        if(gameStageData.getGameMode()==GameStageData.COLOR_COMPARISON)
            Games.Achievements.increment(
                googleApiClient,getAchievementStringId(R.string.achievement_this_weird_colors),1);
        if(gameStageData.getGameMode()==GameStageData.SHAPE_COMPARISON)
            Games.Achievements.increment(
                    googleApiClient,getAchievementStringId(R.string.achievement_this_weird_shapes),1);
        Games.Achievements.increment(
                googleApiClient,getAchievementStringId(R.string.achievement_10_games_),1);
        Games.Achievements.increment(
                googleApiClient,getAchievementStringId(R.string.achievement_50_games_),1);
        Games.Achievements.increment(
                googleApiClient,getAchievementStringId(R.string.achievement_100_games_),1);

    }
    @Override
    public void synchronizeScoreWithLeaderBoard(){
        if(!isPlayServicesAvailable)
            return;
        if(googleApiClient!=null) {
            if(googleApiClient.isConnected()){
                if (!scoreSynchronized)
                    this.loadLeaderBoardScore();
            }else{
                checkInternetConnection();
                if(internetConnection) {
                    tryToConnectToGooglePlayServices();
                    this.loadLeaderBoardScore();
                }
            }
        }
    }
    public void checkPlayServices(){
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(gameActivity);
        isPlayServicesAvailable = resultCode==ConnectionResult.SUCCESS;
    }
    public void checkInternetConnection(){
        internetConnection = true;
        return;
//        ConnectivityManager connectivityManager =
//                (ConnectivityManager)gameActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo active = connectivityManager.getActiveNetworkInfo();
//        if(active!=null){
//            if(active.isAvailable()&&active.isConnectedOrConnecting()){
//                internetConnection = true;
//            }else{
//                internetConnection = false;
//            }
//        }else{
//            internetConnection = false;
//        }
    }
}
