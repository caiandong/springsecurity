package com.example.demo.dao;

import java.util.List;

import com.example.demo.model.ResourceRole;
import com.example.demo.model.UserRole;

public interface URRMapper {
	public List<UserRole> FindAllUserRole();
	public UserRole FindUserRoleByName(String name);
	
	public List<ResourceRole> FindAllResourceRole();
	public ResourceRole FindResourceRoleByName(String resource);
}
