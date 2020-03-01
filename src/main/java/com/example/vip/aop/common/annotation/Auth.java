package com.example.vip.aop.common.annotation;

import com.example.vip.aop.common.enum1.AuthEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限解析
 * @author Zyred
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Auth {
    /** 请求参数类型 **/
    AuthEnum value();

}
