package monsters;



import hero_fighter.HealthBar;
import fighter.Hero;
import monsters.Monster;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.util.ArrayList;
import java.util.List;

public class Boss {
    private boolean attacking = false; // Tracks if the boss is attacking
    private final Image attackPose1 = loadImage("/hero_fighter/ressources/boss/attack1.png");
    private final Image attackPose2 = loadImage("/hero_fighter/ressources/boss/attack2.png");
    private final Image attackPose3 = loadImage("/hero_fighter/ressources/boss/attack3.png");

    // Jumping animation variables
    // Jump-Smash variables
    private boolean smashInProgress = false; // Tracks if a smash is happening
    private double smashHeight = 50; // How high the boss "jumps"
    private double originalY; // Store initial y-coordinate before the smash
    private long smashStartTime; // Time when the smash started
    private long smashDuration = 600; // Total duration for the smash


    private double x, y; // Boss's position
    private double speed = 0.4; // Speed for the boss (slower but stronger)
    private double width = 150; // Boss's width
    private double height = 150; // Boss's height

    // Walking animation frames
    private final Image walkPose1 = loadImage("/hero_fighter/ressources/boss/walk4.png");
    private final Image walkPose2 = loadImage("/hero_fighter/ressources/boss/walk5.png");
    private final Image walkPose3 = loadImage("/hero_fighter/ressources/boss/walk6.png");
    private final Image walkPose4 = loadImage("/hero_fighter/ressources/boss/walk7.png");
    private final Image holdPose = loadImage("/hero_fighter/ressources/boss/hold.png");

    // Smashing animation frames
    private final Image smashPose = loadImage("/hero_fighter/ressources/boss/smash1.png");

    private Image currentPose = holdPose; // Default pose
    private int walkFrame = 0; // Counter for animation frames
    private boolean faceRight = true;

    private HealthBar healthBar;
    private boolean enraged = false; // Special mode when health is low
    private long lastDamageTime = 0; // Track last damage time for regeneration
    private long lastAbilityTime = 0; // Track last ability usage time
    private List<Monster> minions; // Summoned minions

    // Smash animation variables
    private boolean smashing = false; // Flag for smash animation
    private int smashFrame = 0; // Counter for smash animation frames
    private boolean hitThisAttack = false;
    
    public boolean isHitThisAttack() {
    return hitThisAttack;
}

public void setHitThisAttack(boolean hitThisAttack) {
    this.hitThisAttack = hitThisAttack;
}
    public double getX() {
    return x;
}
    public double getY() {
    return y;
}
    public double getSpeed() {
    return speed;
}
    public double getWidth() {
    return width;
}
    public double getHeight() {
    return height;
}
    public List<Monster> getMinions() {
    return minions;
}
    public Boss(double x, double y) {
        this.x = x;
        this.y = y;
        this.healthBar = new HealthBar(1000,true,true); // Boss has more health
        this.minions = new ArrayList<>();
    }

    private Image loadImage(String path) {
        try {
            return new Image(getClass().getResource(path).toExternalForm());
        } catch (Exception e) {
            System.err.println("Failed to load image: " + path);
            return null; // Return null if image cannot be loaded
        }
    }

