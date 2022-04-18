package org.o7planning.krispykart.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Accounts")
public class Registration implements Serializable{

	private static final long serialVersionUID = -1234519078147252957L;
	
	@Id // will be used as the primary key for each entity
    @Column(name = "User_Name", length = 20, nullable = false) // sets the mapped column for each field
    private String username;
	
	@Column(name = "Active", nullable = false)
    private int active;
	
	@Column(name = "Encrypted_Password", length = 255, nullable = false)
    private int encryptedPassword;
	
	@Column(name = "User_Role", length = 255, nullable = false)
    private String user_role;
	
	/*
     *  Generated getters and setters on all private attributes
     */
	public Registration() {}

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
