package com.example.vip.aop.common.exception;

/**
 * TODO 自定义全局异常
 * @author geYang
 * @date 2018-05-11
 */
public class SysException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    private String msg;
    private int code = 500;
    
    public SysException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public SysException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }
    
    public SysException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
}