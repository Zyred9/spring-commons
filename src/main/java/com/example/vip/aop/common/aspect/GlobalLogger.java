package com.example.vip.aop.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 全局日志打印
 *
 * @Author: Zyred
 * @Date: 2020/2/28 11:02
 */
@Slf4j
@Aspect
@Component
public class GlobalLogger {

    @Pointcut("execution(* com.example.vip.aop.*.*.*.*Impl.*(..))")
    public void loggerPoint(){}



    @Before("loggerPoint()")
    public void doLogger(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        log.info("请求Mapping   <=======>" + signature.getName());
        log.info("执行的Service <=======>" + signature.getDeclaringTypeName());
        log.info(signature.getDeclaringType().toGenericString());

        /*JSON.toJSONString(joinPoint) 转换会报异常
        java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
        log.info(JSON.toJSONString(joinPoint));*/
    }

}
