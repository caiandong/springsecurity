package com.example.demo.model;

import java.util.List;

public class UserRole {
	private Integer id;

	private String username;

	private String password;

	private Integer enable;

	private List<TRole> trole;

	
	public UserRole() {
	
	}

	public UserRole(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public List<TRole> getTrole() {
		return trole;
	}

	public void setTrole(List<TRole> trole) {
		this.trole = trole;
	}

	@Override
	public String toString() {
		return "UserRole [id=" + id + ", username=" + username + ", password=" + password + ", enable=" + enable
				+ ", trole=" + trole + "]";
	}

}
