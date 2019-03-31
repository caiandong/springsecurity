package com.example.demo.service;

import java.util.List;
import java.util.function.Consumer;

import com.example.demo.model.ResourceRole;
import com.example.demo.model.UserRole;

public interface SecurityService {
	UserRole FindUserWithRoleByName(String name);
	List<ResourceRole> FindAllResourceWithRole();
}
