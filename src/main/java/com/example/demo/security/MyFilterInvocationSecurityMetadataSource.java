package com.example.demo.security;


import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
import com.example.demo.service.SecurityService;
import com.example.demo.serviceimp.SecurityServiceImp;
import com.example.demo.util.Utils;



@Component
public class MyFilterInvocationSecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private final String Role="ROLE_";
	
	private volatile boolean isneedrefresh=true;
	
	private ReentrantLock lock=new ReentrantLock();
	
	@Autowired
	private SecurityService SS;
	
	private  Map<String, Collection<ConfigAttribute>> requestMap;


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
		synchronizerefresh();
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
	//@EventListener({ContextRefreshedEvent.class})
	public void refreshmap() {
		logger.debug("初始化所有权限");
		logger.error("初始化所有权限");
		List<ResourceRole> allResourceWithRole=SS.FindAllResourceWithRole();	
		requestMap=new HashMap<>();
		allResourceWithRole.stream().forEach(ur->{	//
			requestMap.put(ur.getResUrl(), 
					Utils.zhuanhuan(ur.getTrole(), new HashSet<>(), role->{
						return new SecurityConfig(Role+role.getRoleKey());
					}));
		});
	}
	public void needrefreshmap() {
		isneedrefresh=true;
	}

	private void synchronizerefresh() {
		//检查是否需要刷新map
				if (isneedrefresh)
					//只允许一个线程刷新，其他线程等待
					synchronized (this) {
						if (isneedrefresh) {
							refreshmap();
							isneedrefresh = false;
						}
					}
	}
	private void trylockrefresh() {
		if (isneedrefresh) {
			try {
				boolean haslock = lock.tryLock(2, TimeUnit.SECONDS);
				//获得锁并且需要刷新
				if (haslock && isneedrefresh) {
					try {
						refreshmap();
					} finally {
						lock.unlock();
					}
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
