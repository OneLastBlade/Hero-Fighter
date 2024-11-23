/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hero_fighter;

/**
 *
 * @author yessine
 */

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Hero {
    private double x;
    private double y;
    private double width;
    private double height;
    private double velocityY; // Vertical speed
    private final double gravity = 0.1; // Gravity strength
    private final double jumpStrength = -6; // Jump strength (negative for upward motion)
    private boolean isJumping; // Flag to prevent multiple jumps

    private Image normalPose;
    private Image walkPose1;
    private Image walkPose2;
    private Image jumpPose;
    private Image currentPose; // Keeps track of the current pose
    private boolean facingRight = true; // Track the direction the hero is facing
    private int walkFrame = 0; // Frame counter for walking animation

    public Hero(double x, double y, double width, double height, String folderPath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocityY = 0;
        this.isJumping = false;

        // Load images
        this.normalPose = loadImage(folderPath, "hold1.png");
        this.walkPose1 = loadImage(folderPath, "walk1.png");
        this.walkPose2 = loadImage(folderPath, "walk2.png");
        this.jumpPose = loadImage(folderPath, "jump.png");

        this.currentPose = normalPose; // Default pose
    }

    private Image loadImage(String folderPath, String fileName) {
        String fullPath = folderPath + "/" + fileName;
        return new Image(getClass().getResource(fullPath).toExternalForm());
    }

    public void update(InputHandler inputHandler) {
        // Jump logic
        if (inputHandler.isUp() && !isJumping) {
            isJumping = true;
            velocityY = jumpStrength; // Apply initial jump velocity
        }

        // Apply gravity
        velocityY += gravity;

        // Update vertical position based on velocity
        y += velocityY;

        // Ground detection
        if (y >= 500) { // Assume ground is at y = 550
            y = 500; // Reset to ground level
            velocityY = 0; // Stop vertical velocity
            isJumping = false; // Allow jumping again
        }

        // Horizontal movement logic (decoupled from isJumping)
        if (inputHandler.isLeft()) {
            x -= 1;
            facingRight = false; // Face left
        }
        if (inputHandler.isRight()) {
            x += 1;
            facingRight = true; // Face right
        }

        // Animation logic
        if (!isJumping) {
            if (inputHandler.isLeft() || inputHandler.isRight()) {
                walkFrame++;
                if (walkFrame % 20 < 10) {
                    currentPose = walkPose1; // Switch to walk pose 1
                } else {
                    currentPose = walkPose2; // Switch to walk pose 2
                }
            } else {
                currentPose = normalPose; // Default pose when standing
            }
        } else {
            currentPose = jumpPose; // Use jump pose while in the air
        }
    }

    public void render(GraphicsContext gc) {
        // Save the current transformation matrix
        gc.save();

        // Flip the image if the hero is facing left
        if (!facingRight) {
            gc.translate(x + width, y); // Translate to the right edge
            gc.scale(-1, 1); // Flip horizontally
        } else {
            gc.translate(x, y); // Normal translation
        }

        // Draw the image
        gc.drawImage(currentPose, 0, 0, width, height);

        // Restore the transformation matrix
        gc.restore();
    }
}
