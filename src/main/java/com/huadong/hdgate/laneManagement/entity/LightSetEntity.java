package com.huadong.hdgate.laneManagement.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * TODO
 *
 * @author chuyz
 * @date 2020/3/12 15:25
 */
@Data
@EqualsAndHashCode
@ToString
public class LightSetEntity {
    @JSONField(name="lanecode")
    private String lanecode;

    @JSONField(name="station")
    private String station;

    @JSONField(name="detail")
    private String detail;

    @JSONField(name="time_on")
    private String time_on;

    @JSONField(name="time_off")
    private String time_off;
}
