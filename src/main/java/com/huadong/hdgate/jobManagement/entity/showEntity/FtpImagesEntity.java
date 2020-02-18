package com.huadong.hdgate.jobManagement.entity.showEntity;

import lombok.Data;

/**
 * 图片相关信息
 */
@Data
public class FtpImagesEntity {
	/**
	 * 文件夹名称
	 */
	private String folder;
	/**
	 * 图片名称
	 */
	private String imageName;
	/**
	 * ftp上图片存放路径
	 */
	private String imagePath;

	public FtpImagesEntity() {
	}

	public FtpImagesEntity(String folder, String imageName, String imagePath) {
		this.folder = folder;
		this.imageName = imageName;
		this.imagePath = imagePath;
	}
}
