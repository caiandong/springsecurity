package com.example.demo.serviceimp;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;

import com.example.demo.dao.URRMapper;
import com.example.demo.model.ResourceRole;
import com.example.demo.model.UserRole;
import com.example.demo.service.SecurityService;
import com.example.demo.util.Utils;

@Service
public class SecurityServiceImp implements SecurityService{
	@Autowired
	private URRMapper urrMapper;
	@Cacheable(cacheNames="UserWithRole",unless= "#result==null")
	public UserRole FindUserWithRoleByName(String name){
		System.out.println("从数据库查询用户以及其权限");
		return urrMapper.FindUserRoleByName(name);
	}
	
	public List<ResourceRole> FindAllResourceWithRole(){
		List<ResourceRole> resourceRole = urrMapper.FindAllResourceRole();	
		return resourceRole;
	}

}
