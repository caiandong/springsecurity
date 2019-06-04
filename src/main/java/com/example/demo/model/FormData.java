package com.example.demo.model;

public class FormData {
	private String filename;
	private String message;
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "FormData [filename=" + filename + ", message=" + message + "]";
	}

}
