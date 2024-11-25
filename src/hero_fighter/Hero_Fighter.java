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
    private GameTimer gametimer;
    private HealthBar healthbar;
    private List<Monster> monsters = new ArrayList<>(); // List of active monsters
    private long lastSpawnTime = 0; // Time of the last monster spawn
    private long spawnCooldown = 5000; // 5 seconds between spawns
    
    @Override
    public void start(Stage stage) {
        healthbar=new HealthBar();
        gametimer=new GameTimer();
        Canvas canvas = new Canvas(1200, 800);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        stage.setResizable(false);
        Scene scene = new Scene(new Pane(canvas), 1200, 800);
        stage.setScene(scene);
        backgroundManager = new BackgroundManager("/hero_fighter/ressources/map/background1yellow.jpg", 1200, 800);
        inputHandler = new InputHandler();

        scene.setOnKeyPressed(e -> {
            inputHandler.handleKeyPress(e.getCode());
        });

        scene.setOnKeyReleased(e -> {
            inputHandler.handleKeyRelease(e.getCode());
        });

        hero = new Hero(200, 500, 100, 100, "/hero_fighter/ressources/heros/adventurer/");
        //Weapon sword = new Weapon("sword");
        //hero.equipWeapon(sword);

        stage.setTitle("Hero Fighter");
        stage.show();

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                hero.update(inputHandler);
                hero.render(gc);
                update();
                render(gc);
            }
        };
        gameLoop.start();
    }
    private void update() {
        hero.update(inputHandler);
        // Spawn monsters every 5 seconds
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastSpawnTime >= spawnCooldown) {
    lastSpawnTime = currentTime;
    spawnCooldown -= 50;
    monsters.add(new Monster(RandomBooleanGenerator.getRandomBoolean() ? 0 : 1200, 500)); // Random spawn position
}

// Update monsters
Iterator<Monster> iterator = monsters.iterator();
while (iterator.hasNext()) {
    Monster monster = iterator.next();
    monster.update(hero.getX(), 500); // Follow the hero

    // Check collision with hero
    if (monster.collidesWithHero(hero)) {
        iterator.remove(); // Remove monster on collision
        // Optionally, reduce hero health or take other actions
    }
}
    }
    private void render(GraphicsContext gc) {
        gc.clearRect(0, 0, 1200, 800);
        backgroundManager.render(gc);
        hero.render(gc);
        gametimer.renderTimer(gc,1200);
        healthbar.renderHealthBar(gc);
        for (Monster monster : monsters) {
            monster.render(gc);
        }
        
    }

    public static void main(String[] args) {
        launch(args);
    }

}

