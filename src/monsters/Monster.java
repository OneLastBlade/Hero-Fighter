/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monsters;

import javafx.scene.image.Image;
import utility.Position;
import utility.Power;

/**
 *
 * @author yessine
 */
public abstract class Monster {
    private String monster_name ;
    private Position monster_position ;
    private Power monster_power ;
    private int monster_level ;
    private Image skin_monster ; 
    private char Difficulty ;
    
	public Monster(String monster_name, Position monster_position, Power monster_power, int monster_level,
			char difficulty,String skin_image) {
		super();
		this.monster_name = monster_name;
		this.monster_position = monster_position;
		this.monster_power = monster_power;
		this.monster_level = monster_level;
		Difficulty = difficulty;
		this.skin_monster= new Image(getClass().getResourceAsStream(skin_image));
	}

	public String getMonster_name() {
		return monster_name;
	}

	public void setMonster_name(String monster_name) {
		this.monster_name = monster_name;
	}

	public Position getMonster_position() {
		return monster_position;
	}

	public void setMonster_position(Position monster_position) {
		this.monster_position = monster_position;
	}

	public Power getMonster_power() {
		return monster_power;
	}

	public void setMonster_power(Power monster_power) {
		this.monster_power = monster_power;
	}

	public int getMonster_level() {
		return monster_level;
	}

	public void setMonster_level(int monster_level) {
		this.monster_level = monster_level;
	}

	public Image getSkin_monster() {
		return skin_monster;
	}

	public void setSkin_monster(Image skin_monster) {
		this.skin_monster = skin_monster;
	}

	public void setDifficulty(char difficulty) {
		Difficulty = difficulty;
	}
	
	public void movement() {
		// a définir ulterieuement 
	}
	
	public void dammge() {
		// a définir ulterieuement 
	}
	
	public boolean is_available_level(int level ) {
		return  level <= this.getMonster_level();
	}
	
	public  abstract char getDifficulty() ;
		
	
	
    
    
}
