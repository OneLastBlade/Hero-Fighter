/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monsters;

import hero_fighter.HealthBar;
import fighter.Hero;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 *
 * @author yessine
 */
public class Monster {
    private double x, y; // Monster's position
    private double speed = 0.8; // Speed at which the monster moves
    private double width = 100; // Monster's width
    private double height = 100; // Monster's height
    private Image walkPose1; // Monster's first walking pose
    private Image walkPose2; // Monster's second walking pose
    private Image currentPose; // Current pose to render
    private int walkFrame = 0; // Counter for animation frames
    private boolean faceRight=true;
    private HealthBar healthBar; 
    private boolean hitThisAttack = false;

public boolean isHitThisAttack() {
    return hitThisAttack;
}

public void setHitThisAttack(boolean hitThisAttack) {
    this.hitThisAttack = hitThisAttack;
}

    public Monster(double x, double y) {
        this.x = x;
        this.y = y;

        // Load images
        this.walkPose1 = new Image(getClass().getResource("/hero_fighter/ressources/monsters/zombie_walk1.png").toExternalForm());
        this.walkPose2 = new Image(getClass().getResource("/hero_fighter/ressources/monsters/zombie_walk2.png").toExternalForm());
        this.currentPose = walkPose1; // Default to the first pose
        this.healthBar = new HealthBar(100);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    
    // Update the monster's position to follow the hero
    public void update(double heroX, double heroY) {
        // Move horizontally towards the hero
        if (x < heroX) {
            x += speed;
            faceRight=true;
            // Move right
        } else if (x > heroX) {
            x -= speed; // Move left
            faceRight=false;
        }

        // Move vertically towards the hero
       /* if (y < heroY) {
            y += speed; // Move down
        } else if (y > heroY) {
            y -= speed; // Move up
        }*/

        // Update walking animation
        walkFrame++;
        if (walkFrame % 20 < 10) { // Alternate every 10 frames
            currentPose = walkPose1;
        } else {
            currentPose = walkPose2;
        }
    }

    // Render the monster
   public void render(GraphicsContext gc) {
        // Save the current transformation matrix
        gc.save();

        // If the monster is facing left, flip the image horizontally
        if (!faceRight) {
            gc.translate(x + width, y); // Translate to the right edge
            gc.scale(-1, 1);  // Flip the image horizontally
        } else {
            gc.translate(x, y); // Normal translation
        }

        // Draw the monster's image
        gc.drawImage(currentPose, 0, 0, width, height);

        // Restore the transformation matrix
        gc.restore();
        healthBar.render(gc, x, y);
    }

    // Check collision with the hero
    public boolean collidesWithHero(Hero hero) {
        return x < hero.getX() + hero.getWidth() &&
               x + width > hero.getX() &&
               y < hero.getY() + hero.getHeight() &&
               y + height > hero.getY();
    }
    public void takeDamage(double damage) {
        healthBar.decreaseHealth(damage); // Update health bar on taking damage
    }
    public boolean isDead() {
        return healthBar.getCurrentHealth() <= 0; // Check if health is depleted
    }
     public boolean collidesWithSword(Polygon swordHitbox) {
       Rectangle monsterHitbox = new Rectangle(x, y, width, height); // Monster's hitbox
    Shape intersection = Shape.intersect(monsterHitbox, swordHitbox);
    return intersection.getBoundsInLocal().getWidth() > 0;
    }
}
