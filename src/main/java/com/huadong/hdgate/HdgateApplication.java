package com.huadong.hdgate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) // 禁止加载自身对DataSource的扫描
//@MapperScan("com.huadong.hdgate.*.mapper")
public class HdgateApplication {

	public static void main(String[] args) {
		SpringApplication.run(HdgateApplication.class, args);
	}
}
