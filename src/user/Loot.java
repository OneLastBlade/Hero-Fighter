
package user;

import java.util.ArrayList;
import equipement.Equipement;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class Loot<T extends Equipement> {
	ArrayList<T>  Equip_list ;

	public Loot() {
		super();
	}
	
	public Boolean is_in_loot(T e) {
		
	  return  Equip_list.contains(e) ;
		
	}
	
	public void show_loot(ArrayList<Label> label_list , ArrayList<ImageView> image_list) {
		int label_size= label_list.size();
		int image_size= image_list.size();
		int equip_size= Equip_list.size();
	  
		for(int i=0 ; i< findMinimum(label_size,image_size, equip_size);i++) {
			//Equip_list.get(i).Show_caract(label_list.get(i),image_list.get(i) );
		}
		
	}
	public static int findMinimum(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }
	
	
}
