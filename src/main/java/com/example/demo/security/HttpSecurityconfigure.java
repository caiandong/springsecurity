package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
	private MyDAOAuthorizationConfigurer myDAOAuthorizationConfigurer;
	@Autowired
	private UserDetailsService myUserDetailsService;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/*","/js/*","lib","images","fonts");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
//		auth.inMemoryAuthentication().withUser("root").
//		password("{bcrypt}"+new BCryptPasswordEncoder().encode("root")).roles("admin");
		auth.userDetailsService(myUserDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		//.antMatcher("/root/*")
		//.addFilterBefore(authorization,FilterSecurityInterceptor.class)
		//.and()
		.authorizeRequests()
			//.antMatchers("/denglu").permitAll()
			.anyRequest().authenticated()
			.and()
		.formLogin()//.loginPage("/root/login").permitAll()
		//.defaultSuccessUrl("/index").failureUrl("/new?error")
		.and()
		.csrf().disable()
		.rememberMe().tokenRepository(jdbcTokenRepositoryImpl)
		.and()
		.apply(myDAOAuthorizationConfigurer);
//		http.logout()
//			.deleteCookies("dad")
//			.invalidateHttpSession(true);
		//super.configure(http);
		//http.rememberMe();
		//http.csrf().disable();
		//http.addFilterBefore(filter, beforeFilter)
	}

}
