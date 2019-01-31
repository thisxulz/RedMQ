package com.redmq.web.start;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.redmq.web"})
@ServletComponentScan(value = {"com.redmq.web.filter"})
public class TomcatApplication extends SpringBootServletInitializer{
	private static final Logger log = LoggerFactory.getLogger(TomcatApplication.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TomcatApplication.class);
	}

	public static void main(String[] args) {
		log.debug("服务启动");
		SpringApplication.run(TomcatApplication.class, args);
	}
	
	@Bean
	public ServletContextInitializer initializer() {
	    return new ServletContextInitializer() {
	        @Override
	        public void onStartup(ServletContext servletContext) throws ServletException {
	        		//初始化资源配置
	            servletContext.setAttribute("staticDomain", "static.xx.cn");
	        }
	    };
	}
	
	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
		//针对tomcat9以上容器，兼容".xxx.com.cn"的cookie域
		return (factory) -> factory.addContextCustomizers(
				(context) -> context.setCookieProcessor(new LegacyCookieProcessor()));
	}

}
