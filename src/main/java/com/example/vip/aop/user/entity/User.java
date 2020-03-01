package com.example.vip.aop.user.entity;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Constraint;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户信息类
 * @author Zyred
 */
@Data
@Table(name = "user")
public class User{

    @Id
    private Integer id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String qqEmail;

    /**
     * 注册时候发送邮件的验证码
     */
    @Transient
    private String registryCode;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
