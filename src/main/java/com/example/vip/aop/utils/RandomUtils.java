package com.example.vip.aop.utils;

import cn.hutool.core.text.StrBuilder;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 随机产生数据的工具类
 *
 * @Author: Zyred
 * @Date: 2020/2/28 19:20
 */
public class RandomUtils {

    /**
     * 自动生成名字（中文）
     *
     * @param len
     * @return
     */
    public static String getRandomName(int len) {
        String ret = "";
        for (int i = 0; i < len; i++) {
            String str = null;
            // 定义高低位
            int heightPos, lowPos;
            Random random = new Random();
            // 获取高位值
            heightPos = (176 + Math.abs(random.nextInt(39)));
            // 获取低位值
            lowPos = (161 + Math.abs(random.nextInt(93)));
            byte[] b = new byte[2];
            b[0] = (new Integer(heightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            try {
                // 转成中文
                str = new String(b, "GBK");
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            ret += str;
        }
        return ret;
    }

    /**
     * 生成随机用户名，数字和字母组成,
     *
     * @param length
     * @return
     */
    public static String getStringRandom(int length) {
        // 这里原来使用的是StringBuilder，但是由于StringBuilder每次生成新字符串需要重新构建对象
        StrBuilder builder = StrBuilder.create();
        Random random = new Random();
        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                builder.append((char) (random.nextInt(26) + temp));
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                builder.append(String.valueOf(random.nextInt(10)));
            }
        }
        return builder.toString();
    }

    /**
     * 随机10位数
     * @return
     */
    public static String getTenRandom(){
        return MathUtils.makeUpNewData(Thread.currentThread().hashCode() + "", 3) + MathUtils.randomDigitNumber(7);
    }
}
