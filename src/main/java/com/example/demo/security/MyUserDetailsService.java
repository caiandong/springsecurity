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
import com.example.demo.util.Utils;



@Component
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private URRMapper urm;
	private final String pefix = "{bcrypt}";
	private final String Role="ROLE_";
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserRole roleByName = urm.FindUserRoleByName(username);
		if (roleByName == null)
			throw new UsernameNotFoundException("没有此用户");
		Set<GrantedAuthority> hashSet = new HashSet<>();
		// 第三次
		Utils.zhuanhuan(roleByName.getTrole(), hashSet, role -> {
			return new SimpleGrantedAuthority(Role+role.getRoleKey());
		});
//		第二次
//		roleByName.getTrole().stream().
//		forEach(role->{hashSet.add(new SimpleGrantedAuthority(role.getRoleKey()));});
//			第一次
//		for(TRole role:roleByName.getTrole())
//		hashSet.add(new SimpleGrantedAuthority(role.getRoleKey()));
		return new User(roleByName.getUsername(), pefix + passwordEncoder.encode(roleByName.getPassword()), hashSet);
	}

}
