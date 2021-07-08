package com.example.config;

import com.example.properties.RedisExpirsProperties;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * SpringBoot整合Redis存储对象数据是乱码数据解决方法
 * 配置类序列化对象。（如果没有存储的是乱码）
 * redisTemplate.opsForValue();//操作字符串
 * redisTemplate.opsForHash();//操作hash
 * redisTemplate.opsForList();//操作list
 * redisTemplate.opsForSet();//操作set
 * redisTemplate.opsForZSet();//操作有序set
 */
@Configuration
@EnableCaching
@Slf4j
public class RedisConfig extends CachingConfigurerSupport {

	@Autowired
	RedisExpirsProperties redisExpirsProperties;

	@Bean("commonKey")
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			public Object generate(Object target, Method method, Object... params) {
				// 间隔符
				char sp = ':';
				StringBuilder sb = new StringBuilder();
				// 项目名
				sb.append("mybatisplus");
				sb.append(sp);
				// 类名
				sb.append(target.getClass().getSimpleName());
				sb.append(sp);
				// 方法名
				sb.append(method.getName());
				sb.append(sp);
				for (Object obj : params) {
					sb.append("{");
					sb.append(String.valueOf(obj));
					sb.append("}");
				}
//                log.info("Redis Key[{}]", sb);
				return sb.toString();
			}
		};

	}

	/**
	 * 序列化配置
	 * @param redisConnectionFactory
	 * @return
	 * @throws UnknownHostException
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
		// 我们为了自己开发方便，一般直接使用 <String, Object>
		// 两个泛型都是 Object, Object 的类型，我们后使用需要强制转换 <String, Object>
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		setRedisTemplate(template);
		template.afterPropertiesSet();
		return template;
	}

	private void setRedisTemplate(RedisTemplate<String, Object> template) {
		// String 的序列化
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		// key采用String的序列化方式
		template.setKeySerializer(stringRedisSerializer);
		// hash的key也采用String的序列化方式
		template.setHashKeySerializer(stringRedisSerializer);
		// value序列化方式采用jackson
		template.setValueSerializer(valueSerializer());
		// hash的value序列化方式采用jackson
		template.setHashValueSerializer(valueSerializer());
		// 设置值（value）的序列化采用FastJsonRedisSerializer。
		// 设置键（key）的序列化采用StringRedisSerializer。
		template.afterPropertiesSet();
	}


	@Bean
	RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

		// Redis序列化上下文。序列化键值对
		RedisSerializationContext.SerializationPair<String> key = RedisSerializationContext.SerializationPair.fromSerializer(keySerializer());
		RedisSerializationContext.SerializationPair<Object> value = RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer());

		//配置序列化(解决乱码的问题)(默认缓存块)
		RedisCacheConfiguration config = getRedisCacheConfiguration(Duration.ZERO, key, value);

		// 生成自定义缓存块,redis配置(这个取决于项目中是否有需要在使用@Cacheable缓存数据时,希望有不同的过期时间再决定是否使用)
		Map<String, RedisCacheConfiguration> customCacheBlockConfig = new LinkedHashMap<>();
		customCacheBlockConfig.put("expiretimeDefault", getRedisCacheConfiguration(Duration.ofSeconds(redisExpirsProperties.getExpiretimeDefault()), key, value));
		customCacheBlockConfig.put("expiretimeCommon", getRedisCacheConfiguration(Duration.ofSeconds(redisExpirsProperties.getExpiretimeCommon()), key, value));
		customCacheBlockConfig.put("expiretimeToken", getRedisCacheConfiguration(Duration.ofSeconds(redisExpirsProperties.getExpiretimeToken()), key, value));

		RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
				.cacheDefaults(config)
				.withInitialCacheConfigurations(customCacheBlockConfig) // 自定义缓存默认值配置
				.build();
		return cacheManager;

	}

	/**
	 * 序列化键
	 * @return
	 */
	private RedisSerializer<String> keySerializer() {
		return new StringRedisSerializer();
	}

	/**
	 * 序列化值
	 * @return
	 */
	private RedisSerializer<Object> valueSerializer(){
		// Json序列化配置
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		// 解决jackson2无法反序列化LocalDateTime的问题
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		objectMapper.registerModule(new JavaTimeModule());

		// 该方法过时
		// om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		// 上面 enableDefaultTyping 方法过时，使用 activateDefaultTyping
		objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		return jackson2JsonRedisSerializer;
	}

	/**
	 //	 * 生成Redis缓存配置
	 //	 * */
	private RedisCacheConfiguration getRedisCacheConfiguration(
			Duration expirationTime,
			RedisSerializationContext.SerializationPair<String> key,
			RedisSerializationContext.SerializationPair<?> value) {
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(expirationTime) // 默认缓存时间永久
				.serializeKeysWith(key) // 默认序列化键方式
				.serializeValuesWith(value) // 默认序列化值方式
				.computePrefixWith(name -> name + ":") //变双冒号为单冒号
				.disableCachingNullValues(); // 禁用缓存空值
		return redisCacheConfiguration;
	}

	/**
	 * redis数据操作异常处理 这里的处理：在日志中打印出错误信息，但是放行
	 * 保证redis服务器出现连接等问题的时候不影响程序的正常运行，使得能够出问题时不用缓存
	 *
	 * @return
	 */
	@Bean
	public CacheErrorHandler errorHandler() {
		// 异常处理，当Redis发生异常时，打印日志，但是程序正常走
		log.info("初始化 -> [{}]", "Redis CacheErrorHandler");
		CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
			@Override
			public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
				log.error("Redis occur handleCacheGetError：key -> [{}]", key, e);
			}
			@Override
			public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
				log.error("Redis occur handleCachePutError：key -> [{}]；value -> [{}]", key, value, e);
			}
			@Override
			public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
				log.error("Redis occur handleCacheEvictError：key -> [{}]", key, e);
			}
			@Override
			public void handleCacheClearError(RuntimeException e, Cache cache) {
				log.error("Redis occur handleCacheClearError：", e);
			}
		};
		return cacheErrorHandler;
	}


	protected void RedisErrorException(Exception exception, Object key) {
        log.error("redis异常：key=[{}]", key, exception);
	}

}