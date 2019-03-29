package com.example.demo.security;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@Import({MyFilterInvocationSecurityMetadataSource.class})
public class SecurityConfiguration {

	@Bean
	public PersistentTokenRepository jdbcTokenRepositoryImpl(DataSource dataSource) {
		JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		jdbcTokenRepositoryImpl.setDataSource(dataSource);
		return jdbcTokenRepositoryImpl;
	}
	@Bean
	public MyDAOAuthorizationConfigurer myDAOAuthorizationConfigurer(FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource) {
		 MyDAOAuthorizationConfigurer myDAOAuthorizationConfigurer = new MyDAOAuthorizationConfigurer();
		 myDAOAuthorizationConfigurer.setFilterInvocationSecurityMetadataSource(filterInvocationSecurityMetadataSource);
		 return myDAOAuthorizationConfigurer;
	}
//	@Bean
//	public DAOAuthorization filterInvocationSecurityMetadataSource(FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource) {
//		DAOAuthorization daoAuthorization = new DAOAuthorization();
//		daoAuthorization.setAccessDecisionManager(new AffirmativeBased(Arrays.asList(new RoleVoter())));
//		daoAuthorization.setSecurityMetadataSource(filterInvocationSecurityMetadataSource);
//		
//		return daoAuthorization;
//	}
}
