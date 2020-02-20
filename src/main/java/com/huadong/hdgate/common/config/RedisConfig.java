package com.huadong.hdgate.common.config;

import com.huadong.hdgate.common.entity.MessageReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

	@Value("${spring.redis.host}")
	private String host;
	@Value("${spring.redis.port}")
	private int port;
	@Value("${spring.redis.password}")
	private String password;
	/*@Value("${spring.redis.timeout}")
	private int timeout;
	*/
	private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

	/**
	 * redis消息监听器容器
	 * 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器
	 * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
	 * @param connectionFactory
	 * @param listenerAdapter
	 * @return
	 */
	@Bean
	//相当于xml中的bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
											MessageListenerAdapter listenerAdapter) {

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		//订阅了一个叫crane 的通道
		container.addMessageListener(listenerAdapter, new PatternTopic("hd_gate_business_data_db")); // hd_gate_business_data_db 频道用来业务数据的数据库操作，避免死锁
		//这个container 可以添加多个 messageListener
		return container;
	}

	/**
	 * 消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法
	 * @param receiver
	 * @return
	 */
	@Bean
	MessageListenerAdapter listenerAdapter(MessageReceiver receiver) {
		//这个地方 是给messageListenerAdapter 传入一个消息接受的处理器，利用反射的方法调用“receiveMessage”
		//也有好几个重载方法，这边默认调用处理器的方法 叫handleMessage 可以自己到源码里面看
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	/**redis 读取内容的template */
	@Bean(name="template")
	StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);
	}

	@Bean(name="redisPoolFactory")
	public JedisPool redisPoolFactory()  throws Exception{
		logger.info("JedisPool注入成功！！");
		logger.info("redis地址：" + host + ":" + port);
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		//jedisPoolConfig.setMaxIdle(maxIdle);
		//jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		// 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
		//jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
		// 是否启用pool的jmx管理功能, 默认true
		jedisPoolConfig.setJmxEnabled(true);
		JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, 10000, password);
		return jedisPool;
	}

}
