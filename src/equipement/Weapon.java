/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipement;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import utility.Position;

/**
 *
 * @author yessine
 */
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Weapon {

    private String type; // e.g., "Sword" or "Gun"
    private double x, y;  // Position of the weapon on the screen
    private double angle; // Rotation angle for the weapon
    

    // Constructor
    public Weapon(String type, double x, double y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.angle = 0; // Default no rotation
    }

    // Getters and Setters for the type, position, and angle
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle; // Set the rotation angle
    }

    // Render method to display the weapon on the screen
    public void render(GraphicsContext gc, Image image) {
        // Define a scale factor based on the hero's size (e.g., scaling sword down by 20%)
        double scaleFactor =type=="sword"?0.2:0.1; // Adjust this factor as needed

        // Calculate the new width and height while maintaining the aspect ratio
        double swordWidth = image.getWidth() * scaleFactor;
        double swordHeight = image.getHeight() * scaleFactor;

        // Save the current transformation state before applying rotation
        gc.save();

        // Move the origin to the weapon's position
        gc.translate(x, y);

        // Apply the rotation (rotate around the center of the weapon)
        gc.rotate(angle);

        // Draw the resized sword, positioning it with respect to the center
        gc.drawImage(image, -swordWidth / 2, -swordHeight / 2, swordWidth, swordHeight);

        // Restore the previous transformation state
        gc.restore();
    }
}
