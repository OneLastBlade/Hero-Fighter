/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

/**
 *
 * @author yessine
 */
import java.util.Random;

public class RandomBooleanGenerator {
    private static Random random = new Random();

    public static boolean getRandomBoolean() {
        return random.nextBoolean(); // Returns true or false randomly
    }
}