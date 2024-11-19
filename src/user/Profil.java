/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user;

import java.util.Map;

/**
 *
 * @author yessine
 */
public class Profil extends Account{
	private String username ;
	private int level ; 
	private Map<String,Integer> States ;
	
	public Profil(String e_mail, String pseudo, String password) {
		super(e_mail, pseudo, password);
	}

	public Profil(String e_mail, String pseudo, String password, String username, int level) {
		super(e_mail, pseudo, password);
		this.username = username;
		this.level = level;
		
		States.put("number of game",0);
		States.put("number of wins",0);
			
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Map<String, Integer> getStates() {
		return States;
	}

	public void setStates(Map<String, Integer> states) {
		States = states;
	}
	
	public void increment_level() {
		this.level++;
	}
	
	public void increment_states(Boolean win) {
		Integer new_number_of_games=this.States.get("number of game")+1;
		Integer new_number_of_win= this.States.get("number of wins");
		this.States.put("number of game",new_number_of_games);
		
		if(win) // win
		{
		   	new_number_of_win++;
		   	this.States.put("number of game",new_number_of_games);
		}	
	}
	
	//des methodes a modifié ultérieurement
	
	public void show_username(javafx.scene.control.Label label) {
	    String user_name = this.username;
	    label.setText(user_name);
	}

	public void show_level(javafx.scene.control.Label label) {
	    int user_level = this.level;
	    label.setText(String.format("Your level is %d", user_level));
	}

	public void show_states(javafx.scene.control.Label label) {
		double win_rate ;
		
		if(this.States.get("number of game")!=0) {
			win_rate= (this.States.get("number of game")/this.States.get("number of wins"))*100;
		}
		else {
			win_rate =0 ;
		}
		
		label.setText(String.format("Winrate : %.2f%%", win_rate));
			
	}
	    
}
