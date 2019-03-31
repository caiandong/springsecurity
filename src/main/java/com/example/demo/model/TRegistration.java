package com.example.demo.model;

public class TRegistration {
	private int id;
	private String email;
	private String phone;
	private int userId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "TRegistration [id=" + id + ", email=" + email + ", phone=" + phone + ", userId=" + userId + "]";
	}

}
