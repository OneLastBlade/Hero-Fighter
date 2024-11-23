/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hero_fighter;

/**
 *
 * @author yessine
 */
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class InputHandler {
    private boolean up, down, left, right;

    public void handleKeyPress(KeyCode key) {
        if (key == KeyCode.Z) up = true;   // 'Z' key for jumping
        if (key == KeyCode.S) down = true;
        if (key == KeyCode.Q) left = true;
        if (key == KeyCode.D) right = true;
    }

    public void handleKeyRelease(KeyCode key) {
        if (key == KeyCode.Z) up = false;
        if (key == KeyCode.S) down = false;
        if (key == KeyCode.Q) left = false;
        if (key == KeyCode.D) right = false;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }
}

