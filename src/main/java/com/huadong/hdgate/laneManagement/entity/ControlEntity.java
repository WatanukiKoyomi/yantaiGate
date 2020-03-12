package com.huadong.hdgate.laneManagement.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * TODO
 *
 * @author chuyz
 * @date 2020/3/4 9:18
 */
@Data
@EqualsAndHashCode
@ToString
public class ControlEntity {

    @JSONField(name="lanecode")
    private String lanecode;

    @JSONField(name="station")
    private String station;

    @JSONField(name="detail")
    private String detail;

    public ControlEntity(){
        this.detail = "";
        this.lanecode = "";
        this.station = "";
    }
}
