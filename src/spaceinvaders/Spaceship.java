package spaceinvaders;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cstuser
 */
public class Spaceship extends GameObject {
    public Spaceship(Vector2D position, double width, double height)
    {
        super(position, new Vector2D(0,0), width, height);

        rectangle.setFill(AssetManager.getSpaceShip());
    }
}
