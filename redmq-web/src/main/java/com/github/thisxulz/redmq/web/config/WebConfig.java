package com.github.thisxulz.redmq.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/statics/**").addResourceLocations("/WEB-INF/statics/");
		registry.addResourceHandler("/*.html").addResourceLocations("/WEB-INF/api/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("/WEB-INF/api/webjars/");
	}
	
	@Bean
	public ViewResolver internalResourceViewResolver() {
	    InternalResourceViewResolver bean = new InternalResourceViewResolver();
	    bean.setViewClass(JstlView.class);
	    bean.setPrefix("/WEB-INF/pages/");
	    bean.setSuffix(".jsp");
	    return bean;
	}

	// <mvc:resources mapping="/statics/**" location="/statics/" />
	// <mvc:resources mapping="/favicon.ico" location="/favicon.ico" />
	// <mvc:resources mapping="/*.txt" location="/" />
	// <mvc:resources location="/api/" mapping="/*.html"/>
	// <mvc:resources location="/api/webjars/" mapping="/webjars/**"/>
}
