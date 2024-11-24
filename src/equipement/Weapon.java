/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipement;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import utility.Position;

/**
 *
 * @author yessine
 */
public class Weapon extends Equipement {
	
	private int attack_dammage ;
	private int rage_to_dammage ;
	
	public Weapon(String equip_name, String logo_url, int level) {
		super(equip_name, logo_url, level);
		// TODO Auto-generated constructor stub
	}
	
	public Weapon(String equip_name, String logo_url, int level, int attack_dammage, int rage_to_dammage) {
		super(equip_name, logo_url, level);
		this.attack_dammage = attack_dammage;
		this.rage_to_dammage = rage_to_dammage;
	}


	public int getAttack_dammage() {
		return attack_dammage;
	}


	public void setAttack_dammage(int attack_dammage) {
		this.attack_dammage = attack_dammage;
	}


	public int getRage_to_dammage() {
		return rage_to_dammage;
	}


	public void setRage_to_dammage(int rage_to_dammage) {
		this.rage_to_dammage = rage_to_dammage;
	}


	@Override
	public void Show_caract(Label label,ImageView image) {
		image.setImage(this.getLogo());
		label.setText(String.format("Attack dammage : %d \nRage of dammage : %d",this.getAttack_dammage() ,this.getRage_to_dammage()));
				
	}

    public void setPosition(Position position) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
