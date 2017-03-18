package com.zml.common.constant;

/**
 * 缓存常量,定义缓存KEY的值 
 * @author zhaomingliang
 * @date 2017年2月10日
 */
public class CacheConstant {

	/**
	 * 用户列表
	 */
	public static final String ALL_USER_LIST = "ALL_USER_LIST";
	
	/**
	 * 当前用户(id为key)
	 */
	public static final String CURRENT_USER_ID = "CURRENT_USER_ID_";
	
	/**
	 * The Redis key prefix for the sessions 
	 */
	public static final String SHIRO_REDIS_SESSION = "SHIRO_REDIS_SESSION_";
	
	/**
	 * The Redis key prefix for the caches  
	 */
	public static final String SHIRO_REDIS_CACHE = "SHIRO_REDIS_CACHE_";
	
	public static final String PWD_RETRY_COUNT =  "PWD_RETRY_COUNT_";
	
	/**
	 * 是否显示验证码的缓存key
	 */
	public static final String JCAPTCHA_ENABLED =  "JCAPTCHA_ENABLED_";
}
