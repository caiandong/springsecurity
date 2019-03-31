package com.example.demo.util;

import org.springframework.stereotype.Component;

@Component
public class MyPasswordEncode implements PasswordDeEncoder {

	@Override
	public String encode(String rawPassword) {

		return rawPassword.toString();
	}

	@Override
	public String decode(String string) {
		return string;
	}

}
