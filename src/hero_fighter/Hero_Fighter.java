package hero_fighter;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import utility.RandomBooleanGenerator;

public class Hero_Fighter extends Application {
    private String heroName="alex";
    private String weapon="gun";
    private Hero hero;
    private Boss boss;
    private boolean isGameOver = false;
    private boolean isGameWon = false;
    private boolean bossSpawned = false; // Track if the boss has spawned
    private boolean bossDefeated = false; // Track if the boss is defeated
    private BackgroundManager backgroundManager;
    private InputHandler inputHandler;
    private GameTimer gameTimer;
    private List<Monster> monsters = new ArrayList<>();
    private long lastSpawnTime = 0;
    private long spawnCooldown = 5000; // Initial spawn cooldown (5 seconds)
    private long redAnimationStart = 0;

    public Hero_Fighter(String heroName, String weapon) {
        this.heroName = heroName;
        this.weapon = weapon;
        this.boss = new Boss(Math.random() < 0.5 ? 0.0 : 1200.0, 450.0);
    }

    public Hero_Fighter() {
        this.boss = new Boss(Math.random() < 0.5 ? 0.0 : 1200.0, 450.0);
    }

    @Override
    public void start(Stage stage) {
        gameTimer = new GameTimer();
        Canvas canvas = new Canvas(1200, 800);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        stage.setResizable(false);
        Scene scene = new Scene(new Pane(canvas), 1200, 800);
        stage.setScene(scene);
        backgroundManager = new BackgroundManager("/hero_fighter/ressources/map/background1yellow.jpg", 1200, 800);
        inputHandler = new InputHandler();

        scene.setOnKeyPressed(e -> inputHandler.handleKeyPress(e.getCode()));
        scene.setOnKeyReleased(e -> inputHandler.handleKeyRelease(e.getCode()));

        hero = new Hero(200, 500, 100, 100, "/hero_fighter/ressources/heros/" + heroName + "/", weapon);

        stage.setTitle("Hero Fighter");
        stage.show();

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!isGameOver) {
                    update();
                    render(gc);
                } else {
                    renderEndScreen(gc);
                }
            }
        };
        gameLoop.start();
    }

    private void update() {
        hero.update(inputHandler, monsters,boss);
        gameTimer.update();

        // End the game if the hero's health reaches 0
        if (hero.getHealth() <= 0) {
            isGameOver = true;
            return;
        }

        // Spawn the boss when the timer runs out
        if (gameTimer.isTimeUp() && !bossSpawned) {
            bossSpawned = true;
            monsters.clear(); // Clear existing monsters
        }

        if (bossSpawned) {
            // Update boss logic
            boss.update(hero.getX(), hero.getY());
            if (boss.damagefrompunches()) {
                hero.takeDamage(1);
            }
            if (boss.herotakedamagefromjump(hero.getX(), hero.getY())) {
                hero.takeDamage(5);
            }
            boss.attackhero(hero.getX(), hero.getY());
            if (boss.isDead()) {
                bossDefeated = true;
                isGameOver = true;
                isGameWon = true; // Mark the game as won
            }
        } else {
            // Spawn regular monsters if the boss hasn't appeared
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastSpawnTime >= spawnCooldown) {
                lastSpawnTime = currentTime;
                spawnCooldown = Math.max(spawnCooldown - 50, 500); // Reduce cooldown to a minimum of 500ms
                monsters.add(new Monster(RandomBooleanGenerator.getRandomBoolean() ? 0 : 1200, 500));
            }

            // Update monsters
            Iterator<Monster> iterator = monsters.iterator();
            while (iterator.hasNext()) {
                Monster monster = iterator.next();
                monster.update(hero.getX(), 500); // Follow the hero

                if (monster.collidesWithHero(hero)) {
                    iterator.remove(); // Remove monster on collision
                    hero.takeDamage(20); // Reduce hero's health
                }
            }
        }

        // Handle bullets and collisions
        handleBulletCollisions();
    }

    private void handleBulletCollisions() {
        List<Bullet> bulletsToRemove = new ArrayList<>();
        List<Monster> monstersToRemove = new ArrayList<>();

        // Check collisions with the boss
        for (Bullet bullet : hero.getBullets()) {
            if (bossSpawned && bullet.collideWith(boss)) {
                bulletsToRemove.add(bullet);
                boss.takeDamage(25);
            }
        }

        // Check collisions with monsters
        for (Monster monster : monsters) {
            if (monster.isDead()) {
                        monstersToRemove.add(monster);
             }
            
            for (Bullet bullet : hero.getBullets()) {
                if (bullet.collidesWith(monster)) {
                    bulletsToRemove.add(bullet);
                    monster.takeDamage(25);
                   
                }
            }
        }

        // Remove processed bullets and monsters
        hero.getBullets().removeAll(bulletsToRemove);
        monsters.removeAll(monstersToRemove);
    }

    private void render(GraphicsContext gc) {
        gc.clearRect(0, 0, 1200, 800);

        // Red flash effect when time is low
        if (gameTimer.getRemainingTime() <= 15) {
            if (redAnimationStart == 0) {
                redAnimationStart = System.currentTimeMillis();
            }
            long elapsed = (System.currentTimeMillis() - redAnimationStart) / 500 % 2;
            if (elapsed == 0) {
                gc.setFill(Color.rgb(255, 0, 0, 0.3));
                gc.fillRect(0, 0, 1200, 800);
            }
        }

        backgroundManager.render(gc);
        hero.render(gc);
        gameTimer.renderTimer(gc, 1200);

        // Render monsters or boss
        if (bossSpawned) {
            boss.render(gc);
        } else {
            for (Monster monster : monsters) {
                monster.render(gc);
            }
        }
    }

    private void renderEndScreen(GraphicsContext gc) {
        gc.clearRect(0, 0, 1200, 800);

        String message;
        Color color;

        if (bossDefeated) {
            message = "You Win!";
            color = Color.GREEN;
        } else {
            message = "Game Over!";
            color = Color.RED;
        }

        gc.setFill(color);
        gc.setFont(javafx.scene.text.Font.font("Arial", 64));
        gc.fillText(message, 450, 400);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
