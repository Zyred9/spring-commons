package com.example.vip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Springboot 启动类
 *
 * @author Zyred
 */
@SpringBootApplication
// tk.mybatis 包扫描
@MapperScan(basePackages = {"com.example.vip.*.*.mapper"})
public class SpringCommonsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCommonsApplication.class, args);
    }

}
