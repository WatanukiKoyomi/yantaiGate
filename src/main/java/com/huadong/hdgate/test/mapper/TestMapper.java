package com.huadong.hdgate.test.mapper;

import org.apache.ibatis.annotations.Param;

public interface TestMapper {
	public void addTest(@Param("msg") String msg);
}
