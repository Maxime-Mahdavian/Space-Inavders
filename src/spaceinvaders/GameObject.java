package spaceinvaders;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class GameObject {
    protected Rectangle rectangle;
    protected Vector2D position;
    protected Vector2D velocity;
    protected Vector2D acceleration;
    
    public GameObject(Vector2D position, Vector2D velocity, double width, double height)
    {
        this.position = position;
        this.velocity = velocity;
        
        rectangle = new Rectangle(width, height);
        rectangle.setX(position.getX());
        rectangle.setY(position.getY());
        rectangle.setFill(Paint.valueOf("white"));
    }
    
    public Vector2D getPosition()
    {
        return position;
    }
    
    public void setPosition(double x, double y)
    {
        position.setX(x);
        position.setY(y);
        rectangle.setLayoutX(position.getX() - rectangle.getLayoutBounds().getMinX());
        rectangle.setLayoutY(position.getY() - rectangle.getLayoutBounds().getMinY());      
    }
    
    public Rectangle getRectangle()
    {
        return rectangle;
    }
    
    public void update(double dt)
    {
        // Update position
        position = position.add(velocity.mul(dt));
        rectangle.setX(position.getX());
        rectangle.setY(position.getY());
    }
}
