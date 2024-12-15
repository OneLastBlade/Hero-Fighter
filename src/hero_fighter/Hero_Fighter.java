/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package hero_fighter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import utility.Position;
import utility.RandomBooleanGenerator;

/**
 *
 * @author yessine
 */
public class Hero_Fighter extends Application {

    private Hero hero;
    private InputHandler inputHandler;
    private BackgroundManager backgroundManager;
    private GameTimer gameTimer;
    private List<Monster> monsters = new ArrayList<>(); // List of active monsters
    private long lastSpawnTime = 0; // Time of the last monster spawn
    private long spawnCooldown = 4500; // 5 seconds between spawns
    private String heroName="alex";
    private boolean isGameOver = false;
    private boolean isGameWon = false;
    private long redAnimationStart = 0;
    private Boss boss;
    
    public Hero_Fighter(String heroName){
        this.heroName=heroName;
    }
    public Hero_Fighter(){
       this.boss = new Boss(Math.random() + 0.0 < 0.5 ? 0.0 : 1200.0, 450.0);
    }
    @Override
    public void start(Stage stage) {
       // healthbar=new HealthBar();
        gameTimer=new GameTimer();
        Canvas canvas = new Canvas(1200, 800);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        stage.setResizable(false);
        Scene scene = new Scene(new Pane(canvas), 1200, 600);
        stage.setScene(scene);
        backgroundManager = new BackgroundManager("/hero_fighter/ressources/map/background1yellow.jpg", 1200, 800);
        inputHandler = new InputHandler();

        scene.setOnKeyPressed(e -> {
            inputHandler.handleKeyPress(e.getCode());
        });

        scene.setOnKeyReleased(e -> {
            inputHandler.handleKeyRelease(e.getCode());
        });

        hero = new Hero(200, 500, 100, 100, "/hero_fighter/ressources/heros/"+heroName+"/");
        //Weapon sword = new Weapon("sword");
        //hero.equipWeapon(sword);

        stage.setTitle("Hero Fighter");
        stage.show();

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!isGameOver && !isGameWon) {
                hero.update(inputHandler,monsters);
                hero.render(gc);
                update();
                render(gc);
            }
                 else {
                    renderEndScreen(gc);
                }
            }
        };
        gameLoop.start();
    }
    private void update() {
        hero.update(inputHandler,monsters);
        // Spawn monsters every 5 seconds
         gameTimer.update();

        if (hero.getHealth() <= 0) {
            isGameOver = true;
        }

        if (gameTimer.isTimeUp()) {
            isGameWon = hero.getHealth() > 0;
            isGameOver = true;
        }
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastSpawnTime >= spawnCooldown) {
    lastSpawnTime = currentTime;
    spawnCooldown -= 50;
    monsters.add(new Monster(RandomBooleanGenerator.getRandomBoolean() ? 0 : 1200, 500)); // Random spawn position
}

// Update monsters
Iterator<Monster> iterator =  this.boss.getMinions().iterator();
while (iterator.hasNext()) {
    Monster monster = iterator.next();

    // Check collision with hero
    if (monster.collidesWithHero(hero)) {
        iterator.remove(); // Remove monster on collision
        //healthbar.decreaseHealth(20)
        hero.takeDamage(20);
        // Optionally, reduce hero health or take other actions
    }
    
}
boolean heroisveryclose=boss.damagefrompunches();
boolean heroisclose=boss.herotakedamagefromjump(hero.getX(),hero.getY());
boss.attackhero(hero.getX(), hero.getY());
if (heroisveryclose==true)
{
    hero.takeDamage(1);
}
if (heroisclose==true)
{
    hero.takeDamage(5);
}
Iterator<Monster> iterator1 = monsters.iterator();
while (iterator1.hasNext()) {
    Monster monster = iterator1.next();
    monster.update(hero.getX(), 500); // Follow the hero
    boss.update(hero.getX(), hero.getY()+50);

    // Check collision with hero
    if (monster.collidesWithHero(hero)) {
        iterator1.remove(); // Remove monster on collision
        //healthbar.decreaseHealth(20);
        hero.takeDamage(20);
        // Optionally, reduce hero health or take other actions
    }
    
}
     List<Bullet> bulletsToRemove = new ArrayList<>();
        List<Monster> monstersToRemove = new ArrayList<>();
        Polygon swordHitbox = hero.getSwordHitbox(); 
        for (Monster monster : monsters) {
                if (monster.collidesWithSword(swordHitbox)) {
                    if (monster.isDead()) {
                        monstersToRemove.add(monster); // Remove dead monsters
                    }
                    
                }}

        for (Bullet bullet : hero.getBullets()) {
            
            if (bullet.collideWith(boss)) 
            {
                    bulletsToRemove.add(bullet);
                    //monstersToRemove.add(monster);
                    boss.takeDamage(100);
            }
            for (Monster minion:this.boss.getMinions())
            {
              if (bullet.collidesWith(minion)) {
                    bulletsToRemove.add(bullet);
                    //monstersToRemove.add(monster);
                    minion.takeDamage(100);
                    if (minion.isDead()) {
                        monstersToRemove.add(minion); // Remove dead monsters
                    }
                }  
            }
            for (Monster monster : monsters) {
                if (bullet.collidesWith(monster)) {
                    bulletsToRemove.add(bullet);
                    //monstersToRemove.add(monster);
                    monster.takeDamage(100);
                    if (monster.isDead()) {
                        monstersToRemove.add(monster); // Remove dead monsters
                    }
                }
            }

    }

        hero.getBullets().removeAll(bulletsToRemove);
        monsters.removeAll(monstersToRemove);
        this.boss.getMinions().removeAll(monstersToRemove);
    }
    private void render(GraphicsContext gc) {
        gc.clearRect(0, 0, 1200, 800);
         if (gameTimer.getRemainingTime() <= 15) {
            if (redAnimationStart == 0) {
                redAnimationStart = System.currentTimeMillis();
            }
            long elapsed = (System.currentTimeMillis() - redAnimationStart) / 500 % 2;
            if (elapsed == 0) {
                gc.setFill(Color.rgb(255, 0, 0, 0.3));
                gc.fillRect(0, 0, 1200, 800);
            }
        }
        backgroundManager.render(gc);
        hero.render(gc);
        gameTimer.renderTimer(gc,1200);
        
       // healthbar.renderHealthBar(gc);
        for (Monster monster : monsters) {
            monster.render(gc);
        }
        boss.render(gc);
        
    }
      private void renderEndScreen(GraphicsContext gc) {
        gc.clearRect(0, 0, 1200, 800);

        String message;
        Color color;

        if (isGameWon) {
            message = "You Win!";
            color = Color.GREEN;
        } else {
            message = "Game Over!";
            color = Color.RED;
        }

        gc.setFill(color);
        gc.setFont(javafx.scene.text.Font.font("Arial", 64));
        gc.fillText(message, 450, 400);
    }

    public static void main(String[] args) {
        launch(args);
    }

}

