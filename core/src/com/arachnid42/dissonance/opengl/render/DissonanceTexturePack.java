package com.arachnid42.dissonance.opengl.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.logging.FileHandler;

/**
 * Created by neketek on 07.07.15.
 */
public class DissonanceTexturePack implements Disposable{
    public static final  int PLAY_ICON = 0;
    public static final  int EXIT_ICON = 1;
    public static final  int SCORE_ICON = 2;
    public static final  int SETTINGS_ICON = 3;
    public static final  int SOUND_OFF_ICON = 4;
    public static final  int SOUND_ON_ICON = 5;
    public static final  int NO_ADS_ICON = 6;
    public static final  int TUTORIAL_ON_ICON = 7;
    public static final int TUTORIAL_OFF_ICON = 8;
    private ArrayList<Texture>textures = null;
    private void load(String filePath){
        FileHandle f = null;
        Texture texture = new Texture(Gdx.files.internal(filePath),true);
        texture.setFilter(Texture.TextureFilter.MipMap, Texture.TextureFilter.Nearest);
        textures.add(texture);
    }
    private void loadTexturePack(){
        textures = new ArrayList<Texture>();
        load("textures/play.png"); // 0
        load("textures/exit.png"); // 1
        load("textures/score.png"); // 2
        load("textures/settings.png"); // 3
        load("textures/soundOff.png"); // 4
        load("textures/soundOn.png"); // 5
        load("textures/noAds.png"); // 6
        load("textures/tutorialOn.png"); // 7
        load("textures/tutorialOff.png"); // 8
    }
    public DissonanceTexturePack(){
        loadTexturePack();
    }
    public Texture getTextureById(int textureId){
        return this.textures.get(textureId);
    }
    @Override
    public void dispose() {
        for(Texture texture:textures)
            texture.dispose();
    }
}
