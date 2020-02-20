package com.huadong.hdgate.jobManagement.entity.xijingEntity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * TODO
 *
 * @author chuyz
 * @date 2020/2/20 10:12
 */
@Data
public class ContainerEntity {
    @JSONField(name="number")
    private String number;
    @JSONField(name="iso")
    private String iso;
    @JSONField(name="direction")
    private String direction;
    @JSONField(name="leadsealstate")
    private String leadsealstate;
    @JSONField(name="leadsealno")
    private String leadsealno;
    @JSONField(name="property")
    private String property;
}
