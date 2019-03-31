package com.example.demo.service;

import java.util.List;

import com.example.demo.model.TRegistration;
import com.example.demo.model.TUser;
import com.example.demo.model.UserWithRegistration;
import com.example.demo.util.RegistrationException;

public interface RegistrationService {
	default int SaveTUser(TUser tUser) {
		return 0;
		}

	int SaveRegistrationAndTuser(TRegistration registration,TUser tUser) throws Exception;

	List<UserWithRegistration> FindUserWithRegistrationByid(int id);
}
