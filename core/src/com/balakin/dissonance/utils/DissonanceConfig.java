package com.balakin.dissonance.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.StringTokenizer;

/**
 * Created by neketek on 07.07.15.
 */
public class DissonanceConfig {
    private static final boolean OVERWRITE = false; // because append should be false for overwriting
    private static final String SETTINGS_FILE_NAME = "settings.cfg";
    private static final String NEW_LINE = "\n";
    private static final String SOUND_OPTION = "sound:";
    private static final String ADDS_OPTION = "ads:";
    private static final String MAX_SCORE = "score:";
    private static final String TUTORIAL = "tutorial:";
    private static boolean loadAdsStatus(){
        return true;
    }
    private static void loadDefaults(){
        maxScore = 0;
        adsEnabled = true;
        soundEnabled = true;
    }

    private static boolean isSettingsFileEmpty(FileHandle settings){
        return settings.length()==0;
    }
    private static FileHandle getSettingsFile(){
        return Gdx.files.local(SETTINGS_FILE_NAME);
    }
    private static void readConfig(String config){
        StringTokenizer tokenizer = new StringTokenizer(config,"\n: ");
        tokenizer.nextToken();//go to sound value
        soundEnabled = Boolean.parseBoolean(tokenizer.nextToken());
        tokenizer.nextToken();//go to ads value
        adsEnabled = Boolean.parseBoolean(tokenizer.nextToken());
        tokenizer.nextToken();//go to max score value
        maxScore = Integer.parseInt(tokenizer.nextToken());
        tokenizer.nextToken();
        tutorialEnabled = Boolean.parseBoolean(tokenizer.nextToken());
    }
    private static boolean isLocalStorageAvailable(){
        return Gdx.files.isLocalStorageAvailable();
    }
    public static void saveConfig(){
        FileHandle settings = getSettingsFile();
        String soundOption = SOUND_OPTION+soundEnabled+NEW_LINE;
        String adsOption = ADDS_OPTION+adsEnabled+NEW_LINE;
        String maxScoreOption = MAX_SCORE+maxScore+NEW_LINE;
        String tutorialOption = TUTORIAL+tutorialEnabled+NEW_LINE;
        String settingsFileInfo = soundOption+adsOption+maxScoreOption+tutorialOption;
        settings.writeString(settingsFileInfo,OVERWRITE);
    }
    public static void initConfig(){
        FileHandle settings = getSettingsFile();
        if(!isSettingsFileEmpty(settings)) {
            try {
                readConfig(settings.readString());
            }catch(Exception e){
                loadDefaults();
            }
        }else{
            loadDefaults();
        }
    }
    public static boolean soundEnabled = true;
    public static boolean adsEnabled = true;
    public static int maxScore = 0;
    public static boolean tutorialEnabled = true;
}
