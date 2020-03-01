package com.example.vip.aop.common.enum1;

import java.io.Serializable;

/**
 * Http接口响应代码
 * @Author: Zyred
 * @Date: 2020/2/27 11:37
 */
public enum  ResponseEnum implements Serializable {
    /** 账号或密码错误 **/
    INFO_ERROR("00100", "账号或密码错误"),
    /** 查无此人 **/
    EMPTY_USER("00101", "查无此人"),
    /** 账号密码不能为空 **/
    EMPTY_CONTENT("00000", "账号密码不能为空"),
    /** 用户已经存在 **/
    THE_USER_ALREADY_EXISTS("00002", "用户已经存在"),
    /** 尚未登录，请前往登录 **/
    NOT_LOGIN("00003", "尚未登录，请前往登录"),
    /** 您无权限访问该资源 **/
    WITHOUT_PERMISSION("00005", "您无权限访问该资源"),
    /** 操作成功 **/
    OK_SUCCESS("20000", "操作成功"),
    /** 验证码错误 **/
    CODE_ERROR("00006", "验证码错误")
    ;

    ResponseEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
