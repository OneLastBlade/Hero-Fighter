/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package hero_fighter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameTimer {
    private int timeInSeconds = 180;  // Total game time in seconds
    private final Font timerFont = new Font("Arial", 50);  // Font for timer display
    private String timerText = "";
    private long lastUpdateTime;
    private boolean isTimeUp = false;

    public GameTimer() {
        updateTimerText();
        lastUpdateTime = System.nanoTime();  // Initialize the timer
    }

    public void update() {
        if (isTimeUp) {
            return;  // Stop updating if time is up
        }

        long currentTime = System.nanoTime();
        long elapsedTime = (currentTime - lastUpdateTime) / 1_000_000_000;

        if (elapsedTime >= 1) {
            timeInSeconds--;
            lastUpdateTime = currentTime;
            updateTimerText();
        }

        if (timeInSeconds <= 0) {
            isTimeUp = true;
            timeInSeconds = 0;  // Ensure time does not go negative
            updateTimerText();
        }
    }

    private void updateTimerText() {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        timerText = String.format("%02d:%02d", minutes, seconds);
    }

    public void renderTimer(GraphicsContext gc, double canvasWidth) {
        gc.setFont(timerFont);
        gc.setFill(Color.WHITE);  // Set timer text color

        // Calculate text width for centering
        Text text = new Text(timerText);
        text.setFont(timerFont);
        double textWidth = text.getBoundsInLocal().getWidth();

        // Center the timer text
        double x = (canvasWidth - textWidth) / 2;

        gc.fillText(timerText, x, 60);  // Render at the top-center of the screen
    }

    public int getRemainingTime() {
        return timeInSeconds;
    }

    public boolean isTimeUp() {
        return isTimeUp;
    }
}

