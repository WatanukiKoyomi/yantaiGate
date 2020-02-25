package com.huadong.hdgate.common.utils;

import com.huadong.hdgate.jobManagement.entity.cdiEntity.BusinessEntity;
import com.huadong.hdgate.jobManagement.service.BusinessService;
import com.huadong.hdgate.laneManagement.entity.GateLane;
import com.huadong.hdgate.laneManagement.service.GateLaneService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author chuyz
 * @date 2020/2/24 16:19
 */
@Component
public class LaneDBUtils {

    @Resource
    private GateLaneService gateLaneService;

    public Integer getLaneDB(String laneCode){
        for(GateLane g:gateLaneService.queryAllGateLanes()){
            if(g.getLaneCode().equals(laneCode)){
                return g.getLaneDB();
            }
        }
        return null;
    }

    public List<Integer> getAllLaneDB(){
        List<Integer> list = new ArrayList<>();
        for(GateLane g:gateLaneService.queryAllGateLanes()){
            list.add(g.getLaneDB());
        }
        return list;
    }

    public String getLaneCode(int laneDB){
        for(GateLane g:gateLaneService.queryAllGateLanes()){
            if(g.getLaneDB() == laneDB){
                return g.getLaneCode();
            }
        }
        return null;
    }

}
