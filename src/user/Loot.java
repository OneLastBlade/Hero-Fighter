
package user;

import java.util.ArrayList;
import equipement.Equipement;

public class Loot {
	ArrayList<Equipement>  Equip_list ;

	public Loot() {
		super();
	}
	
	public Boolean is_in_loot(Equipement e) {
		
	  return  Equip_list.contains(e) ;
		
	}
	
	public void show_loot(javafx.scene.control.Label label) {
		
		// A definir ulterieurment ...
	}
	
	
}
