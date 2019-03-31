package com.example.demo.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MyHandlerInterceptor extends HandlerInterceptorAdapter {
@Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
	// TODO Auto-generated method stub
	SecurityContext context=null;
	HttpSession session = request.getSession(false);
	if(session!=null)
		context = (SecurityContext)session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
	if(context==null)
		context=new MySecurityContext();
	SecurityContextHolder.setContext(context);
	return true;
}
@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	SecurityContextHolder.clearContext();
	}
private class MySecurityContext implements SecurityContext{

	@Override
	public Authentication getAuthentication() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAuthentication(Authentication authentication) {
		// TODO Auto-generated method stub
		
	}
	
}
}
