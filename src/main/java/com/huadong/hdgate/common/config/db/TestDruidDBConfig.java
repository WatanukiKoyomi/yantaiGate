package com.huadong.hdgate.common.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

//@Configuration
@MapperScan(basePackages = {"com.huadong.hdgate.test.mapper"},
		sqlSessionFactoryRef = "testSqlSessionFactory")
public class TestDruidDBConfig {

	@Value("${test.datasource.url}")
	private String url;

	@Value("${test.datasource.username}")
	private String user;

	@Value("${test.datasource.password}")
	private String password;

	@Value("${test.datasource.driverClassName}")
	private String driverClass;

	@Bean(name = "testDataSource")
	public DataSource masterDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		return dataSource;
	}
	@Bean(name = "testTransactionManager")
	public DataSourceTransactionManager masterTransactionManager() {
		return new DataSourceTransactionManager(masterDataSource());
	}

	@Bean(name = "testSqlSessionFactory")
	public SqlSessionFactory masterSqlSessionFactory(@Qualifier("testDataSource") DataSource masterDataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(masterDataSource);
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources("classpath:mapper/other/*.xml"));
		return sessionFactory.getObject();
	}
}
