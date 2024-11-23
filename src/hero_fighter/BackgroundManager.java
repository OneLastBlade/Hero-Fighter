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

public class BackgroundManager {
    private Image background;
    private double width;
    private double height;

    public BackgroundManager(String imagePath, double width, double height) {
        this.width = width;
        this.height = height;
        this.background = new Image(getClass().getResource(imagePath).toExternalForm());    }

    public void render(GraphicsContext gc) {
        gc.drawImage(background, 0, 0, width, height);
    }
}