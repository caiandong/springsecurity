package com.example.demo.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.util.RegistrationException;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler({RegistrationException.class})
	public String zhuceyichang() {
		return "404";
	}
}