   public void update(double heroX, double heroY) {
    // Update smash animation if active
    updateSmashAnimation();

    // Skip movement if smashing
    if (!smashing & !attacking) {
        // Move horizontally towards the hero
        if (x < heroX) {
            x += speed;
            faceRight = true;
        } else if (x > heroX) {
            x -= speed;
            faceRight = false;
        }
    }
    if (!smashing & attacking)
    {
        walkFrame++;
        switch (walkFrame % 30) {
            case 0, 1, 2, 3, 4, 5 -> currentPose = attackPose1;
            case 10, 11, 12, 13, 14, 15 -> currentPose = attackPose2;
            case 20, 21, 22, 23, 24, 25 -> currentPose = attackPose3;
            
        }
        
    }

    // Special mode when health is below a threshold
    if (!enraged && healthBar.getCurrentHealth() < 150) {
        enraged = true;
        speed = 1.0; // Increase speed when enraged
    }

    // Summon minions if health falls below a threshold (e.g., 100)
    if (healthBar.getCurrentHealth() < 100) {
        summonMinions(); // Summon minions when health drops below 100
    }

    // Regenerate health if not attacked for 5 seconds
    if (System.currentTimeMillis() - lastDamageTime > 5000 && healthBar.getCurrentHealth()<healthBar.getMaxHealth()) {
        healthBar.increaseHealth(1);
    }

    // Use special abilities every 8 seconds
    if (System.currentTimeMillis() - lastAbilityTime > 8000) {
        useRandomAbility(heroX, heroY);
        lastAbilityTime = System.currentTimeMillis();
    }

    // Walking animation (only if not smashing)
    if (!smashing & !attacking) {
        walkFrame++;
        switch (walkFrame % 40) {
            case 0, 1, 2, 3, 4, 5 -> currentPose = walkPose1;
            case 10, 11, 12, 13, 14, 15 -> currentPose = walkPose2;
            case 20, 21, 22, 23, 24, 25 -> currentPose = walkPose3;
            case 30, 31, 32, 33, 34, 35 -> currentPose = walkPose4;
        }
    }

    // Update minions
    for (Monster minion : minions) {
        minion.update(heroX, heroY);
    }
}
   public void attackhero(double heroX,double heroY)
   {
       if (Math.abs(heroX - x) < 100 && Math.abs(heroY - y) < 100) {
            System.out.println("Hero takes damage from punching!");
            // Add damage logic here
            attacking=true;
        }
       else attacking=false;
       
   }
   public boolean damagefrompunches()
   {
       if(attacking==true)
         {return true ;}
       else 
           return false;
       
   }


private void updateSmashAnimation() {
    if (smashInProgress) {
        long elapsed = System.currentTimeMillis() - smashStartTime;

        if (elapsed < smashDuration) {
            double progress = (double) elapsed / smashDuration;

            // Parabolic motion for jump-smash (up and down)
            y = originalY - (Math.sin(progress * Math.PI) * smashHeight);

            // Maintain the smash pose
            currentPose = smashPose;
        } else {
            // Reset position after the smash
            y = originalY;
            smashInProgress = false;
            smashing = false; // Stop the smash animation
            currentPose = holdPose; // Return to idle pose
        }
    }
}


    public void render(GraphicsContext gc) {
        gc.save();

        if (!faceRight) {
            gc.translate(x + width, y);
            gc.scale(-1, 1);
        } else {
            gc.translate(x, y);
        }

        if (currentPose != null) {
            gc.drawImage(currentPose, 0, 0, width, height);
        }

        gc.restore();

        healthBar.render(gc, x, y - 10);

        for (Monster minion : minions) {
            minion.render(gc);
        }
    }

    public boolean collidesWithHero(Hero hero) {
        return x < hero.getX() + hero.getWidth() &&
                x + width > hero.getX() &&
                y < hero.getY() + hero.getHeight() &&
                y + height > hero.getY();
    }

    public void takeDamage(double damage) {
        healthBar.decreaseHealth(damage);
        lastDamageTime = System.currentTimeMillis();
    }

    public boolean isDead() {
        return healthBar.getCurrentHealth() <= 0;
    }

    public boolean collidesWithSword(Polygon swordHitbox) {
        Rectangle bossHitbox = new Rectangle(x, y, width, height);
        Shape intersection = Shape.intersect(bossHitbox, swordHitbox);
        return intersection.getBoundsInLocal().getWidth() > 0;
    }

    private void useRandomAbility(double heroX, double heroY) {
        int ability = (int) (Math.random() * 3);
        switch (ability) {
            case 0 -> groundSmash();
            case 1 -> enrageMode();
        }
    }

 private long lastSummonTime = 0; // To store the last summon time

private void summonMinions() {
    long currentTime = System.currentTimeMillis();

    // Check if 30 seconds have passed since the last summon
    if (currentTime - lastSummonTime >= 30000) {
        // Summon 3 minions when health drops below 100
        if (minions.size() < 3) {
            for (int i = 0; i < 3; i++) {
                minions.add(new Monster(x + i * 50, y + 50)); // Adjust position as needed
            }
            lastSummonTime = currentTime; // Update the last summon time
        }
    }
}


  private void groundSmash() {
    if (!smashInProgress) {
        smashInProgress = true; // Start the smash
        smashStartTime = System.currentTimeMillis();
        originalY = y; // Save the boss's original position
        smashing = true; // Start the smashing animation

        System.out.println("Ground Smash initiated!");

        // Check if the hero is in range       
    }
}
   public boolean herotakedamagefromjump(double heroX,double heroY)        
    {
        long elapsed = System.currentTimeMillis() - smashStartTime;
        if (Math.abs(heroX - x) < 200 && Math.abs(heroY - y) < 200 && smashing == true &&   elapsed >= 550 && elapsed <= 600) {
            System.out.println("Hero takes damage from Ground Smash!");
            // Add damage logic here
            return true;
            
        }
        else return false;
    }


    private void enrageMode() {
        System.out.println("Boss is enraged! Increased speed and attack!");
        speed = 1.0;
    }
}