package com.huadong.hdgate.jobManagement.entity.cdiEntity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 图片相关信息
 */
@Data
public class FtpImagesEntity {
	/**
	 * 文件夹名称
	 */
	@JSONField(name="Folder")
	private String folder;
	/**
	 * 图片名称
	 */
	@JSONField(name="ImageName")
	private String imageName;
	/**
	 * ftp上图片存放路径
	 */
	@JSONField(name="ImagePath")
	private String imagePath;

}
