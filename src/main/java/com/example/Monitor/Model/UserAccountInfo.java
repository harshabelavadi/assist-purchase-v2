package com.example.Monitor.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Component
@Entity
public class UserAccountInfo {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int uid;
	
	@Column(unique = true)
	private String uname;
	
	@Column
	private String password;
	
	@Column
	private boolean admin;

	public UserAccountInfo() {}
	
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
}
