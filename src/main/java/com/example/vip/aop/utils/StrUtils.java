package com.example.vip.aop.utils;

import com.alibaba.fastjson.JSON;
import com.example.vip.aop.user.entity.User;

/**
 * @Author: Zyred
 * @Date: 2020/2/26 22:33
 */
public class StrUtils {

    /**
     * 讲Object转换为执行对象
     * @param src 源字符串
     * @param clazz 转成指定对象
     * @return
     */
    public static Object parseEntity(Object src, Class<?> clazz){
        return JSON.parseObject(src.toString(), clazz);
    }

}
