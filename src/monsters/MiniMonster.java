/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monsters;

import utility.Position;
import utility.Power;

/**
 *
 * @author yessine
 */
public class MiniMonster extends Monster {

	public MiniMonster(String monster_name, Position monster_position, Power monster_power, int monster_level,
			char difficulty, String skin_image) {
		super(monster_name, monster_position, monster_power, monster_level, difficulty, skin_image);
		// TODO Auto-generated constructor stub
	}

	@Override
	public char getDifficulty() {
		// TODO Auto-generated method stub
		return 'E';
	}
    
}
