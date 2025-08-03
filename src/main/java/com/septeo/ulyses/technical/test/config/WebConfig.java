package com.septeo.ulyses.technical.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.septeo.ulyses.technical.test.interceptor.MyCustomInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private MyCustomInterceptor loggingInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loggingInterceptor).addPathPatterns("/**");
	}
}
