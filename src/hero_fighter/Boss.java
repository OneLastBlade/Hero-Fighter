package hero_fighter;



import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.util.ArrayList;
import java.util.List;

public class Boss {

    private double x, y; // Boss's position
    private double speed = 0.6; // Speed for the boss (slower but stronger)
    private double width = 150; // Boss's width
    private double height = 150; // Boss's height

    // Walking animation frames
    private final Image walkPose1 = loadImage("/hero_fighter/ressources/boss/walk4.png");
    private final Image walkPose2 = loadImage("/hero_fighter/ressources/boss/walk5.png");
    private final Image walkPose3 = loadImage("/hero_fighter/ressources/boss/walk6.png");
    private final Image walkPose4 = loadImage("/hero_fighter/ressources/boss/walk7.png");
    private final Image holdPose = loadImage("/hero_fighter/ressources/boss/hold.png");

    // Smashing animation frames
    private final Image smashPose1 = loadImage("/hero_fighter/ressources/boss/smash1.png");
    private final Image smashPose2 = loadImage("/hero_fighter/ressources/boss/smash2.png");
    private final Image smashPose3 = loadImage("/hero_fighter/ressources/boss/smash3.png");

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
    private long smashStartTime = 0; // Time when the smash animation starts

    public Boss(double x, double y) {
        this.x = x;
        this.y = y;
        this.healthBar = new HealthBar(300); // Boss has more health
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
        if (!smashing) {
            // Move horizontally towards the hero
            if (x < heroX) {
                x += speed;
                faceRight = true;
            } else if (x > heroX) {
                x -= speed;
                faceRight = false;
            }
        }

        // Special mode when health is below a threshold
        if (!enraged && healthBar.getCurrentHealth() < 150) {
            enraged = true;
            speed = 1.2; // Increase speed when enraged
        }

        // Summon minions if health falls below a threshold (e.g., 100)
        if (healthBar.getCurrentHealth() < 100) {
            summonMinions(); // Summon minions when health drops below 100
        }

        // Regenerate health if not attacked for 5 seconds
        if (System.currentTimeMillis() - lastDamageTime > 5000) {
            healthBar.increaseHealth(1);
        }

        // Use special abilities every 8 seconds
        if (System.currentTimeMillis() - lastAbilityTime > 8000) {
            useRandomAbility(heroX, heroY);
            lastAbilityTime = System.currentTimeMillis();
        }

        // Walking animation (only if not smashing)
        if (!smashing) {
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

    private void updateSmashAnimation() {
        if (smashing) {
            smashFrame++;
            long elapsedTime = System.currentTimeMillis() - smashStartTime;

            // Cycle through smash animation frames
            if (elapsedTime < 300) {
                currentPose = smashPose1;
            } else if (elapsedTime < 600) {
                currentPose = smashPose2;
            } else if (elapsedTime < 900) {
                currentPose = smashPose3;
            } else {
                // End smashing animation
                smashing = false;
                currentPose = holdPose; // Reset to default pose
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
            case 0 -> groundSmash(heroX, heroY);
            case 1 -> enrageMode();
        }
    }

    private void summonMinions() {
        // Summon 3 minions when health drops below 100
        if (minions.size() < 3) {
            for (int i = 0; i < 3; i++) {
                minions.add(new Monster(x + i * 50, y)); // Adjust position as needed
            }
        }
    }

    private void groundSmash(double heroX, double heroY) {
        if (!smashing) {
            smashing = true; // Enter smash state
            smashStartTime = System.currentTimeMillis();
            smashFrame = 0; // Start smash animation from the first frame
            System.out.println("Ground Smash! Hero takes damage!");

            // Check if the hero is in range
            if (Math.abs(heroX - x) < 200 && Math.abs(heroY - y) < 200) {
                System.out.println("Hero takes damage from Ground Smash!");
                // Apply damage logic to the hero here
            }
        }
    }

    private void enrageMode() {
        System.out.println("Boss is enraged! Increased speed and attack!");
        speed = 1.5;
    }
}