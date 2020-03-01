package com.example.vip.aop.controllers.commonservice;

import com.example.vip.aop.utils.Response;

/**
 * @Author: Zyred
 * @Date: 2020/2/28 18:44
 */
public interface CommonService {

    /**
     * 发送邮件
     * @param email 邮箱
     * @return
     */
    Response<String> sendEmail(String email);

}
