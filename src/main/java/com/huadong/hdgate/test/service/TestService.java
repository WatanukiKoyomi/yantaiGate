package com.huadong.hdgate.test.service;

import com.huadong.hdgate.test.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("testService")
public class TestService {
	@Resource
	private TestMapper testMapper;

	public void addTest(String msg){
		testMapper.addTest(msg);
	}
}
