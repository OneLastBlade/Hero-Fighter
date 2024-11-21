/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipement;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author yessine
 */
public abstract class Equipement {
	private String equip_name ;
	private Image logo ;
	private int level_to_unlock ;
	public Equipement(String equip_name, String  logo_url, int level) {
		super();
		this.equip_name = equip_name;
		this.logo = new Image(getClass().getResourceAsStream(logo_url));
		this.level_to_unlock = level;
	}
	public String getEquip_name() {
		return equip_name;
	}
	
	public void setEquip_name(String equip_name) {
		this.equip_name = equip_name;
	}
	
	public Image getLogo() {
		return logo;
	}
	
	public void setLogo(Image logo) {
		this.logo = logo;
	}
	
	public int getLevel() {
		return level_to_unlock;
	}
	
	public void setLevel(int level) {
		this.level_to_unlock = level;
	}
	
	public abstract void  Show_caract(Label label ,ImageView image) ;
	
	public boolean is_unlockable(int level) {
		return this.getLevel() < level ;
	}
	
	  
}
