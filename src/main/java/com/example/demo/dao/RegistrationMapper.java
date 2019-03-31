package com.example.demo.dao;

import java.util.List;

import com.example.demo.model.TRegistration;
import com.example.demo.model.TUser;
import com.example.demo.model.UserWithRegistration;

public interface RegistrationMapper {
	 int SaveTUser(TUser tUser);
	 int SaveRegistrationInfo(TRegistration registration);
	 List<UserWithRegistration> FindUserWithRegistrationByid(int id);
}
