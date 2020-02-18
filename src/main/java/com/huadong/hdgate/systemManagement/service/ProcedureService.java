package com.huadong.hdgate.systemManagement.service;


import com.huadong.hdgate.systemManagement.mapper.ProcedureMapper;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

//@Service("procedureService")
public class ProcedureService {
	@Resource
	ProcedureMapper procedureMapper;

	public String testProcedure(String str,String result){
		Map<String, Object> map = new HashMap<>();
		map.put("userId",str);
		map.put("result",result);
		procedureMapper.testProcedure(map);
		System.out.println(map.toString()+"----------------");
		return map.get("result").toString();
	}
}
