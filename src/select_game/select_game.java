package select_game;

import hero_fighter.Hero_Fighter;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.Profil;

public class select_game extends Application {

    // Profile attributes
    static String E_mail = "medamir071@gmail.com";
    static String Pseudo = "miromiro007";
    static String password = "xxxx";
    static String username = "Player";
    int lvl = 1;

    Profil myProfile = new Profil(E_mail, Pseudo, password, username, lvl);

    // FXML elements for profile and hero selection
    @FXML
    private VBox profil_vbox;
    @FXML
    private Label E_mail_label, Pseudo_label, username_label, level_label;
    @FXML
    private ImageView selected_hero;
    @FXML
    private Label shl;
    @FXML
    private RadioButton SYNDRA_radio, ALEX_radio, CRISTIAN_radio;
    @FXML
    private RadioButton gun_radio , sword_radio ;
    @FXML
    private ToggleGroup HERO;
    @FXML
    private ToggleGroup WEAPON ;

    // Static variable to store the selected champion globally
    private static String selectedChampion = "";
    private static String selectedWeapon ="";

    public static String getSelectedChampion() {
        return selectedChampion;
    }
    
    public static String getSelectedWeapon() {
    	return selectedWeapon ;
    }

    public static void setSelectedChampion(String champion) {
        selectedChampion = champion;
    }
    
    public static void setSelectedWeapon(String weapon) {
    	selectedWeapon =weapon ;
    }

    // FXML elements for game selection
    @FXML
    private AnchorPane mainContainer;
    @FXML
    private Button option_btn, map_btn, hero_btn, profle_btn;
    @FXML
    private VBox game1, game2, game3;
    @FXML
    private Label game1Label, game2Label, game3Label;
    @FXML
    private ImageView game1Image, game2Image, game3Image;
    @FXML
    private Slider sound_slide;
    @FXML
    private HBox menu_bar;

