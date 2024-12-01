/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hero_fighter;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author yessine
 */
public class Bullet {

    private double x, y; // Position of the bullet
    private final double velocityX; // Bullet's horizontal speed (final to prevent accidental changes)
    private final double width = 15; // Width of the bullet (adjust as needed)
    private final double height = 7.5; // Height of the bullet (adjust as needed)
    private final Image image; // Bullet image

    public Bullet(double x, double y, boolean facingRight) {
        this.x = x;
        this.y = y;

        // Set velocity based on initial facing direction
        this.velocityX = facingRight ? 5 : -5; // Bullet speed is fixed and independent
        this.image = new Image(getClass().getResource("/hero_fighter/ressources/weapons/bullet.png").toExternalForm());
    }

    public void update() {
        x += velocityX; // Move the bullet horizontally
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y, width, height); // Draw the bullet at its current position
    }

    // Getters for position (optional, if needed for collision or other logic)
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    public boolean collidesWith(Monster monster) {
    return x < monster.getX() + monster.getWidth() &&
           x + width > monster.getX() &&
           y < monster.getY() + monster.getHeight() &&
           y + height > monster.getY();
}
}
