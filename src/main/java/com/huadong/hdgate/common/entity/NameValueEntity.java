package com.huadong.hdgate.common.entity;

/**
 * 存放name和value的实体类(图形数据)
 */
public class NameValueEntity {
	private String name;
	private int value;
	/**
	 * 识别率，在主页识别率中用
	 */
	private double recognitionRation;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public double getRecognitionRation() {
		return recognitionRation;
	}

	public void setRecognitionRation(double recognitionRation) {
		this.recognitionRation = recognitionRation;
	}

	@Override
	public String toString() {
		return "NameValueEntity{" +
				"name='" + name + '\'' +
				", value=" + value +
				", recognitionRation=" + recognitionRation +
				'}';
	}
}
