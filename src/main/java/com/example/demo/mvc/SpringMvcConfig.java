package com.example.demo.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

import com.example.demo.util.DirNameConvert;
import com.example.demo.util.Utils;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

	@Autowired
	private MvcProperties mvc;

	@Bean
	@ConditionalOnMissingBean(DirNameConvert.class)
	public DirNameConvert pramerydnc() {
		return new DirNameConvert();
	}

	@Bean
	@Conditional(MyWindowsCondition.class)
	public DirNameConvert dnc() {
		return new DirNameConvert();
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {

//		registry.addMapping("/api/**").allowedOrigins("https://domain2.com").allowedMethods("PUT", "DELETE")
				//.allowedHeaders("header1", "header2", "header3").exposedHeaders("header1", "header2")
//				.allowCredentials(true).
//				.maxAge(3600);

		// Add more mappings...
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		WebMvcConfigurer.super.configurePathMatch(configurer);
//		UrlPathHelper helper = new UrlPathHelper();
//		helper.setRemoveSemicolonContent(false);
//		configurer.setUrlPathHelper(helper);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/hello").setViewName("hello");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/register").setViewName("register");
		registry.addViewController("/root/index").setViewName("index");
		registry.addViewController("/forms").setViewName("forms");
		registry.addViewController("/playmovie").setViewName("playmovie");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//registry.addInterceptor(new MyHandlerInterceptor()).addPathPatterns("/*");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/movie/**").addResourceLocations( mvc.GetMovieDirMapping() );
	}
}
