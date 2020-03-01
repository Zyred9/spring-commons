package com.example.vip.aop.common.exception;

/**
 * 无权限访问异常
 *
 * @Author: Zyred
 * @Date: 2020/2/27 22:05
 */
public class WithoutPermissionException extends RuntimeException{

    public WithoutPermissionException(){
        super();
    }

    public WithoutPermissionException(String msg){
        super(msg);
    }
}