    private static Game_selected game1Object, game2Object, game3Object;
    private String currentFXML = "hero.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception {
        loadScene(primaryStage, currentFXML);
    }

    private void loadScene(Stage stage, String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        AnchorPane root = loader.load();

        select_game controller = loader.getController();
        controller.currentFXML = fxmlFile;
        controller.initializeGames();

        Scene scene = new Scene(root, 800, 400);
        stage.setTitle("Select Game");
        stage.setScene(scene);
        stage.show();
    }
    //weapon selection logic 
    public void selectWeapon(ActionEvent e) {
    	if(gun_radio.isSelected()) {
    		setSelectedWeapon("gun");
    	}else if (sword_radio.isSelected()) {
    		setSelectedWeapon("sword");
    	}
    	System.out.println(getSelectedWeapon());
    }
  
    // Hero selection logic
    public void select_hero(ActionEvent e) {
        if (ALEX_radio.isSelected()) {
            setSelectedChampion("alex");
            shl.setText(getSelectedChampion());
            selected_hero.setImage(new Image(getClass().getResourceAsStream("/champions_logo/alex_talk.png")));
        } else if (SYNDRA_radio.isSelected()) {
            setSelectedChampion("synda");
            shl.setText(getSelectedChampion());
            selected_hero.setImage(new Image(getClass().getResourceAsStream("/champions_logo/synda_talk.png")));
        } else if (CRISTIAN_radio.isSelected()) {
            setSelectedChampion("cristian");
            shl.setText(getSelectedChampion());
            selected_hero.setImage(new Image(getClass().getResourceAsStream("/champions_logo/cristian_talk.png")));
        }
        System.out.println(getSelectedChampion()); // Debugging
    }

    // Update hero selection when returning to the hero selection screen
    public void update_hero_selection() {
        System.out.println("Selected champion: " + getSelectedChampion()); // Debugging

        // Use .equals() for string comparison
        if ("alex".equals(getSelectedChampion())) {
            ALEX_radio.setSelected(true);
            SYNDRA_radio.setSelected(false);
            CRISTIAN_radio.setSelected(false);
        } else if ("synda".equals(getSelectedChampion())) {
            SYNDRA_radio.setSelected(true);
            ALEX_radio.setSelected(false);
            CRISTIAN_radio.setSelected(false);
        } else if ("cristian".equals(getSelectedChampion())) {
            CRISTIAN_radio.setSelected(true);
            SYNDRA_radio.setSelected(false);
            ALEX_radio.setSelected(false);
        }

        // Update the hero label and image based on the selected champion
        shl.setText(getSelectedChampion());
        if ("alex".equals(getSelectedChampion())) {
            selected_hero.setImage(new Image(getClass().getResourceAsStream("/champions_logo/alex_talk.png")));
        } else if ("synda".equals(getSelectedChampion())) {
            selected_hero.setImage(new Image(getClass().getResourceAsStream("/champions_logo/synda_talk.png")));
        } else if ("cristian".equals(getSelectedChampion())) {
            selected_hero.setImage(new Image(getClass().getResourceAsStream("/champions_logo/cristian_talk.png")));
        }
    }
    
    public void update_weapon_selection() {
        System.out.println("Selected weapon: " + getSelectedWeapon()); // Debugging

        // Check the currently selected weapon and update the radio buttons
        if ("gun".equals(getSelectedWeapon())) {
            gun_radio.setSelected(true);
            sword_radio.setSelected(false);
        } else if ("".equals(getSelectedWeapon())) {
            sword_radio.setSelected(true);
            gun_radio.setSelected(false);
        } else {
            // If no weapon is selected, reset the radio buttons (optional)
            gun_radio.setSelected(false);
            sword_radio.setSelected(false);
        }
    }

    // Update profile details
    public void updateProfile() {
        level_label.setText(String.valueOf(myProfile.getLevel()));
        username_label.setText(myProfile.getUsername());
        E_mail_label.setText(myProfile.getE_mail());
        Pseudo_label.setText(myProfile.getPseudo());
    }

    // Initialize games based on profile level
    public void initializeGames() {
        int i = myProfile.getLevel();
        if (game1Object == null) {
            game1Object = new Game_selected(1, i);
            game2Object = new Game_selected(2, i);
            game3Object = new Game_selected(3, i);
        }

        updateGameUI(game1, game1Label, game1Image, game1Object);
        updateGameUI(game2, game2Label, game2Image, game2Object);
        updateGameUI(game3, game3Label, game3Image, game3Object);
    }

    // Update the game UI elements
    private void updateGameUI(VBox gameBox, Label gameLabel, ImageView gameImage, Game_selected game) {
        if (gameBox != null) {
            gameLabel.setText(game.getLevelLabel().getText());
            gameImage.setImage(game.getGameLogo().getImage());
        }
    }

    // Scene switching logic
    @FXML
    private void handleOptionButtonAction(ActionEvent event) {
        switchScene("Options1.fxml", event);
    }

    @FXML
    private void handleMapButtonAction(ActionEvent event) {
        switchScene("select_game.fxml", event);
    }

    @FXML
    private void handleHeroButtonAction(ActionEvent event) {
        switchScene("hero.fxml", event);
    }

    @FXML
    private void handleProfilButtonAction(ActionEvent event) {
        switchScene("profil.fxml", event);
    }

    // Switch between scenes
    public void switchScene(String fxmlFile, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            AnchorPane root = loader.load();

            select_game controller = loader.getController();
            controller.currentFXML = fxmlFile;

            // Ensure the champion selection is updated when switching scenes
            if ("select_game.fxml".equals(fxmlFile)) {
                controller.initializeGames();
            }

            if ("profil.fxml".equals(fxmlFile)) {
                controller.updateProfile();
            }

            if ("hero.fxml".equals(fxmlFile)) {
            	controller.update_weapon_selection();//Ensure the weapon selection is updated ;
                controller.update_hero_selection();  // Ensure the hero selection is updated
            }

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene newScene = new Scene(root, 800, 400);
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


public void onGameStart(ActionEvent event) {
    try {
        // Retrieve the current Stage from the event source
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        
        // Create and start the Hero_Fighter game on the current Stage
        Hero_Fighter heroFighter = new Hero_Fighter(getSelectedChampion(),getSelectedWeapon());
        heroFighter.start(currentStage);
    } catch (Exception e) {
        e.printStackTrace();
        System.err.println("Failed to start Hero_Fighter: " + e.getMessage());
    }
}
    

    public static void main(String[] args) {
        launch(args);
    }
}
