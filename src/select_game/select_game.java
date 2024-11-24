package select_game;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class select_game extends Application {
	@FXML
	Button select_map_button ;
	@FXML
	Button option_button ;
	@FXML
	Button select_hero_button ;
	@FXML
	Button check_profile_button ;
	@FXML
    private VBox game1, game2, game3; // Injected VBox elements

    @FXML
    private Label game1Label, game2Label, game3Label; // Injected Label elements

    @FXML
    private ImageView game1Image, game2Image, game3Image; // Injected ImageView elements

    private Game_selected game1Object, game2Object, game3Object;
	

	@Override
	public void start(Stage primaryStage) throws Exception {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("select_game.fxml"));
	    AnchorPane root = loader.load(); // Load the FXML file

	    primaryStage.setTitle("Select Game");
	    primaryStage.setScene(new Scene(root, 800, 400)); // Set the scene with dimensions
	    primaryStage.show();

	    // Retrieve the controller and initialize games
	    select_game controller = loader.getController();
	    controller.initializeGames();
	}

	public void initializeGames() {
        game1Object = new Game_selected(1, 1);
        game2Object = new Game_selected(0, 2);
        game3Object = new Game_selected(0, 3);

        // Update UI for Game 1
        if (game1 != null) {
            game1Label.setText(game1Object.getLevelLabel().getText());
            game1Image.setImage(game1Object.getGameLogo().getImage());
        }

        // Update UI for Game 2
        if (game2 != null) {
            game2Label.setText(game2Object.getLevelLabel().getText());
            game2Image.setImage(game2Object.getGameLogo().getImage());
        }

        // Update UI for Game 3
        if (game3 != null) {
            game3Label.setText(game3Object.getLevelLabel().getText());
            game3Image.setImage(game3Object.getGameLogo().getImage());
        }
    }

	public static void main(String[] args) {
        launch(args);
    }
}
