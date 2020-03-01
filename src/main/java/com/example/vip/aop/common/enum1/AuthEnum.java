package com.example.vip.aop.common.enum1;

import java.io.Serializable;

/**
 * 权限类型
 * @author Zyred
 */
public enum AuthEnum implements Serializable {
    /**
     * 可以匿名访问
     */
    NON(null),
    /**
     * 登录权限
     */
    LOGIN("login"),
    /**
     * 创建权限
     */
    CREATE("create"),
    /**
     * 更新权限
     */
    UPDATE("update"),
    /**
     * 删除权限
     */
    DELETE("delete"),
    /**
     * 查询权限
     */
    SELECT("select");

    AuthEnum(String authType) {
        this.authType = authType;
    }

    private String authType;

    public String getCode() {
        return authType;
    }
}
