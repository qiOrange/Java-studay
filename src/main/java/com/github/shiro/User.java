package com.github.shiro;

import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String perm;
	public User() {
	}
	
	public User(String username, String password, String perm) {
		super();
		this.username = username;
		this.password = password;
		this.perm = perm;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getPerm() {
		return perm;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPerm(String perm) {
		this.perm = perm;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
