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
import javafx.util.Duration;

public class GameTimer {
    private int timeInSeconds = 180;  // 3 minutes in seconds
    private final Font timerFont = new Font("Arial", 50);  // Set larger font size

    private String timerText = "";

    public GameTimer() {
        updateTimerText();

        // Timer countdown logic
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> {
                if (timeInSeconds > 0) {
                    timeInSeconds--;
                    updateTimerText();
                }
            })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateTimerText() {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        timerText = String.format("%02d:%02d", minutes, seconds);  // Update the timer display
    }

    public void renderTimer(GraphicsContext gc, double canvasWidth) {
        gc.setFont(timerFont);  // Set font for the timer
        gc.setFill(Color.WHITE);  // Set text color to white

        // Calculate the x-coordinate for centering
        double textWidth = gc.getFont().getSize() * timerText.length() / 2.5;  // Estimate width based on font size
        double x = (canvasWidth - textWidth) / 2;

        // Render the timer text in the middle at the top of the screen
        gc.fillText(timerText, x, 60);  // Adjust y-position for top
    }
}




