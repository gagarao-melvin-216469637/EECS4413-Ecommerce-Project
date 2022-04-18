package org.o7planning.krispykart.form;

import org.o7planning.krispykart.entity.Registration;
import org.springframework.web.multipart.MultipartFile;

public class RegistrationForm {
	
	private String username;
	private int active;
	private int encryptedPassword;
	private String user_role;
	
	private boolean newAccount = false;
	
	private MultipartFile fileData;
	
	public RegistrationForm() {
        this.newAccount= true;
    }
	
	public RegistrationForm(Registration registration) {
        this.username = registration.getUsername();
        this.active = registration.getActive();
        this.encryptedPassword = registration.getEncryptedPassword();
        this.user_role = registration.getUser_role();
    }

	/*
	 * Auto generated getters and setters
	 */
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

	public boolean isNewAccount() {
		return newAccount;
	}

	public void setNewAccount(boolean newAccount) {
		this.newAccount = newAccount;
	}

	public MultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(MultipartFile fileData) {
		this.fileData = fileData;
	}
	

}
