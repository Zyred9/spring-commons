package com.example.vip.aop.controllers.commoncontroller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.example.vip.aop.common.annotation.Auth;
import com.example.vip.aop.common.enum1.AuthEnum;
import com.example.vip.aop.controllers.commonservice.CommonService;
import com.example.vip.aop.utils.Response;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 公共的controller  例如: 生成校验码
 *
 * @Author: Zyred
 * @Date: 2020/2/28 15:04
 */
@Controller
@Api(tags = "公共Controller")
@RequestMapping("/commons")
public class CommonController {

    @Value("${hutool.captcha.width}")
    private Integer width;

    @Value("${hutool.captcha.height}")
    private Integer height;

    @Autowired
    private CommonService commonService;

    @Auth(AuthEnum.NON)
    @ApiOperation("生成图形验证码")
    @PostMapping("/createLineCaptcha")
    public void createLineCaptcha(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 设置resp 返回类型，并且禁止缓存图片
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");
        try(ServletOutputStream outputStream = resp.getOutputStream()) {
            //定义图形验证码的长、宽、验证码字符数、干扰元素个数
            CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(width, height, 4, 20);
            //CircleCaptcha captcha = new CircleCaptcha(200, 100, 4, 20);
            //图形验证码写出，可以写出到文件，也可以写出到流
            captcha.write(outputStream);
            //验证图形验证码的有效性，返回boolean值
            // captcha.verify("1234");
        }
    }

    /**
     * 生成二维码
     * @param resp
     */
    @ResponseBody
    @Auth(AuthEnum.NON)
    @PostMapping("/createQrcode")
    @ApiOperation("生成二维码")
    public void createQrcode(HttpServletResponse resp, @RequestParam("url") String url) throws IOException {
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");
        try(OutputStream os = resp.getOutputStream()) {
            // 读法读取相对路径的文件
            QrCodeUtil.generate(url,
                    QrConfig.create().setImg("static/image/zyred.png")
                    .setErrorCorrection(ErrorCorrectionLevel.H),
                    ImgUtil.IMAGE_TYPE_JPG,
                    os);
        }
    }



    @ResponseBody
    @Auth(AuthEnum.NON)
    @GetMapping("/sendEmailCode")
    @ApiOperation("邮件发送登录验证码")
    public Response<String> sendEmailCode(@RequestParam("email") String email){
        return this.commonService.sendEmail(email);
    }


}
