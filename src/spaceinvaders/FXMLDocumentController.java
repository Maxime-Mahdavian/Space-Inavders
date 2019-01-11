/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Max
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    AnchorPane pane;
    
    //private Scene scene = pane.getScene();
    //private Window window = scene.getWindow();
    //Stage stage = (Stage)window;
    
    
    private double lastFrameTime = 0.0;
    private ArrayList<Alien> aliens = null;
    private int numAliensKilled = 0;
    private double mouseX = 0;
    private Spaceship spaceship = null;
    private ArrayList<GameObject> projectileSpaceship = null;
    private ArrayList<GameObject> projectileAliens = null;
    private ArrayList<GameObject> explosions = null;
    private int v = 1;
    private int n = 1;
    private int k = 3;
    private int l = 1;
    private int score;
    private int lives = 3;
    private boolean ended = false;
    private boolean goDown = false;
    private MediaPlayer musicPlayer;
    
    @FXML
    private Label gameOverLabel;
    @FXML
    private Label winLabel;
    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;
    @FXML
    private Label playAgainLabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label livesLabel;
    @FXML
    private ImageView life1;
    @FXML
    private ImageView life2;
    @FXML
    private ImageView life3;
    
    @FXML
    public void quitAction(Event event){
        System.exit(0);
    }
    
    @FXML
    public void replayAction(Event event){
        yesButton.setVisible(false);
        yesButton.setDisable(true);
        
        noButton.setVisible(false);
        noButton.setDisable(true);
                
        playAgainLabel.setVisible(false);
        playAgainLabel.setDisable(true);
        
        gameOverLabel.setVisible(false);
        gameOverLabel.setDisable(true);
        
        winLabel.setVisible(false);
        winLabel.setDisable(true);
        
        projectileAliens.clear();
        projectileSpaceship.clear();
        explosions.clear();
        aliens.clear();
        
        life1.setVisible(true);
        life2.setVisible(true);
        life3.setVisible(true);
        
        score = 0;
        lives = 3;
        ended = false;
        numAliensKilled = 0;
        musicPlayer.stop();
        playGame();
    }
    
    @FXML
    public void onMouseMove(MouseEvent event){
        mouseX = event.getX();
        if(!ended){
            if(mouseX <= spaceship.getRectangle().getWidth()/2)
                spaceship.setPosition(spaceship.getRectangle().getWidth()/2, 700);
            else if(mouseX >= pane.getWidth()-spaceship.getRectangle().getWidth())
                spaceship.setPosition(pane.getWidth()-spaceship.getRectangle().getWidth(), 700);
            else
                spaceship.setPosition(mouseX, 700);
        }
    }
    
    @FXML
    public void onMouseClick(MouseEvent event){
        if(projectileSpaceship.isEmpty() && !ended)
            createProjectileSpaceship();
    }
    
    public void addToPane(Node node){
        
        pane.getChildren().add(node);
    }
    
    public void playGame(){
        
        lastFrameTime = 0.0f;
        long initialTime = System.nanoTime();
        
        AssetManager.preloadAllAssets();
        
        projectileSpaceship = new ArrayList<>();
        projectileAliens = new ArrayList<>();
        aliens = new ArrayList<>();
        explosions = new ArrayList<>();
        
        life1.setImage(AssetManager.getSpaceShip().getImage());
        life2.setImage(AssetManager.getSpaceShip().getImage());
        life3.setImage(AssetManager.getSpaceShip().getImage());
        
        
        spaceship = new Spaceship(new Vector2D(0,700), 80, 50);
        addToPane(spaceship.getRectangle());
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j< 2; j++){
                    aliens.add(new Alien(new Vector2D(i*80 + 385, 875-(j*50+600)), new Vector2D(0,0), 75, 50, 0));
                    addToPane(aliens.get(aliens.size()-1).getRectangle());
                }
            }
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j< 2; j++){
                    aliens.add(new Alien(new Vector2D(i*80 + 385, 875-(j*50+700)), new Vector2D(0,0), 75, 50, 1));
                    addToPane(aliens.get(aliens.size()-1).getRectangle());
                }
            }
        
        for(int i = 0; i < 8; i++){
            
                    aliens.add(new Alien(new Vector2D(i*80 + 385, 75), new Vector2D(0,0), 75, 50, 2));
                    addToPane(aliens.get(aliens.size()-1).getRectangle());
                
            }
        
        musicPlayer = new MediaPlayer(AssetManager.getBackgroundMusic());
        musicPlayer.play();
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                
                double currentTime = (now - initialTime)/1000000000.0;
                double frameDeltaTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;
                
                if(!explosions.isEmpty()){
                    if(v == 4){
                    for(int i = 0; i< explosions.size(); i++){
                        pane.getChildren().remove(explosions.get(i).getRectangle());
                        explosions.remove(i);
                    }v = 1;
                    }
                    else
                        v++;
                }
                
                if(!projectileSpaceship.isEmpty()){
                    projectileSpaceship.get(0).update(frameDeltaTime);
                    if(projectileSpaceship.get(0).getPosition().getY()<=0){
                        pane.getChildren().remove(projectileSpaceship.get(0).getRectangle());
                        projectileSpaceship.remove(0);
                    }
                    else{
                        for(int i = 0; i < aliens.size(); i++){
                            Alien alien = aliens.get(i);
                            if(checkCollision(projectileSpaceship.get(0), alien)){
                                
                                switch(alien.getAlienType()){
                                case 0: score+= 50;
                                        break;
                                case 1: score+= 100;
                                        break;
                                case 2: score+= 150;
                                        break;
                                case 3: score+= 50;
                                        break;
                                case 4: score+= 100;
                                        break;
                                case 5: score+= 150;
                                        break;
                            }
                                scoreLabel.setText(String.format("SCORE: %04d", score));
                                
                                AssetManager.getAlienDeathSound().play();
                                alien.getRectangle().setFill(AssetManager.getExplosion());
                                explosions.add(alien);
                                numAliensKilled++;
                                aliens.remove(alien);
                                pane.getChildren().remove(projectileSpaceship.get(0).getRectangle());
                                projectileSpaceship.remove(0);
                                if(numAliensKilled == 40){
                                    win();
                                    this.stop();
                                    ended = true;
                                    
                                }
                                break;
                            }
                        }
                    }
                    
                    
                        
                }
                
                if(!projectileAliens.isEmpty()){
                    for(int i = 0; i < projectileAliens.size(); i++){
                        projectileAliens.get(i).update(frameDeltaTime);
                        if(checkCollision(projectileAliens.get(i), spaceship)){
                                lives--;
                                pane.getChildren().remove(projectileAliens.get(i).getRectangle());
                                projectileAliens.remove(i);
                                AssetManager.getDeathSound().play();
                                switch(lives){
                                    case 2: life1.setVisible(false);
                                            break;
                                    case 1: life2.setVisible(false);
                                            break;
                                    case 0: life3.setVisible(false);
                                            break;
                                    case -1:gameOver();
                                            this.stop();
                                            ended = true;
                                            break;
                            }
                            
                            
                        } 
                    }
                        
                }
                
                

                if(currentTime > n){
                    
                    if(!aliens.isEmpty()){
                        for(int i = 0; i < aliens.size(); i++){
                            if(aliens.get(i).getX() + l*(75 + aliens.get(i).getRectangle().getWidth()) > pane.getWidth() || aliens.get(i).getX() + l*(75 + aliens.get(i).getRectangle().getWidth()/2) < 0){
                                l = - l;
                                goDown = true;
                                break;
                            }
                        }
                        for(int i = 0; i < aliens.size(); i++){
                            if(goDown){
                                aliens.get(i).setPosition(aliens.get(i).getX(), aliens.get(i).getY() + 80);
                                aliens.get(i).getRectangle().setFill(AssetManager.getAliens().get(aliens.get(i).getAlienType() + k));
                            }
                            else{
                              aliens.get(i).setPosition(aliens.get(i).getX() + l*75, aliens.get(i).getY());
                                aliens.get(i).getRectangle().setFill(AssetManager.getAliens().get(aliens.get(i).getAlienType() + k));  
                            }
                        }
                        goDown = false;
                    }
                    
                    
                    n++;
                    k = -k + 3;
                }
                
                if(!aliens.isEmpty()){
                    for(int i = 0; i< aliens.size(); i++){
                        
                        if(checkCollision(aliens.get(i), spaceship)){
                            gameOver();
                            this.stop();
                            ended = true;
                        }
                        else{
                            if(Math.random()<0.00025){
                                createProjectileAlien(aliens.get(i).getPosition().add(new Vector2D(aliens.get(i).getRectangle().getWidth()/2,0)) );
                            } 
                        }
                        
                    }
                }
                                
                
            }
            
            
        }.start();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playGame();
    }    
    
    public void createProjectileSpaceship(){
        AssetManager.getShootingSound().play();
        projectileSpaceship.add(new GameObject(new Vector2D(spaceship.getPosition().getX() + spaceship.getRectangle().getWidth()/2, 700), new Vector2D(0, -600), 3, 20));
        addToPane(projectileSpaceship.get(0).getRectangle());
    }
    
    public void createProjectileAlien(Vector2D position){
        //AssetManager.getShootingSound().play(0.1);
        projectileAliens.add(new GameObject(position, new Vector2D(0,600), 3, 8));
        addToPane(projectileAliens.get(projectileAliens.size() - 1).getRectangle());
    }
    
    public boolean checkCollision(GameObject objectA, GameObject objectB){
        return objectA.getRectangle().getBoundsInParent().intersects(objectB.getRectangle().getBoundsInParent());
    }
    
    public void win(){
        winLabel.setVisible(true);
        winLabel.setDisable(false);
        displayEnding();
    }
    
    public void gameOver(){
        gameOverLabel.setVisible(true);
        gameOverLabel.setDisable(false);
        gameOverLabel.toFront();
        displayEnding();
        
    }
    
    public void displayEnding(){
        pane.getChildren().retainAll(gameOverLabel, winLabel, yesButton, noButton, playAgainLabel, scoreLabel, livesLabel, life1, life2, life3);
        
        n = 1;
        k = 3;
        l = 1;
        lastFrameTime = 0.0;
        
        
        yesButton.setVisible(true);
        yesButton.setDisable(false);
        yesButton.toFront();
        
        noButton.setVisible(true);
        noButton.setDisable(false);
        noButton.toFront();
        
        playAgainLabel.setVisible(true);
        playAgainLabel.setDisable(false);
        playAgainLabel.toFront();
    }
    
}
