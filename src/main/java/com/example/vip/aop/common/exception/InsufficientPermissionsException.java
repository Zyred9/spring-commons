package com.example.vip.aop.common.exception;

/**
 * 权限不足异常
 * @author Zyred
 */
public class InsufficientPermissionsException extends RuntimeException{

    public InsufficientPermissionsException(){
        super();
    }

    public InsufficientPermissionsException(String str){
        super(str);
    }

}
