package com.huadong.hdgate.common.utils;

import com.huadong.hdgate.laneManagement.entity.GateLane;
import com.huadong.hdgate.laneManagement.service.GateLaneService;

import javax.annotation.Resource;
import java.util.Map;

/**
 *
 * @author chuyz
 * @date 2020/2/24 16:19
 */
public class LaneDBUtils {

    private Map<String, Integer> map;

    @Resource
    private GateLaneService gateLaneService;

    {
        for(GateLane g: gateLaneService.queryAllGateLanes()){
            map.put(g.getLaneCode(),g.getLaneDB());
        }
    }

    public Map<String, Integer> getMap(){
        return map;
    }
}
