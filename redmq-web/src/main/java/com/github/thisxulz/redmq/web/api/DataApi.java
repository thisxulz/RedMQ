package com.github.thisxulz.redmq.web.api;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.thisxulz.redmq.web.model.HttpConstants;
import com.github.thisxulz.redmq.web.model.MsgListStatus;
import com.github.thisxulz.redmq.web.model.ServiceResult;
import com.github.thisxulz.redmq.web.service.DataService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @title 
 * @author xulz
 * @date 2019年1月24日下午2:40:16
 */
@Api(value = "DataApi", description = "查询相关接口")
@RestController
@RequestMapping(value="/api")
public class DataApi {
	private static final Logger log = LoggerFactory.getLogger(DataApi.class);
	
	@Autowired
	private DataService dataService;
	
	@RequestMapping(value = "/getAllTopics", method = RequestMethod.GET)
	@ApiOperation(value = "获取所有注册的topics",notes = "获取所有注册的topics")
	@ApiImplicitParams({//请求参数描述
	})
	public ServiceResult getAllTopics() {
		ServiceResult result = null;
		Set<String> data = dataService.getAllTopics();
		if(data != null) {
			result = new ServiceResult(HttpConstants.RESUTL_OK, "");		
			result.setData(data);
		}else {
			result = new ServiceResult(HttpConstants.RESUTL_FAIL, HttpConstants.CODE_EXCEPTION, true);
		}
		return result;
	}
	
	@RequestMapping(value = "/getAllGroups", method = RequestMethod.GET)
	@ApiOperation(value = "获取所有注册的groups",notes = "获取所有注册的groups")
	@ApiImplicitParams({//请求参数描述
	})
	public ServiceResult getAllGroups() {
		ServiceResult result = null;
		Set<String> data = dataService.getAllGroups();
		if(data != null) {
			result = new ServiceResult(HttpConstants.RESUTL_OK, "");		
			result.setData(data);
		}else {
			result = new ServiceResult(HttpConstants.RESUTL_FAIL, HttpConstants.CODE_EXCEPTION, true);
		}
		return result;
	}
	
	@RequestMapping(value = "/getTopicGroups/{topic}", method = RequestMethod.GET)
	@ApiOperation(value = "获取topic下的分组",notes = "获取topic下的分组")
	@ApiImplicitParams({//请求参数描述
        @ApiImplicitParam(name = "topic", value = "消息主题", required = true, dataTypeClass = String.class),
	})
	public ServiceResult getTopicGroups(@PathVariable("topic") String topic) {
		ServiceResult result = null;
		Set<String> data = dataService.getTopicGroups(topic);
		if(data != null) {
			result = new ServiceResult(HttpConstants.RESUTL_OK, "");		
			result.setData(data);
		}else {
			result = new ServiceResult(HttpConstants.RESUTL_FAIL, HttpConstants.CODE_EXCEPTION, true);
		}
		return result;
	}
	
	@RequestMapping(value = "/getGroupTopics/{group}", method = RequestMethod.GET)
	@ApiOperation(value = "获取分组监听的topic",notes = "获取分组监听的topic")
	@ApiImplicitParams({//请求参数描述
        @ApiImplicitParam(name = "group", value = "消费者分组", required = true, dataTypeClass = String.class),
	})
	public ServiceResult getGroupTopics(@PathVariable("group") String group) {
		ServiceResult result = null;
		Set<String> data = dataService.getGroupTopics(group);
		if(data != null) {
			result = new ServiceResult(HttpConstants.RESUTL_OK, "");		
			result.setData(data);
		}else {
			result = new ServiceResult(HttpConstants.RESUTL_FAIL, HttpConstants.CODE_EXCEPTION, true);
		}
		return result;
	}
	
	@RequestMapping(value = "/getMsgList/{topic}/{group}", method = RequestMethod.GET)
	@ApiOperation(value = "获取消息队列",notes = "获取消息队列")
	@ApiImplicitParams({//请求参数描述
		@ApiImplicitParam(name = "topic", value = "消息主题", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "group", value = "消费者分组", required = true, dataTypeClass = String.class),
	})
	public ServiceResult getMsgList(@PathVariable("topic") String topic, @PathVariable("group") String group) {
		ServiceResult result = null;
		MsgListStatus data = dataService.getMsgList(topic, group);
		if(data != null) {
			result = new ServiceResult(HttpConstants.RESUTL_OK, "");		
			result.setData(data);
		}else {
			result = new ServiceResult(HttpConstants.RESUTL_FAIL, HttpConstants.CODE_EXCEPTION, true);
		}
		return result;
	}
	
	@RequestMapping(value = "/getAllMsgList", method = RequestMethod.GET)
	@ApiOperation(value = "获取所有消息队列",notes = "获取所有消息队列")
	@ApiImplicitParams({//请求参数描述
	})
	public ServiceResult getAllMsgList() {
		ServiceResult result = null;
		List<MsgListStatus> data = dataService.getAllMsgList();
		if(data != null) {
			result = new ServiceResult(HttpConstants.RESUTL_OK, "");		
			result.setData(data);
		}else {
			result = new ServiceResult(HttpConstants.RESUTL_FAIL, HttpConstants.CODE_EXCEPTION, true);
		}
		return result;
	}
	
	
	@RequestMapping(value = "/changeConsumerStatus/{topic}/{group}/{status}", method = RequestMethod.GET)
	@ApiOperation(value = "开启/关闭消费者",notes = "开启/关闭消费者")
	@ApiImplicitParams({//请求参数描述
		@ApiImplicitParam(name = "topic", value = "消息主题", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "group", value = "消费者分组", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "status", value = "0关闭消费,1开启消费", required = true, dataTypeClass = Integer.class),
	})
	public ServiceResult changeConsumerStatus(@PathVariable("topic") String topic, @PathVariable("group") String group, @PathVariable("status") int status) {
		ServiceResult serviceResult = null;
		int result = dataService.changeConsumerStatus(topic, group, status);
		if(result == 1) {
			serviceResult = new ServiceResult(HttpConstants.RESUTL_OK, "操作成功");		
		}else {
			serviceResult = new ServiceResult(HttpConstants.RESUTL_FAIL, HttpConstants.CODE_EXCEPTION, true);
		}
		return serviceResult;
	}
}
