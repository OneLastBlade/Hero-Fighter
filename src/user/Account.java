/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user;

import java.util.Random;

/**
 *
 * @author yessine
 */
public class Account {
	private String E_mail ; 
	private String pseudo ;
	private String password ;
	public Account(String e_mail, String pseudo, String password) {
		super();
		E_mail = e_mail;
		this.pseudo = pseudo;
		this.password = password;
	}
	
	public String getE_mail() {
		return E_mail;
	}
	
	public void setE_mail(String e_mail) {
		E_mail = e_mail;
	}
	
	public String getPseudo() {
		return pseudo;
	}
	
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	////des methodes a modifié ultérieurement
	public void change_pass() {
		
	}
	
    public void change_mail(){
    	
    }
    
    public int validation_code() {
    	Random random = new Random();
    	int code= 1000+random.nextInt(9000);
    	return  code ;
    }
	
}
