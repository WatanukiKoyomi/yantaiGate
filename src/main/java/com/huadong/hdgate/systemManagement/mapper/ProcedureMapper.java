package com.huadong.hdgate.systemManagement.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.huadong.hdgate.systemManagement.entity.SysDeptEntity;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

public interface ProcedureMapper extends BaseMapper<SysDeptEntity>{

	@Select("testProcedure")
	public void testProcedure(Map<String, Object> paramMap);
}
