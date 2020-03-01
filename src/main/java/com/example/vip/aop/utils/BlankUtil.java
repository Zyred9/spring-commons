package com.example.vip.aop.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 提供对JDK常用类的简单操作工具
 * @author geYang
 * */
public class BlankUtil {
	
	/**
	 * Map类型去空键空值操作, 主要作用于页面传递的Map参数,防止引起数据库数据类型或空指针报错
	 * */
	public static Map<String, Object> mapGetRidOfNull(Map<String,Object> map) {
	    Set<String> keySet = map.keySet();
		keySet.removeIf(key -> isBlank(key) || isBlank(map.get(key)));
		return map;
	}
	
	/** 对象toString后Null判断  */
	public static boolean isBlank(Object str) {
		return str == null || str.toString().trim().isEmpty();
	}
	
	/** 字符串 Null判断  */
	public static boolean isBlank(String str) {
		return str == null || str.trim().isEmpty();
	}
	
	/** 判断List是否为NULL */
	public static boolean isBlank(List<?> list) {
	    return list == null || list.isEmpty();
	}
	
	/** 判断Map是否为NULL */
	public static boolean isBlank(Map<?, ?> map) {
	    return map == null || map.isEmpty();
	}
	
	/** 判断Set是否为NULL */
	public static boolean isBlank(Set<?> set) {
	    return set == null || set.isEmpty();
	}
	
	/** 判断Integer是否为NULL */
	public static boolean isBlank(Integer i) {
	    return i == null || i < 1;
	}
	
	/** 判断Long是否为NULL */
	public static boolean isBlank(Long l) {
	    return l == null || l < 1;
	}
	
	/** 判断Object[]是否为NULL */
	public static boolean isBlank(Object[] arr) {
	    return arr == null || arr.length == 0;
	}

}