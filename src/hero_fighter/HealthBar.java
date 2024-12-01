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
    private double width = 50; // Default width of the health bar
    private double height = 5; // Default height of the health bar
    private boolean isMonster=true;

    // Constructor
    public HealthBar(double maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }
    public HealthBar(double maxHealth,boolean isMonster) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.isMonster = isMonster;
    }

    // Method to decrease health based on damage
    public void decreaseHealth(double damage) {
        currentHealth = Math.max(0, currentHealth - damage); // Prevent negative health
    }

    // Method to render health bar relative to an entity
    public void render(GraphicsContext gc, double x, double y) {
        // Draw background (gray bar)
        gc.setFill(Color.GRAY);
        gc.fillRect(x+13, y - 10, width, height); // Position above the entity

        // Calculate health percentage and draw the health (green bar)
        double healthPercentage = currentHealth / maxHealth;
        gc.setFill(isMonster?Color.RED:Color.LAWNGREEN);
        gc.fillRect(x+13, y - 10, width * healthPercentage, height);
    }

    // Method to reset health
    public void resetHealth() {
        currentHealth = maxHealth;
    }

    // Method to get current health
    public double getCurrentHealth() {
        return currentHealth;
    }
}

