/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.io.File;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.ImagePattern;
import javafx.scene.media.Media;

/**
 *
 * @author Max
 */
public class AssetManager {
    
    static ArrayList<ImagePattern> aliens = new ArrayList<>();
    static private ImagePattern spaceship = null;
    static private ImagePattern explosion = null;
    
    static private Media backgroundMusic = null;
    static private AudioClip shootingSound = null;
    static private AudioClip deathSound = null;
    static private AudioClip alienDeathSound = null;
    
    
    static private String fileURL(String relativePath){
        
        return new File(relativePath).toURI().toString();
    }
    
    static public void preloadAllAssets(){
        explosion = new ImagePattern(new Image(fileURL("./assets/images/explosion.png")));
        aliens.add(new ImagePattern(new Image(fileURL("./assets/images/frontAliensRest.png"))));//0
        aliens.add(new ImagePattern(new Image(fileURL("./assets/images/middleAliensRest.png"))));//1
        aliens.add(new ImagePattern(new Image(fileURL("./assets/images/backAliensRest.png"))));//2
        aliens.add(new ImagePattern(new Image(fileURL("./assets/images/frontAliensMoving.png"))));//3
        aliens.add(new ImagePattern(new Image(fileURL("./assets/images/middleAliensMoving.png"))));//4
        aliens.add(new ImagePattern(new Image(fileURL("./assets/images/backAliensMoving.png"))));//5
        spaceship = (new ImagePattern(new Image(fileURL("./assets/images/spaceship.png"))));
        
        backgroundMusic = new Media(fileURL("./assets/music/Space_Invaders.mp3"));
        
        shootingSound = new AudioClip(fileURL("./assets/soundfx/shooting.wav"));
        deathSound = new AudioClip(fileURL("./assets/soundfx/death.wav"));
        alienDeathSound = new AudioClip(fileURL("./assets/soundfx/invaderkilled.wav"));
    }
    
    static public ImagePattern getSpaceShip(){
        return spaceship;
    }
    
    static public ImagePattern getExplosion(){
        return explosion;
    }
    
    static public ArrayList<ImagePattern> getAliens(){
        
        return aliens;
    }
    
    static public Media getBackgroundMusic(){
        
        return backgroundMusic;
    }
    
    static public AudioClip getShootingSound(){
        
        return shootingSound;
    }
    
    static public AudioClip getDeathSound(){
        
        return deathSound;
    }
    
    static public AudioClip getAlienDeathSound(){
        
        return alienDeathSound;
    }
}
