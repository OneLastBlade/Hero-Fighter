package hero_fighter;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import utility.Position;

public class Hero {

    private double x;
    private double y;
    private double width;
    private double height;
    private double velocityY; // Vertical speed
    private final double gravity = 0.1; // Gravity strength
    private final double jumpStrength = -8; // Jump strength (negative for upward motion)
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
    private final long cooldownTime = 750;
    private HealthBar healthBar;

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
        this.equippedWeapon = new Weapon("gun", 0,0);// Initialize weapon at hero's position

        equipWeapon(this.equippedWeapon);  // Equip the weapon
        this.healthBar = new HealthBar(100,false);
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
    public List<Bullet> getBullets() {
    return bullets;
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

  public void update(InputHandler inputHandler, List<Monster> monsters) {
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
        x -= 2;
        facingRight = false; // Face left
    }
    if (inputHandler.isRight()) {
        x += 2;
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
    // Check if cooldown has passed since the last bullet was fired
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastFiredTime >= cooldownTime) {
        isAttacking = true; // Start the attack

        if (equippedWeapon.getType().equals("gun")) {
            // Fire a bullet in the direction the hero is facing
            bullets.add(new Bullet(x + (facingRight ? width : -10), y + height / 2 - 5, facingRight)); 

            lastFiredTime = currentTime; // Update the last fired time
            isAttacking = false; // Reset attack flag for next use
        } else if (equippedWeapon.getType().equals("sword")) {
            // Sword attack logic (swing animation)
            attackFrame = 0; // Reset attack frame to animate the attack
        }
    }
}

    if (isAttacking) {
        if (equippedWeapon.getType().equals("sword")) {
            attackFrame++; // Increment attack frame

            if (attackFrame < 20) { // Sword swing phase
                equippedWeapon.setAngle(75); 
            
            } else if (attackFrame < 40) { // Return phase
                equippedWeapon.setAngle(0); // Reset sword to neutral position
            } else {
                isAttacking = false; // End attack after animation
                equippedWeapon.setAngle(0); // Reset sword position
            }
            


            Polygon swordHitbox = getSwordHitbox();
            // Check if the sword hitbox collides with any monster
            for (Monster monster : monsters) {
                if (!monster.isHitThisAttack() && monster.collidesWithSword(swordHitbox)) {
                    monster.takeDamage(20);
                monster.setHitThisAttack(true); // Mark monster as hit for this attack
    }
}         
       }
    }
    // Reset the hit flag at the end of the attack cycle
if (!isAttacking) {
    for (Monster monster : monsters) {
        monster.setHitThisAttack(false);
    }
}

    // Update bullets
    for (Bullet bullet : bullets) {
        bullet.update(); // Move each bullet
    }

    // Ensure the weapon follows the hero's position
    equipWeapon(equippedWeapon); // Update the weapon's position relative to the hero
}

public Polygon getSwordHitbox() {
    // Sword position and dimensions
    double swordLength = 120;  // Length of the sword
    double swordThickness = 5;  // Thickness of the sword
    double swordX, swordY;

    // Adjust sword position based on facing direction
    if (facingRight) {
        swordX = x + width; // Start at the hero's right hand
        swordY = y + height / 2;
    } else {
        swordX = x - swordLength; // Start at the hero's left hand
        swordY = y + height / 2;
    }

    // Calculate the rotation angle in radians
    double angleInRadians = Math.toRadians(equippedWeapon.getAngle());
    double cos = Math.cos(angleInRadians);
    double sin = Math.sin(angleInRadians);

    // Calculate the sword's corners for the hitbox
    double x1 = swordX;
    double y1 = swordY;
    double x2 = swordX + swordLength * cos;
    double y2 = swordY + swordLength * sin;
    double x3 = x2 - swordThickness * sin;
    double y3 = y2 + swordThickness * cos;
    double x4 = x1 - swordThickness * sin;
    double y4 = y1 + swordThickness * cos;

    // Create the Polygon hitbox
    return new Polygon(x1, y1, x2, y2, x3, y3, x4, y4);
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

    // Restore the transformation matrix for bullets
    gc.restore();

    // Render bullets independently, without being affected by hero flipping
    for (Bullet bullet : bullets) {
        bullet.render(gc); // Draw each bullet
    }
    healthBar.render(gc, x, y);

}
    public void takeDamage(double damage) {
        healthBar.decreaseHealth(damage); // Update health bar on taking damage
    }

    double getHealth() {
        return healthBar.getCurrentHealth();
    }

}


