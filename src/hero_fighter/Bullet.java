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
    private double velocityX; // Bullet's horizontal speed
    private double width = 10; // Width of the bullet (adjust as needed)
    private double height = 5; // Height of the bullet (adjust as needed)
    private Image image; // Bullet image

    public Bullet(double x, double y, boolean facingRight) {
        this.x = x;
        this.y = y;
        this.velocityX = facingRight ? 5 : -5; // Bullet speed depends on the hero's facing direction
        this.image = new Image(getClass().getResource("/hero_fighter/ressources/weapons/bullet.png").toExternalForm()); // Load the bullet image
    }

    public void update() {
        x += velocityX; // Move the bullet horizontally
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y, width, height); // Draw the bullet
    }

    // Getters and setters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
