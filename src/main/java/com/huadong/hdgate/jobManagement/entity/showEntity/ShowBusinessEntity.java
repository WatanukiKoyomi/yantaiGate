package com.huadong.hdgate.jobManagement.entity.showEntity;

import com.huadong.hdgate.common.entity.BaseEntityWithPage;
import lombok.Data;

/**
 * 业务数据相关信息（包含车道，集卡，集装箱，图片等信息）
 */
@Data
public class ShowBusinessEntity extends BaseEntityWithPage {
	/**
	 * 主键
	 */
	private String visitGuid;
	/**
	 * 增加标志确认是从集控发送的数据 web：从集控发送的数据
	 */
	private String station;
	/**
	 *  "Msg":"作业成功"
	 */
	private String msg;
	/**
	 *  "ArriveTime": "2018/12/04 23:27:39"
	 */
	private String arriveTime;
	/**
	 *  "EnterTime": "2018/12/05 00:00:39",
	 */
	private String enterTime;
	/**
	 * 基本信息
	 */
	private GeneralInfoEntity generalInfo;
	/**
	 * 识别的集卡信息
	 */
	private CarPlateEntity ocrCarPlate;
	/**
	 * 前集装箱信息
	 */
	private ContainerEntity ocrFrontContainer;
	/**
	 * 后集装箱信息
	 */
	private ContainerEntity ocrAfterContainer;

	/**
	 * 显示的集卡信息,前后集装箱信息
	 */
	private CarPlateEntity carPlate = new CarPlateEntity();
	private ContainerEntity frontContainer = new ContainerEntity();
	private ContainerEntity afterContainer = new ContainerEntity();
	/**
	 * 图片信息
	 */
	private FtpImagesEntity ftpImages = new FtpImagesEntity();

	/**
	 * 修改数据的用户名
	 */
	private String account;

	public ShowBusinessEntity(String visitGuid, String station, String msg, String arriveTime, String enterTime) {
		this.visitGuid = visitGuid;
		this.station = station;
		this.msg = msg;
		this.arriveTime = arriveTime;
		this.enterTime = enterTime;
	}

	public ShowBusinessEntity() {
	}
}
