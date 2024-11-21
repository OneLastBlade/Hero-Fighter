/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipement;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 *
 * @author yessine
 */
public class Armor extends Equipement {
	
   private int health ;
   private int armor ;

	public Armor(String equip_name, String logo_url, int level) {
		super(equip_name, logo_url, level);
		// TODO Auto-generated constructor stub
	}
	
	public Armor(String equip_name, String logo_url, int level, int health, int armor) {
		super(equip_name, logo_url, level);
		this.health = health;
		this.armor = armor;
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

	@Override
	public void Show_caract(Label label ,ImageView image) {
		image.setImage(this.getLogo());
		label.setText(String.format("Health : %d armor : %d",this.getHealth() ,this.getArmor()));
	}
    
}
