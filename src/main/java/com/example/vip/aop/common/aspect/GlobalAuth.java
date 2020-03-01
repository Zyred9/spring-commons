package com.example.vip.aop.common.aspect;


import com.alibaba.fastjson.JSON;
import com.example.vip.aop.common.annotation.Auth;
import com.example.vip.aop.common.enum1.AuthEnum;
import com.example.vip.aop.common.exception.InsufficientPermissionsException;
import com.example.vip.aop.common.exception.WithoutPermissionException;
import com.example.vip.aop.utils.Constant;
import com.example.vip.aop.utils.CookieUtils;
import com.example.vip.aop.utils.Jwt;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 全局权限控制，主要解析 @Auth注解是否存在
 * NON：那么该方法属于匿名访问
 * LOGIN：登录操作
 * SELECT：查询操作
 * UPDATE：更新操作
 * DELETE：删除操作
 *
 * 如果没有该注解表示该方法不提供外界访问
 * @author Zyred
 */
@Slf4j
@Aspect
@Component
public class GlobalAuth {

    /** 请求切入点，拦截所有 controller 所有方法 **/
    @Pointcut("execution(* com.example.vip.aop.*.*.*Controller.*(..))")
    public void authPermission() {
    }

    /**
     * 请求方法之前执行拦截判断
     * @param jp
     * @throws Throwable
     */
    @Before("authPermission()")
    public void doAuthHandler(JoinPoint jp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //得到当前执行的方法
        ProceedingJoinPoint joinPoint = (ProceedingJoinPoint) jp;
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //是否又@Auth注解
        if (method.isAnnotationPresent(Auth.class)) {
            String code = method.getAnnotation(Auth.class).value().getCode();
            // @Auth注解中参数的校验
            if (AuthEnum.LOGIN.getCode().equals(code)) {
                joinPoint.proceed();
            } else if (AuthEnum.SELECT.getCode().equals(code)
                    || AuthEnum.UPDATE.getCode().equals(code)
                    || AuthEnum.DELETE.getCode().equals(code)) {
                // 从cookie中获取token
                String token = CookieUtils.getCookieValue(request, Constant.ACCESS_TOKEN, true);
                if (StringUtils.isEmpty(token)) {
                    throw new InsufficientPermissionsException("Please log in ！！");
                    // 否则验证token有效性
                } else {
                    Claims claims = Jwt.phaseToken(token);
                    log.info(JSON.toJSONString(claims));
                    joinPoint.proceed();
                }
            }
        } else {
            throw new WithoutPermissionException("Without permission !!");
        }
    }
}
