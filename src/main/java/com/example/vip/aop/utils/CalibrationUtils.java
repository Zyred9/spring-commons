package com.example.vip.aop.utils;

import java.util.Objects;

/**
 * 参数校验
 *
 * @Author: Zyred
 * @Date: 2020/2/26 20:20
 */
public class CalibrationUtils {

    /**
     * 校验参数是否为空
     * @param paras
     * @return
     */
    public static boolean isNull(String... paras){
        for (int i = 0; i < paras.length; i++) {
            if (Objects.isNull(paras[i])) {
                return false;
            }
        }
        return true;
    }

}
