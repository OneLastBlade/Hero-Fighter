/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package hero_fighter;

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

/**
 *
 * @author yessine
 */
public class Hero_Fighter extends Application {
    
    private double playerX = 200;
    private double playerY = 200;
    private boolean up, down, left, right;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Scene scene = new Scene(new Pane(canvas), 800, 600);
        stage.setScene(scene);
        stage.setTitle("Simple 2D Game");
        stage.show();

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.Z) up = true;
            if (e.getCode() == KeyCode.S) down = true;
            if (e.getCode() == KeyCode.Q) left = true;
            if (e.getCode() == KeyCode.D) right = true;
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.Z) up = false;
            if (e.getCode() == KeyCode.S) down = false;
            if (e.getCode() == KeyCode.Q) left = false;
            if (e.getCode() == KeyCode.D) right = false;
        });

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render(gc);
            }
        };
        gameLoop.start();
    }

    private void update() {
        if (up) playerY -= 2;
        if (down) playerY += 2;
        if (left) playerX -= 2;
        if (right) playerX += 2;
    }

    private void render(GraphicsContext gc) {
        gc.clearRect(0, 0, 800, 600);
        gc.fillRect(playerX, playerY, 50, 50);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
    
