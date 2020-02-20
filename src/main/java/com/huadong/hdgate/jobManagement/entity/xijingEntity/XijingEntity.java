package com.huadong.hdgate.jobManagement.entity.xijingEntity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * TODO
 * 西井数据格式
 * @author chuyz
 * @date 2020/2/20 9:45
 */
@Data
public class XijingEntity {
    @JSONField(name="uuid")
    private String uuid;
    @JSONField(name="station")
    private String station;
    @JSONField(name="weight")
    private String weight;
    @JSONField(name="starttime")
    private String starttime;
    @JSONField(name="endtime")
    private String endtime;
    @JSONField(name="containersize")
    private String containersize;
    @JSONField(name="lorry")
    private LorryEntity lorry;
    @JSONField(name="containerahead")
    private ContainerEntity containerahead;
    @JSONField(name="containerbehind")
    private ContainerEntity containerbehind;
    @JSONField(name="images")
    private ImagesEntity images;
}
