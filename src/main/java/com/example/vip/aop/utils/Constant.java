package com.example.vip.aop.utils;

/**
 * 登录工具类
 * @author Zyred
 */
public class Constant{

    public static ThreadLocal<String> threadLocal = new ThreadLocal();

    /** http header中token 的key**/
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    /** 生成TOKEN，在MAP中的key **/
    public static final String TOKEN_KEY = "token";

    /**超时时间**/
    public static final String EXPIRE_TIME = "expireTime";

    /**Redis 存储用户信息的前缀 后缀为ID**/
    public static final String USER_INFO_REDIS_PREFIX = "USER_INFO_PREFIX:";

    /** 登录作用，用于存储token **/
    public static final String REDIS_SESSION_PREFIX = "REDIS_SESSION:";

    /** 空 **/
    public static final Object EMPTY = null;

    /** Redis中存储发送验证码的KEY **/
    public static final String REGISTRY_EMAIL_CODE_REDIS_KEY = "REGISTRY_EMAIL_CODE:";

    /** Redis中存储发送验证码的KEY的失效时间 5分钟 **/
    public static final Long REGISTRY_EMAIL_CODE_FAILURE_TIME  = 5 * 60L;
}
