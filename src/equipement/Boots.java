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
public class Boots extends Equipement {
	private  int move_speed ;

	public Boots(String equip_name, String logo_url, int level) {
		super(equip_name, logo_url, level);
		// TODO Auto-generated constructor stub
	}
	
	public Boots(String equip_name, String logo_url, int level, int move_speed) {
		super(equip_name, logo_url, level);
		this.move_speed = move_speed;
	}
	
	public int getMove_speed() {
		return move_speed;
	}

	public void setMove_speed(int move_speed) {
		this.move_speed = move_speed;
	}

	@Override
	public void Show_caract(Label label, ImageView image) {
		image.setImage(this.getLogo());
		label.setText(String.format("Movement speed : %d",this.getMove_speed()));
	}
    
}
