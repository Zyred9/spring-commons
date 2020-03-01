package com.example.vip.aop.controllers.commonservice.impl;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.example.vip.aop.common.enum1.ResponseEnum;
import com.example.vip.aop.controllers.commonservice.CommonService;
import com.example.vip.aop.utils.Constant;
import com.example.vip.aop.utils.MathUtils;
import com.example.vip.aop.utils.RedisUtil;
import com.example.vip.aop.utils.Response;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;

/**
 * 公共服务层
 * @Author: Zyred
 * @Date: 2020/2/28 18:43
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private RedisUtil redisUtil;

    /** 验证码长度 **/
    private final int codeLength = 4;

    @Override
    public Response<String> sendEmail(String email) {
        ThreadUtil.execute(()->{
            String key = Constant.REGISTRY_EMAIL_CODE_REDIS_KEY.concat(email);
            String value = MathUtils.randomDigitNumber(codeLength);
            redisUtil.set(key, value, Constant.REGISTRY_EMAIL_CODE_FAILURE_TIME);
            final String subject = "XX系统注册验证码";
            String content = StrBuilder.create().append("亲爱的用户：")
                    .append(email)
                    .append("\n 您的验证码为：")
                    .append(value)
                    .append("\n")
                    .append("请您在5分中内完成注册，如若5分钟后验证码将失效，需要您再次发送邮件获取验证码！\n")
                    .append("发送人：")
                    .append("Zyred")
                    .toString();

            MailUtil.send(email, subject, content, false);
        });
        return Response.ok(ResponseEnum.OK_SUCCESS.getCode(), ResponseEnum.OK_SUCCESS.getMsg());
    }
}
