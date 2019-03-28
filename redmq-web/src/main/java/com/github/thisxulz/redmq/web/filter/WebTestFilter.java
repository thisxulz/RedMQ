package com.github.thisxulz.redmq.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter(urlPatterns = "*", filterName = "WebTestFilter")
public class WebTestFilter implements Filter{
	private static final Logger log = LoggerFactory.getLogger(WebTestFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//  Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		//  Auto-generated method stub
		try {
		} catch (Exception e) {
			log.error("doFilter exception", e);
		} finally{
			chain.doFilter(request, response);
		}
		return;
	}

	@Override
	public void destroy() {
		//  Auto-generated method stub
		
	}

}
