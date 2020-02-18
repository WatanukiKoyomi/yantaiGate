package com.huadong.hdgate.jobManagement.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 西井相关配置文件配置
 */
@Data
@Component
@ConfigurationProperties(prefix="xijing")
public class XijingParamsEntity {

	private String ip;

	private int port;
}
