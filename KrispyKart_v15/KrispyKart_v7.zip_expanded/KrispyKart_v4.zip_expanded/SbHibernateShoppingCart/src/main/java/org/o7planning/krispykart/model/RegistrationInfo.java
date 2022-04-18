package org.o7planning.krispykart.model;

import org.o7planning.krispykart.entity.Registration;

public class RegistrationInfo {

	private String username;
	private int active;
	private int encryptedPassword;
	private String user_role;
	
	public RegistrationInfo() {}
	
	public RegistrationInfo(Registration reg) {
		this.username = reg.getUsername();
		this.active = reg.getActive();
		this.encryptedPassword = reg.getEncryptedPassword();
		this.user_role = reg.getUser_role();
	}
	
	public RegistrationInfo(String username, int active, int encryptedPassword, String user_role) {
		this.username = username;
		this.active = active;
		this.encryptedPassword = encryptedPassword;
		this.user_role = user_role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(int encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getUser_role() {
		return user_role;
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}
	
	
	
}
