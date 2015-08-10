package com.balakin.dissonance.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by neketek on 16.07.15.
 */
public class DissonanceSound {
    private static final String[] BUTTON_SAMPLES_NAMES = {"button_sample_1.mp3","button_sample_2.mp3","button_sample_3.mp3"};
    private static final String[] SHAPE_FALL_SAMPLES_NAMES = {"shape_fall_sample_1.mp3","shape_fall_sample_2.mp3","shape_fall_sample_3.mp3"};
    private static final String[] GAME_FAIL_SAMPLES_NAMES = {"game_failed_sample_1.mp3","game_failed_sample_2.mp3","game_failed_sample_3.mp3"};
    private static final String[] GAME_MODE_SAMPLES_NAMES = {"game_mode_sample_1.mp3","game_mode_sample_2.mp3","game_mode_sample_3.mp3"};
    public static final int BUTTON = 0;
    public static final int SHAPE = 1;
    public static final int GAME_MODE = 2;
    public static final int GAME_FAILED = 3;
    private ArrayList<ArrayList<Sound>> soundLists = null;
    private Random random = null;
    private Sound loadSound(String filename){
        return Gdx.audio.newSound(Gdx.files.internal("sounds/"+filename));
    }
    private ArrayList<Sound> loadSoundArray(String[]names){
        ArrayList<Sound>sounds = new ArrayList<Sound>();
        for(String name:names){
            sounds.add(loadSound(name));
        }
        return sounds;
    }
    private void loadSounds(){
        this.soundLists = new ArrayList<ArrayList<Sound>>();
        soundLists.add(loadSoundArray(BUTTON_SAMPLES_NAMES));
        soundLists.add(loadSoundArray(SHAPE_FALL_SAMPLES_NAMES));
        soundLists.add(loadSoundArray(GAME_MODE_SAMPLES_NAMES));
        soundLists.add(loadSoundArray(GAME_FAIL_SAMPLES_NAMES));
    }
    private void playSoundArray(int id){
        ArrayList<Sound> sounds = soundLists.get(id);
        sounds.get(random.nextInt(sounds.size())).play(1);
    }
    public DissonanceSound(){
        loadSounds();
        random = new Random();
    }
    public void play(int id){
        if(DissonanceConfig.soundEnabled)
            playSoundArray(id);
    }
}
