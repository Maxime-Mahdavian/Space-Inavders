package spaceinvaders;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.paint.ImagePattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cstuser
 */
public class Alien extends GameObject {
    private int alienType;
    private Vector2D position;
    
    public Alien(Vector2D position, Vector2D velocity, double width, double height, int alienType)
    {
        super(position, velocity, width, height);
        this.alienType = alienType;
        this.position = position;
        ArrayList<ImagePattern> aliens = new ArrayList<>();
        aliens = AssetManager.getAliens();
        rectangle.setFill(AssetManager.aliens.get(alienType));
    }
    
    public int getAlienType(){
        return alienType;
    }
    
    public double getX(){
        return position.getX();
    }
    
    public double getY(){
        return position.getY();
    }
    
    public void setPosition(double x, double y){
        super.setPosition(x, y);
        position.setX(x);
        position.setY(y);
    }
    
    public void update(double dt){
        super.update(dt);  
    }
}
