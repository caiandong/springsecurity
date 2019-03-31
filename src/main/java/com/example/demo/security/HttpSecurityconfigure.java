package com.example.demo.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
public class HttpSecurityconfigure extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PersistentTokenRepository jdbcTokenRepositoryImpl;

	@Autowired
	private UserDetailsService myUserDetailsService;
	
	@Autowired
	private FilterInvocationSecurityMetadataSource securityMetadataSource;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/*","/js/*","/vendor/*","/imgs/*","/favicon.ico");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.antMatcher("/root/*")
		//.addFilterBefore(authorization,FilterSecurityInterceptor.class)
		//.and()
		.authorizeRequests()
			//.antMatchers("/denglu").permitAll()
			.anyRequest().authenticated()
			.and()
		.formLogin().loginPage("/login").permitAll()
			.loginProcessingUrl("/root/login")
			.defaultSuccessUrl("/index")
			.failureUrl("/root/login?error")
		.and()
		.csrf().disable()
		.rememberMe().tokenRepository(jdbcTokenRepositoryImpl)
		.and()
		.apply(new MyDAOAuthorizationConfigurer());
		http.logout()
			.logoutUrl("/root/logout")
			
			.logoutSuccessUrl("/login");
//			.deleteCookies("dad")
//			.invalidateHttpSession(true);
		//super.configure(http);
		//http.rememberMe();
		//http.csrf().disable();
		//http.addFilterBefore(filter, beforeFilter)
	}

	private class MyDAOAuthorizationConfigurer extends AbstractHttpConfigurer<MyDAOAuthorizationConfigurer, HttpSecurity> {	
		@Override
		public void configure(HttpSecurity http) throws Exception {
			DAOAuthorization daoAuthorization = new DAOAuthorization();
			AffirmativeBased affirmativeBased = new AffirmativeBased(Arrays.asList(new RoleVoter()));
			affirmativeBased.setAllowIfAllAbstainDecisions(true);
			daoAuthorization.setAccessDecisionManager(affirmativeBased);
			daoAuthorization.setSecurityMetadataSource(securityMetadataSource);
			http.addFilterBefore(daoAuthorization, FilterSecurityInterceptor.class);
		}
	}
}
