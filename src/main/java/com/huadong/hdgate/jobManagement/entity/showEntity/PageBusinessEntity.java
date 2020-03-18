package com.huadong.hdgate.jobManagement.entity.showEntity;

import com.alibaba.fastjson.annotation.JSONField;
import com.huadong.hdgate.jobManagement.entity.cdiEntity.FtpImagesEntity;
import lombok.Data;

/**
 * 展示页实体
 * @author chuyz
 * @date 2020/3/18 9:22
 */
@Data
public class PageBusinessEntity {
    @JSONField(name="VisitGuid")
    private String visitGuid;
    @JSONField(name="plate")
    private String plate;
    @JSONField(name="weight")
    private String weight;
    @JSONField(name="fNo")
    private String fNo;
    @JSONField(name="fISO")
    private String fISO;
    @JSONField(name="fEfid")
    private String fEfid;
    @JSONField(name="fLeadSealState")
    private String fLeadSealState;
    @JSONField(name="fleadSealNo")
    private String fleadSealNo;
    @JSONField(name="fProperty")
    private String fProperty;
    @JSONField(name="bNo")
    private String bNo;
    @JSONField(name="bISO")
    private String bISO;
    @JSONField(name="bEfid")
    private String bEfid;
    @JSONField(name="bLeadSealState")
    private String bLeadSealState;
    @JSONField(name="bleadSealNo")
    private String bleadSealNo;
    @JSONField(name="bProperty")
    private String bProperty;
    @JSONField(name="FtpImages")
    private FtpImagesEntity ftpImages;

}
