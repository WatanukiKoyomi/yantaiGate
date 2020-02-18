package com.huadong.hdgate.common.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 本身集控数据库访问配置
 */
@Configuration
@MapperScan(basePackages = {"com.huadong.hdgate.*.mapper"},
		sqlSessionFactoryRef = "mainSqlSessionFactory")
public class MainDruidDBConfig {

	@Value("${main.datasource.type}")
	private String type;

	@Value("${main.datasource.url}")
	private String url;

	@Value("${main.datasource.username}")
	private String user;

	@Value("${main.datasource.password}")
	private String password;

	@Value("${main.datasource.driverClassName}")
	private String driverClass;

	@Value("${main.datasource.initialSize}")
	private int initialSize;

	@Value("${main.datasource.minIdle}")
	private int minIdle;

	@Value("${main.datasource.maxActive}")
	private int maxActive;

	@Bean(name = "mainDataSource")
	@Primary
	public DataSource masterDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDbType(type);
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		return dataSource;
	}
	@Bean(name = "mainTransactionManager")
	@Primary
	public DataSourceTransactionManager masterTransactionManager() {
		return new DataSourceTransactionManager(masterDataSource());
	}

	@Bean(name = "mainSqlSessionFactory")
	@Primary
	public MybatisSqlSessionFactoryBean masterSqlSessionFactory(@Qualifier("mainDataSource") DataSource masterDataSource)
			throws Exception {
		final MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
		sessionFactory.setDataSource(masterDataSource);
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources("classpath:mapper/base/*.xml"));
		return sessionFactory;
	}

}
