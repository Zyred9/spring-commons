package com.example.vip.aop.common.config;

import com.example.vip.aop.common.exception.SysException;
import com.example.vip.aop.utils.BlankUtil;
import com.example.vip.aop.utils.Jwt;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT过滤器
 * @author Zyred
 */
//@Component
@Deprecated
public class JwtInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String servletPath = request.getServletPath();
        System.out.println("servletPath: " + servletPath);
        // 不需要验证,直接放行
        boolean isNotCheck = isNotCheck(servletPath);
        if (isNotCheck) {
            return true;
        }
        // 需要验证
        String token = getToken(request);
        if (BlankUtil.isBlank(token)) {
            throw new SysException("登录已过期,请重新登录", 401);
        }
        // 获取签名信息
        Claims claims = Jwt.getClaimByToken(token);
        System.out.println("TOKEN: " + claims);
        // 判断签名是否存在或过期
        boolean b = claims == null || claims.isEmpty() || Jwt.isTokenExpired(claims.getExpiration());
        if (b) {
            throw new SysException("登录已过期,请重新登录", 401);
        }
        // 将签名中获取的用户信息放入request中;
        request.setAttribute(Jwt.USER_KEY, claims.getSubject());
        return true;
    }

    /**
     * 获取请求Token
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(Jwt.JWT_KEY);
        if (BlankUtil.isBlank(token)) {
            token = request.getParameter(Jwt.JWT_KEY);
        }
        return token;
    }

    /**
     * 不用拦截的页面路径(也可存入数据库中), 不要以 / 结尾
     **/
    private static final String[] NOT_CHECK_URL = {"/test/**"};

    /**
     * 根据URL判断当前请求是否不需要校验, true:不需要校验
     **/
    public boolean isNotCheck(String servletPath) {
        // 若 请求接口 以 / 结尾, 则去掉 /
        servletPath = servletPath.endsWith("/")
                ? servletPath.substring(0, servletPath.lastIndexOf("/"))
                : servletPath;
        System.out.println("servletPath = " + servletPath);
        for (String path : NOT_CHECK_URL) {
            System.out.println("path = " + path);
            if (path.equals(servletPath)) {
                return true;
            }
            // path 以 /** 结尾, servletPath 以 path 前缀开头
            if (path.endsWith("/**")) {
                String pathStart = path.substring(0, path.lastIndexOf("/") + 1);
                if (servletPath.startsWith(pathStart)) {
                    return true;
                }
                String pathStart2 = path.substring(0, path.lastIndexOf("/"));
                if (servletPath.equals(pathStart2)) {
                    return true;
                }
            }
        }
        return false;
    }
}