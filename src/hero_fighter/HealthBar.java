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
    private boolean isBoss=false;

    // Constructor
    public HealthBar(double maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }
    public HealthBar(double maxHealth,boolean isMonster,boolean isBoss) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.isMonster = isMonster;
        this.isBoss=isBoss;
    }
    public int getMaxHealth(){
        return 1000;
    }

    // Method to decrease health based on damage
    public void decreaseHealth(double damage) {
        currentHealth = Math.max(0, currentHealth - damage); // Prevent negative health
    }
    public void increaseHealth(double health) {
        currentHealth = Math.max(maxHealth, currentHealth + health); // Prevent negative health
    }

    // Method to render health bar relative to an entity
    public void render(GraphicsContext gc, double x, double y) {
         if (isBoss) {
            // Render a large health bar at the top of the screen
            renderBossHealthBar(gc);
        } else {
            // Render health bar above the entity
            renderEntityHealthBar(gc, x, y);
        }
    }
    private void renderBossHealthBar(GraphicsContext gc) {
        double bossBarWidth = 400; // Boss health bar width
        double bossBarHeight = 20; // Boss health bar height
        double screenX = (1200 - bossBarWidth) / 2; // Center on the screen
        double screenY = 20; // Top of the screen

        // Draw the background (gray bar)
        gc.setFill(Color.GRAY);
        gc.fillRect(screenX, screenY, bossBarWidth, bossBarHeight);

        // Draw the health percentage (red bar for the boss)
        double healthPercentage = currentHealth / maxHealth;
        gc.setFill(Color.RED);
        gc.fillRect(screenX, screenY, bossBarWidth * healthPercentage, bossBarHeight);

        // Optional: Draw a border for the boss health bar
        gc.setStroke(Color.BLACK);
        gc.strokeRect(screenX, screenY, bossBarWidth, bossBarHeight);
    }

    // Render health bar above regular entities
    private void renderEntityHealthBar(GraphicsContext gc, double x, double y) {
        // Draw the background (gray bar)
        gc.setFill(Color.GRAY);
        gc.fillRect(x + 13, y - 10, width, height); // Position above the entity

        // Calculate health percentage and draw the health (green for hero, red for monster)
        double healthPercentage = currentHealth / maxHealth;
        gc.setFill(isMonster ? Color.RED : Color.LAWNGREEN);
        gc.fillRect(x + 13, y - 10, width * healthPercentage, height);
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

