package com.example.demo.dao;

import java.util.List;

import com.example.demo.model.ResourceRole;
import com.example.demo.model.UserRole;

public interface URRMapper {
	//获取所有用户角色给userdetailservice
	public List<UserRole> FindAllUserRole();
	public UserRole FindUserRoleByName(String name);
	
	//获取所有资源对应的角色
	public List<ResourceRole> FindAllResourceRole();
	public ResourceRole FindResourceRoleByName(String resource);
}
