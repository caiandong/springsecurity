package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.TRegistration;
import com.example.demo.model.TUser;
import com.example.demo.service.RegistrationService;
import com.example.demo.util.RegistrationException;

//@Controller
public class UserController {
@Autowired
private RegistrationService registrationService;
@PostMapping("/registration")
public String registration(TUser tUser,BindingResult userResult,
		TRegistration registration,BindingResult RegistrationResult,
		ModelAndView modelAndView) throws RegistrationException {
		
	try {
			registrationService.SaveRegistrationAndTuser(registration, tUser);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RegistrationException("服务器出错，注册异常！");
		}
			return "login";	
}
@RequestMapping("/getuser")
@ResponseBody
public Authentication hha() {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	System.out.println(authentication);
	return authentication;
}
@RequestMapping("/")
public String index() {
	if(SecurityContextHolder.getContext().getAuthentication()!=null)
		return "index";
	return "login";
}
}
