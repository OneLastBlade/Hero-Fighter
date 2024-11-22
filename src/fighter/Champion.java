/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fighter;

import java.util.ArrayList;

import equipement.Equipement;
import user.Loot;
import utility.Position;
import utility.Power;

/**
 *
 * @author yessine
 */
public class Champion {
	private String champ_name ;
	private Power default_power ;
	private Power champion_power ;
	private Loot<?> player_loot ;
	private Position champion_pos ;
	private ArrayList<Ability> champion_ablity ;
	
	
	public Champion(String champ_name, Power default_power, Power champion_power, Loot<?> player_loot,
			Position champion_pos, ArrayList<Ability> champion_ablity) {
		super();
		this.champ_name = champ_name;
		this.default_power = default_power;
		this.champion_power = champion_power;
		this.player_loot = player_loot;
		this.champion_pos = champion_pos;
		this.setChampion_ablity(champion_ablity);
	}


	public Champion(String champ_name, Power default_power, Power champion_power, Loot<?> player_loot) {
		super();
		this.champ_name = champ_name;
		this.default_power = default_power;
		this.champion_power = champion_power;
		this.player_loot = player_loot;
	}


	public Champion(String champ_name, Power default_power, Power champion_power, Loot<?> player_loot,
			Position champion_pos) {
		super();
		this.champ_name = champ_name;
		this.default_power = default_power;
		this.champion_power = champion_power;
		this.player_loot = player_loot;
		this.champion_pos = champion_pos;
	}


	public String getChamp_name() {
		return champ_name;
	}


	public void setChamp_name(String champ_name) {
		this.champ_name = champ_name;
	}


	public Power getDefault_power() {
		return default_power;
	}


	public void setDefault_power(Power default_power) {
		this.default_power = default_power;
	}


	public Power getChampion_power() {
		return champion_power;
	}


	public void setChampion_power(Power champion_power) {
		this.champion_power = champion_power;
	}


	public Loot<?> getPlayer_loot() {
		return player_loot;
	}


	public void setPlayer_loot(Loot<?> player_loot) {
		this.player_loot = player_loot;
	}


	public Position getChampion_pos() {
		return champion_pos;
	}


	public void setChampion_pos(Position champion_pos) {
		this.champion_pos = champion_pos;
	}


	public ArrayList<Ability> getChampion_ablity() {
		return champion_ablity;
	}


	public void setChampion_ablity(ArrayList<Ability> champion_ablity) {
		this.champion_ablity = champion_ablity;
	}
	
	
}
