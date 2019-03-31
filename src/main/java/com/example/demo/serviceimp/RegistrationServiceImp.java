package com.example.demo.serviceimp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.RegistrationMapper;
import com.example.demo.model.TRegistration;
import com.example.demo.model.TUser;
import com.example.demo.model.UserWithRegistration;
import com.example.demo.service.RegistrationService;
import com.example.demo.util.MyPasswordEncode;
import com.example.demo.util.PasswordDeEncoder;

@Service
public class RegistrationServiceImp implements RegistrationService {

	@Autowired
	private RegistrationMapper registrationMapper;
	
	@Autowired
	private PasswordDeEncoder myPasswordEncode;
	
	@Override
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public int SaveRegistrationAndTuser(TRegistration registration,TUser tUser) throws Exception{
		tUser.setPassword(myPasswordEncode.encode(tUser.getPassword()));
		registrationMapper.SaveTUser(tUser);
		registration.setUserId(tUser.getId());
		registrationMapper.SaveRegistrationInfo(registration);
		return 0;
	}

	@Override
	public List<UserWithRegistration> FindUserWithRegistrationByid(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
