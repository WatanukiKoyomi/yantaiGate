package com.huadong.hdgate.systemManagement.controller;

import com.huadong.hdgate.systemManagement.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(value = "/test")
public class LoginApiTestController {

	@RequestMapping(value = "/loginApiTest",method = RequestMethod.POST)
	public String loginApiTest(@RequestBody SysUserEntity user) {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"account\":\"").append(user.getAccount()).append("\",").
				append("\"userName\":\"").append("我叫某某某").append("\",").
				append("\"userPwd\":\"").append(user.getUserPwd()).append("\"}");
		return sb.toString();
	}

	@RequestMapping(value = "/loginApiTestGet",method = RequestMethod.GET)
	public String loginApiTestGet(@RequestBody SysUserEntity user) {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"account\":\"").append(user.getAccount()).append("\",").
				append("\"userName\":\"").append("我叫某某某").append("\",").
				append("\"userPwd\":\"").append(user.getUserPwd()).append("\"}");
		return sb.toString();
	}

	@Autowired
	RedisTemplate redisTemplate;

	@RequestMapping(value = "/redisTest",method = RequestMethod.GET)
	public void test(){
		redisTemplate.opsForValue().set("test", "aaaaa");
		String str = (String)redisTemplate.opsForValue().get("test");
		System.out.println(str);
		redisTemplate.delete("test");
		str = (String)redisTemplate.opsForValue().get("test");
		System.out.println(str);
	}
}
