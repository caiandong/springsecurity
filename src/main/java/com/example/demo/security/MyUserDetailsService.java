package com.example.demo.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.dao.URRMapper;
import com.example.demo.model.UserRole;
import com.example.demo.service.SecurityService;
import com.example.demo.serviceimp.SecurityServiceImp;
import com.example.demo.util.PasswordDeEncoder;
import com.example.demo.util.Utils;



@Component
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private SecurityService urm;
	
	@Autowired
	private PasswordDeEncoder myPasswordEncoder;
	
	private final String pefix = "{bcrypt}";
	private final String Role="ROLE_";
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserRole roleByName = urm.FindUserWithRoleByName(username);
		
		if (roleByName == null)
			throw new UsernameNotFoundException("没有此用户");
		Set<GrantedAuthority> hashSet = new HashSet<>();
		//把查出来的list角色放到set中
		Utils.zhuanhuan(roleByName.getTrole(), hashSet, role -> {
			return new SimpleGrantedAuthority(Role+role.getRoleKey());
		});
		return new User(roleByName.getUsername(), 
				pefix + passwordEncoder.encode(myPasswordEncoder.decode(roleByName.getPassword())), hashSet);
	}

}
