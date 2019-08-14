package com.github.shiro.entity;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Data;
@Data
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

}
