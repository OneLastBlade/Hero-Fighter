	package select_game;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Game_selected {
    private final ImageView gameLogo; // Holds the level image
    private final int gameLevel;      // The level of the game
    private final boolean locked;     // Indicates if the level is locked or not

    // Constructor
    public Game_selected(int gameLevel, int playerLevel) {
        this.gameLevel = gameLevel;

        // Determine if the level is locked or unlocked
        this.locked = this.gameLevel >= playerLevel;

        // Initialize the gameLogo ImageView
        this.gameLogo = new ImageView();
        String imagePath = locked 
            ? "/Gamge_selected_icons/level_locked.png"
            : "/Gamge_selected_icons/level_unlocked.png";
        this.gameLogo.setImage(new Image(getClass().getResourceAsStream(imagePath)));
    }

    // Getter for gameLogo
    public ImageView getGameLogo() {
        return gameLogo;
    }

    // Getter for gameLevel
    public int getGameLevel() {
        return gameLevel;
    }

    // Getter for locked status
    public boolean isLocked() {
        return locked;
    }

    // Create a label representing the game level
    public Label getLevelLabel() {
        Label label = new Label(String.format("Level: %d", this.gameLevel));
        label.getStyleClass().add("game-level-label"); // Add a style class for customization
        return label;
    }
}
