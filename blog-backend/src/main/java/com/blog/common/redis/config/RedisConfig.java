package com.blog.common.redis.config;

import cn.hutool.core.util.ObjectUtil;
import com.blog.common.redis.handler.KeyPrefixHandler;
import com.blog.common.redis.property.RedissonProperty;
import com.blog.common.util.JacksonUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.CompositeCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zouzhangpeng
 * @desc redis配置类
 */
@RequiredArgsConstructor
@Slf4j
@Configuration
public class RedisConfig {

	private final RedissonProperty redissonProperty;

	@Bean
	public RedissonAutoConfigurationCustomizer redissonCustomizer() {
		return config -> {
			ObjectMapper om = JacksonUtil.OBJECT_MAPPER.copy();
			om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
			// 指定序列化输入的类型，类必须是非final修饰的。序列化时将对象全类名一起保存下来
			om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
			TypedJsonJacksonCodec jsonCodec = new TypedJsonJacksonCodec(Object.class, om);
			// 组合序列化 key 使用 String 内容使用通用 json 格式
			CompositeCodec codec = new CompositeCodec(StringCodec.INSTANCE, jsonCodec, jsonCodec);
			config.setThreads(redissonProperty.getThreads())
				.setNettyThreads(redissonProperty.getNettyThreads())
				// 缓存 Lua 脚本 减少网络传输(redisson 大部分的功能都是基于 Lua 脚本实现)
				.setUseScriptCache(true)
				.setCodec(codec);
			RedissonProperty.SingleServerConfig singleServerConfig = redissonProperty.getSingleServerConfig();
			if (ObjectUtil.isNotNull(singleServerConfig)) {
				// 使用单机模式
				config.useSingleServer()
					// 设置redis key前缀
					.setNameMapper(new KeyPrefixHandler(redissonProperty.getKeyPrefix()))
					.setTimeout(singleServerConfig.getTimeout())
					.setClientName(singleServerConfig.getClientName())
					.setIdleConnectionTimeout(singleServerConfig.getIdleConnectionTimeout())
					.setSubscriptionConnectionPoolSize(singleServerConfig.getSubscriptionConnectionPoolSize())
					.setConnectionMinimumIdleSize(singleServerConfig.getConnectionMinimumIdleSize())
					.setConnectionPoolSize(singleServerConfig.getConnectionPoolSize());
			}
			// 集群配置方式 参考下方注释
			RedissonProperty.ClusterServersConfig clusterServersConfig = redissonProperty.getClusterServersConfig();
			if (ObjectUtil.isNotNull(clusterServersConfig)) {
				config.useClusterServers()
					// 设置redis key前缀
					.setNameMapper(new KeyPrefixHandler(redissonProperty.getKeyPrefix()))
					.setTimeout(clusterServersConfig.getTimeout())
					.setClientName(clusterServersConfig.getClientName())
					.setIdleConnectionTimeout(clusterServersConfig.getIdleConnectionTimeout())
					.setSubscriptionConnectionPoolSize(clusterServersConfig.getSubscriptionConnectionPoolSize())
					.setMasterConnectionMinimumIdleSize(clusterServersConfig.getMasterConnectionMinimumIdleSize())
					.setMasterConnectionPoolSize(clusterServersConfig.getMasterConnectionPoolSize())
					.setSlaveConnectionMinimumIdleSize(clusterServersConfig.getSlaveConnectionMinimumIdleSize())
					.setSlaveConnectionPoolSize(clusterServersConfig.getSlaveConnectionPoolSize())
					.setReadMode(clusterServersConfig.getReadMode())
					.setSubscriptionMode(clusterServersConfig.getSubscriptionMode());
			}
			log.info("redis信息初始化完成");
		};
	}

}
