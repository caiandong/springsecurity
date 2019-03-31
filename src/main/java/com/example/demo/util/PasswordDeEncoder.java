package com.example.demo.util;

public interface PasswordDeEncoder {
	 String encode(String rawPassword);
	 String decode(String rawPassword);
}
