package com.arachnid42.dissonance.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;

/**
 * Created by neketek on 16.07.15.
 */
public class DissonanceSound {
    public static final int BUTTON = 0;
    public static final int SHAPE = 1;
    public static final int GAME_MODE = 2;
    private ArrayList<Sound> sounds = null;
    private void loadSound(String filename){
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/"+filename));
        sounds.add(sound);
    }
    private void loadSounds(){
        this.sounds = new ArrayList<Sound>();
        loadSound("button.mp3");
        loadSound("shape.mp3");
        loadSound("gameMode.mp3");
    }
    public DissonanceSound(){
        loadSounds();
    }
    public void play(int id){
        if(DissonanceConfig.soundEnabled)
            sounds.get(id).play(1f);
    }
}
