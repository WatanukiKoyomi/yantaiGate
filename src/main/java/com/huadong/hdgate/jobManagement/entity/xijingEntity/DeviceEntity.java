package com.huadong.hdgate.jobManagement.entity.xijingEntity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * TODO
 *
 * @author chuyz
 * @date 2020/2/25 13:34
 */
@Data
public class DeviceEntity {
    @JSONField(name="uuid")
    private String uuid;
    @JSONField(name="lanecode")
    private String lanecode;
    @JSONField(name="time")
    private String time;
    @JSONField(name="devno")
    private String devno;
    @JSONField(name="devname")
    private String devname;
    @JSONField(name="detail")
    private String detail;
    @JSONField(name="ip")
    private String ip;

    public String checkDetail(){
        if(detail.equals("正常")){
            return "1";
        }else{
            return "0";
        }
    }
}
