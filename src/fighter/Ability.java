/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fighter;

import javafx.util.Duration;

/**
 *
 * @author yessine
 */
public class Ability {
	private String ability_name ;
	private String ability_type ;
	private int ability_range ;
	private Boolean scalibility ;
	private Duration  cooldown ;
	
	public Ability(String ability_name, String ability_type, int ability_range, Boolean scalibility,
			Duration cooldown) {
		super();
		this.ability_name = ability_name;
		this.ability_type = ability_type;
		this.ability_range = ability_range;
		this.scalibility = scalibility;
		this.cooldown = cooldown;
	}

	public String getAbility_name() {
		return ability_name;
	}

	public void setAbility_name(String ability_name) {
		this.ability_name = ability_name;
	}

	public String getAbility_type() {
		return ability_type;
	}

	public void setAbility_type(String ability_type) {
		this.ability_type = ability_type;
	}

	public int getAbility_range() {
		return ability_range;
	}

	public void setAbility_range(int ability_range) {
		this.ability_range = ability_range;
	}

	public Boolean getScalibility() {
		return scalibility;
	}

	public void setScalibility(Boolean scalibility) {
		this.scalibility = scalibility;
	}

	public Duration getCooldown() {
		return cooldown;
	}

	public void setCooldown(Duration cooldown) {
		this.cooldown = cooldown;
	}
	
	public boolean ability_activation(Duration time ) {
		return cooldown.compareTo(time) <= 0;
	}
	
	
    
}
