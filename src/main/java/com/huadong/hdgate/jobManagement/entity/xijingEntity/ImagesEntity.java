package com.huadong.hdgate.jobManagement.entity.xijingEntity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TODO
 *
 * @author chuyz
 * @date 2020/2/20 10:12
 */
public class ImagesEntity {
    @JSONField(name="lorry")
    private String lorry;
    @JSONField(name="plate")
    private String plate;
    @JSONField(name="ahead")
    private String ahead;
    @JSONField(name="behind")
    private String behind;
    @JSONField(name="left1")
    private String left1;
    @JSONField(name="left2")
    private String left2;
    @JSONField(name="right1")
    private String right1;
    @JSONField(name="right2")
    private String right2;
    @JSONField(name="top")
    private String top;
    @JSONField(name="left")
    private String left;
    @JSONField(name="right")
    private String right;

}
