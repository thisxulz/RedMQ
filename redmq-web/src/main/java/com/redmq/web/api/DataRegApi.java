package com.redmq.web.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.redmq.web.model.HttpConstants;
import com.redmq.web.model.ServiceResult;
import com.redmq.web.service.DataRegService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @title 
 * @author xulz
 * @date 2019年1月24日下午5:55:06
 */
@RestController
@RequestMapping(value="/api")
public class DataRegApi {
	private static final Logger log = LoggerFactory.getLogger(DataRegApi.class);
	
	@Autowired
	private DataRegService dataRegService;
	
	@RequestMapping(value = "/regTopic/{topic}", method = RequestMethod.GET)
	@ApiOperation(value = "注册topic",notes = "注册topic")
	@ApiImplicitParams({//请求参数描述
		@ApiImplicitParam(name = "topic", value = "消息主题", required = true, dataTypeClass = String.class),
	})
	public ServiceResult regTopic(@PathVariable("topic") String topic) {
		ServiceResult serviceResult = null;
		int result = dataRegService.regTopic(topic);
		if(result == 1) {
			serviceResult = new ServiceResult(HttpConstants.RESUTL_OK, "注册成功");		
		}else if(result == 2){
			serviceResult = new ServiceResult(HttpConstants.RESUTL_FAIL, "注册失败，topic已存在");
		}else {
			serviceResult = new ServiceResult(HttpConstants.RESUTL_FAIL, HttpConstants.CODE_EXCEPTION, true);
		}
		return serviceResult;
	}
	
	@RequestMapping(value = "/regGroup/{topic}/{group}", method = RequestMethod.GET)
	@ApiOperation(value = "注册消费者group",notes = "注册消费者group")
	@ApiImplicitParams({//请求参数描述
		@ApiImplicitParam(name = "topic", value = "消息主题", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "group", value = "消费者分组", required = true, dataTypeClass = String.class),
	})
	public ServiceResult regGroup(@PathVariable("topic") String topic, @PathVariable("group") String group) {
		ServiceResult serviceResult = null;
		int result = dataRegService.regGroup(topic, group);
		if(result == 1) {
			serviceResult = new ServiceResult(HttpConstants.RESUTL_OK, "注册成功");		
		}else if(result == 2){
			serviceResult = new ServiceResult(HttpConstants.RESUTL_FAIL, "注册失败，topic不存在");
		}else {
			serviceResult = new ServiceResult(HttpConstants.RESUTL_FAIL, HttpConstants.CODE_EXCEPTION, true);
		}
		return serviceResult;
	}
}
