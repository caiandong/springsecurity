package com.example.demo.model;

public class TUser {
	private int id;
	private String username;
	private String password;
	private int enable;
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public int getEnable() {
		return enable;
	}
	public void setEnable(int enable) {
		this.enable = enable;
	}
	@Override
	public String toString() {
		return "TUser [id=" + id + ", username=" + username + ", password=" + password + ", enable=" + enable + "]";
	}

}
