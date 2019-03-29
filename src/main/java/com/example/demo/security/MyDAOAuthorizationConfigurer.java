package com.example.demo.security;

import java.util.Arrays;

import org.springframework.context.ApplicationContext;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

public class MyDAOAuthorizationConfigurer extends AbstractHttpConfigurer<MyDAOAuthorizationConfigurer, HttpSecurity> {
	
	private FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		DAOAuthorization daoAuthorization = new DAOAuthorization();
		AffirmativeBased affirmativeBased = new AffirmativeBased(Arrays.asList(new RoleVoter()));
		affirmativeBased.setAllowIfAllAbstainDecisions(true);
		daoAuthorization.setAccessDecisionManager(affirmativeBased);
		daoAuthorization.setSecurityMetadataSource(filterInvocationSecurityMetadataSource);
		http.addFilterBefore(daoAuthorization, FilterSecurityInterceptor.class);
	}
	

	public FilterInvocationSecurityMetadataSource getFilterInvocationSecurityMetadataSource() {
		return filterInvocationSecurityMetadataSource;
	}

	public void setFilterInvocationSecurityMetadataSource(
			FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource) {
		this.filterInvocationSecurityMetadataSource = filterInvocationSecurityMetadataSource;
	}

}
