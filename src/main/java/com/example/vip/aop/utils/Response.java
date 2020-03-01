package com.example.vip.aop.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口返回统一实体
 * @author Administrator
 */
@Data
public class Response<T> implements Serializable {
    private String code;
    private String msg;
    private Long count;
    private T data;

    /**
     * 成功返回(无数据) 返回码200
     *
     * @param msg
     * @return
     */
    public static Response ok(String msg) {
        return new Response("200", msg, null);
    }

    /**
     * 成功返回(有数据) 返回码200
     *
     * @param msg
     * @return
     */
    public static Response ok(String msg, Object data) {
        return new Response("200", msg, data);
    }


    /****
     * 自定义返回码
     * @param code
     * @param msg
     * @return
     */
    public static Response ok(String code, String msg) {
        return new Response(code, msg, null);
    }

    /**
     * 直接返回数据(当data为String类型时,与ok(String msg)方法一致
     *
     * @param data 数据
     * @return 结果
     */
    public static Response ok(Object data) {
        return new Response("200", data);
    }

    /**
     * 失败返回 返回码500
     *
     * @param msg
     * @return
     */
    public static Response error(String msg) {
        return new Response("500", msg, null);
    }

    /**
     * 失败返回 自定义返回码
     *
     * @param msg
     * @return
     */
    public static Response error(String code, String msg) {
        return new Response(code, msg, null);
    }

    public Response(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Response(String code, T data) {
        this.code = code;
        this.data = data;
    }
}
