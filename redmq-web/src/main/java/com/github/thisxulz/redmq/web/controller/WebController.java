package com.github.thisxulz.redmq.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @title 
 * @author xulz
 * @date 2019年1月24日下午2:32:48
 */
@Controller
public class WebController {
	private static final Logger log = LoggerFactory.getLogger(WebController.class);
	
	@GetMapping("/")
	public String index(Map<String, Object> model) {
		return "index";
	}
	
	@RequestMapping(value = "/groups")
	public String groups(HttpServletRequest request, HttpServletResponse response) {
		return "groups";
	}
	
	@RequestMapping(value = "/msgs")
	public String msgs(HttpServletRequest request, HttpServletResponse response) {
		return "msgs";
	}
	@RequestMapping(value = "/regGroup")
	public String regGroup(HttpServletRequest request, HttpServletResponse response) {
		return "regGroup";
	}
	
	@RequestMapping(value = "/regTopic")
	public String regTopic(HttpServletRequest request, HttpServletResponse response) {
		return "regTopic";
	}
	
	@RequestMapping(value = "/topics")
	public String user(HttpServletRequest request, HttpServletResponse response) {
		return "topics";
	}
}
