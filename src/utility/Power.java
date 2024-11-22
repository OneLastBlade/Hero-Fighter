/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

public class Power {
	private int attack_Dammage ;
	private int range ;
	private int health ;
	private int armor ;
	private int move_speed ;
	
	public Power(int attack_Dammage, int range, int health, int armor, int move_speed) {
		super();
		this.attack_Dammage = attack_Dammage;
		this.range = range;
		this.health = health;
		this.armor = armor;
		this.move_speed = move_speed;
	}

	public int getAttack_Dammage() {
		return attack_Dammage;
	}

	public void setAttack_Dammage(int attack_Dammage) {
		this.attack_Dammage = attack_Dammage;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public int getMove_speed() {
		return move_speed;
	}

	public void setMove_speed(int move_speed) {
		this.move_speed = move_speed;
	}
	
	
	 
    
}
