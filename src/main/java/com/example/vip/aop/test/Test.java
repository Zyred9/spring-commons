package com.example.vip.aop.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Zyred
 */
public class Test {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encode = encoder.encode("admin");
            System.out.println(encode);
        }
    }

}
