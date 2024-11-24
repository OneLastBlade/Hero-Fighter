/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package hero_fighter;

import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

public class HealthBar {
    private double maxHealth = 100;
    private double currentHealth = 100;
    private double width = 200;
    private double height = 20;

    // Constructor to initialize health bar

    // Method to decrease health based on damage
    public void decreaseHealth(double damage) {
        currentHealth = Math.max(0, currentHealth - damage); // Prevent negative health
    }

    // Method to render health bar on the canvas
    public void renderHealthBar(GraphicsContext gc) {
        // Draw background (gray bar)
        gc.setFill(Color.GRAY);
        gc.fillRect(20, 30, width, height);  // Position and size for background bar

        // Calculate the health percentage and draw the health bar (red bar)
        double healthPercentage = currentHealth / maxHealth;
        gc.setFill(Color.RED);
        gc.fillRect(20, 30, width * healthPercentage, height);  // Adjust the width based on health
    }

    // Optional: Method to reset health (useful for testing)
    public void resetHealth() {
        currentHealth = maxHealth;
    }

    // Optional: Method to get current health for display purposes
    public double getCurrentHealth() {
        return currentHealth;
    }
}
