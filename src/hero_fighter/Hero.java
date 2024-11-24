package hero_fighter;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import utility.Position;

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
   private List<Bullet> bullets = new ArrayList<>(); 
    private Weapon equippedWeapon;
    private int attackFrame=0;
    private boolean isAttacking=false;
    private long lastFiredTime = 0;
    private final long cooldownTime = 1000;

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
        this.equippedWeapon = new Weapon("sword", 0,0);// Initialize weapon at hero's position

        equipWeapon(this.equippedWeapon);  // Equip the weapon
    }

    private Image loadImage(String folderPath, String fileName) {
        String fullPath = folderPath + "/" + fileName;
        return new Image(getClass().getResource(fullPath).toExternalForm());
    }

    public void equipWeapon(Weapon weapon) {
        this.equippedWeapon = weapon;

        // Adjust weapon's X and Y position based on the hero's current position and direction
        double weaponOffsetX = weapon.getType().equals("sword")?-90:-100;// Adjust this to move the weapon closer or farther from the hero
        double weaponOffsetY = weapon.getType().equals("sword")?-10:50; // Adjust this to position the weapon vertically relative to the hero's hand

        // Make sure weapon position is updated every frame based on hero's position
       if(!isAttacking)
       {
            equippedWeapon.setX(-weaponOffsetX); // Position to the left of hero
            equippedWeapon.setY( weaponOffsetY+equippedWeapon.getAngle());
       }
  
        else
       {equippedWeapon.setX(-weaponOffsetX+equippedWeapon.getAngle()); // Position to the left of hero
            equippedWeapon.setY( weaponOffsetY+equippedWeapon.getAngle());}
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
    if (y >= 500) { // Assume ground is at y = 500
        y = 500; // Reset to ground level
        velocityY = 0; // Stop vertical velocity
        isJumping = false; // Allow jumping again
    }

    // Horizontal movement logic
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

            // Rotate the weapon based on hero's movement
            equippedWeapon.setAngle(15);

        } else {
            currentPose = normalPose; // Default pose when standing
            equippedWeapon.setAngle(0); // No rotation when standing still
        }
    } else {
        currentPose = jumpPose; // Use jump pose while in the air
        equippedWeapon.setAngle(0); // Keep weapon angle neutral while jumping
    }

    // Check for attack (shoot gun or swing sword)
    if (inputHandler.isSpace() && !isAttacking) {
            // Check if cooldown has passed since last bullet
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastFiredTime >= cooldownTime) {
                isAttacking = true; // Start the attack

                if (equippedWeapon.getType().equals("gun")) {
                    // If the weapon is a gun, fire a bullet in the direction the hero is facing
                    bullets.add(new Bullet(70,  height / 2-10, true)); // Fire bullet
                    isAttacking = false;
                    lastFiredTime = currentTime; // Update last fired time
                } else if (equippedWeapon.getType().equals("sword")) {
                    // Sword attack logic (swing)
                    attackFrame = 0; // Reset attack frame to animate the attack
                }
            }
    }
    if (isAttacking) {
        if (equippedWeapon.getType().equals("sword")) {
            attackFrame++; // Increment the attack frame

            // Swing the sword with a short duration of frames
            if (attackFrame < 20) { // Attack duration (swing)
                equippedWeapon.setAngle(45); // Swing the sword (rotate by 45 degrees)
            } else if (attackFrame < 40) { // Attack return phase
                equippedWeapon.setAngle(0); // Return the sword to neutral position
            } else {
                isAttacking = false; // End the attack after the animation is done
                equippedWeapon.setAngle(0); // Reset sword to neutral position
            }
        }
    }

    // Update bullets
    for (Bullet bullet : bullets) {
        bullet.update(); // Move each bullet
    }

    // Ensure the weapon follows the hero's position
    equipWeapon(equippedWeapon); // Update the weapon's position relative to the hero
}


    public void render(GraphicsContext gc) {
        // Save the current transformation matrix
        gc.save();

        // Translate the position correctly without flipping transformations
        if (!facingRight) {
            gc.translate(x + width, y); // Translate to the right edge
            gc.scale(-1, 1); // Flip horizontally
        } else {
            gc.translate(x, y); // Normal translation
        }

        // Draw the hero image
        gc.drawImage(currentPose, 0, 0, width, height);

        // Draw the equipped weapon if available
        if (equippedWeapon != null) {
            Image weaponImage = new Image(getClass().getResource("/hero_fighter/ressources/weapons/" + equippedWeapon.getType() + ".png").toExternalForm());
            equippedWeapon.render(gc, weaponImage);
        }
         for (Bullet bullet : bullets) {
            bullet.render(gc); // Draw each bullet
        }

        // Restore the transformation matrix
        gc.restore();
    }
}


