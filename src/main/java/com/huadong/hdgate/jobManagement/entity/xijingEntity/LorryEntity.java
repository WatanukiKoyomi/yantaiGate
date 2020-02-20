package com.huadong.hdgate.jobManagement.entity.xijingEntity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * TODO
 *
 * @author chuyz
 * @date 2020/2/20 10:11
 */
@Data
public class LorryEntity {
    @JSONField(name="plate")
    private String plate;
    @JSONField(name="color")
    private String color;
}
