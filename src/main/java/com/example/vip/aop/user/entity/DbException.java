package com.example.vip.aop.user.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 数据库中异常存储
 * @Author: Zyred
 * @Date: 2020/2/27 22:52
 */
@Data
@Table(name = "exception")
public class DbException {
    /**主键 **/
    @Id
    @Transient
    private Integer id;
    /** 异常发生时间 **/
    @Transient
    private String time;
    /** 类名 **/
    @Transient
    private String className;
    /**方法名称 **/
    @Transient
    private String methodName;
    /**请求参数 **/
    @Transient
    private String parameters;
    /**异常信息 **/
    @Transient
    private String exceptionMsg;
    /** 参数类型 **/
    @Transient
    private String parameterType;
    /** 请求路径 **/
    @Transient
    private String requestPath;
    /** 错误所在文件 **/
    @Transient
    private String exceptionFile;
    /** 错误行 **/
    @Transient
    private String exceptionLine;

}
