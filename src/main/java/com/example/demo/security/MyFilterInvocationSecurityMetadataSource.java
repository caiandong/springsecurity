package com.example.demo.security;


import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import com.example.demo.dao.URRMapper;
import com.example.demo.model.ResourceRole;
import com.example.demo.util.Utils;



@Component
public class MyFilterInvocationSecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private final String Role="ROLE_";
	
	@Autowired
	private URRMapper mapper;

	private  Map<String, Collection<ConfigAttribute>> requestMap=
			new HashMap<>();


	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> allAttributes = new HashSet<>();

		for (Map.Entry<String, Collection<ConfigAttribute>> entry : requestMap
				.entrySet()) {
			allAttributes.addAll(entry.getValue());
		}

		return allAttributes;
	}

	public Collection<ConfigAttribute> getAttributes(Object object) {
		final HttpServletRequest request = ((FilterInvocation) object).getRequest();
		String requestURI = request.getRequestURI();
		//initl();
		
		for (Map.Entry<String, Collection<ConfigAttribute>> entry : requestMap
				.entrySet()) {
			if (requestURI.contains(entry.getKey())) {
				return entry.getValue();
			}
		}
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}
	@EventListener({ContextRefreshedEvent.class})
	public void initl() {
		logger.debug("初始化所有权限");
		List<ResourceRole> resourceRole = mapper.FindAllResourceRole();
		System.out.println(resourceRole);
		resourceRole.stream().forEach(ur->{
			requestMap.put(ur.getResUrl(), 
					Utils.zhuanhuan(ur.getTrole(), new HashSet<>(), role->{
						return new SecurityConfig(Role+role.getRoleKey());
					}));
		});
//		List<UserRole> findAllUserRole = mapper.FindAllUserRole();
//		System.out.println(findAllUserRole);
//		findAllUserRole.stream().forEach(ur->{
//			requestMap.put(ur.getUsername(), 
//					Utils.zhuanhuan(ur.getTrole(), new HashSet<>(), role->{
//						return new SecurityConfig(role.getRoleKey());
//					}));
//			});
	}
}
