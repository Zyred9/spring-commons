package com.example.vip.aop.common.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域请求
 * 参考 ::: https://segmentfault.com/a/1190000019550329?utm_source=tag-newest
 * @Author: Zyred
 * @Date: 2020/2/27 15:35
 */
public class WebMvcConfigWhitCors /*implements WebMvcConfigurer*/ {

    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET","HEAD","POST","PUT","DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }*/
}
