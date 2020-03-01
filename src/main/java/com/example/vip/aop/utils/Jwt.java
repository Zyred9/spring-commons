package com.example.vip.aop.utils;

import com.example.vip.aop.common.exception.SysException;
import com.example.vip.aop.user.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.security.KeyRep.Type.SECRET;

/**
 * JWT配置类
 * @author Zyred
 */
public class Jwt {

    private static Logger logger = LoggerFactory.getLogger(Jwt.class);
    /**
     * JWT过滤后request中存放用户信息的参数名
     * */
    public static final String USER_KEY = "userId";
    /**
     * 凭证key
     */
    public final static String JWT_KEY = "token";
    /**
     * 加密秘钥
     */
    private static final String JWT_SECRET = "aHR0cHM6Ly9teS5vc2NoaW5hLm5ldC91LzM2ODE4Njg=";

    /**
     * 有效时间(小时)
     */
    private static final int JWT_EXPIRE = 12;
    /**
     * 生成Token签名
     * @param password 用户密码
     * @return 签名字符串
     */
    public static Map<String,Object> generateTokenMap(String password) {
        Date nowDate = new Date();
        Date expireDate = getExpireDate();
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(String.valueOf(password))
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
        Map<String,Object> tokenMap = new HashMap<>();
        tokenMap.put(Constant.TOKEN_KEY, token);
        tokenMap.put(Constant.EXPIRE_TIME, expireDate);
        return tokenMap;
    }

    private static Key generatorKey() {
        SignatureAlgorithm saa = SignatureAlgorithm.HS256;
        byte[] bin = DatatypeConverter.parseBase64Binary(JWT_SECRET);
        Key key = new SecretKeySpec(bin, saa.getJcaName());
        return key;
    }

    public static String generatorToken(Map<String, Object> payLoad) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return Jwts.builder().setPayload(objectMapper.writeValueAsString(payLoad))
                    .signWith(SignatureAlgorithm.HS256, generatorKey()).compact();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Claims phaseToken(String token) {
        Jws<Claims> claimsJwt = Jwts.parser().setSigningKey(generatorKey()).parseClaimsJws(token);
        return claimsJwt.getBody();
    }


    /**
     * 只获取Token
     * @param password
     * @return
     */
    public static String getToken(String password){
        return String.valueOf(Jwt.generateTokenMap(password).get(Constant.TOKEN_KEY));
    }

    /**
     * 获取过期时间
     * @author geYang
     * */
    private static Date getExpireDate() {
        return new Date(System.currentTimeMillis() + JWT_EXPIRE * 3600000);
    }

    /**
     * 获取签名信息
     */
    public static Claims getClaimByToken(String token) {
        try {
            return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            logger.debug("JWT TOKEN 校验失败", e);
            return null;
        }
    }

    /**
     * 判断Token是否过期
     */
    public static boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

    /**
     * SHA-256 密码加密
     * @param password 未加密密码
     * @return String 加密后密码
     * @author geYang
     * */
    public static String SHA256(String password) {
        try {
            byte[] bytes = password.getBytes("UTF-8");
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(bytes);
            return new String(encodeHex(digest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new SysException("系统加密处理异常");
        }
    }

    /**
     * 转16进制
     * */
    private static char[] encodeHex(final byte[] data) {
        final int l = data.length;
        final char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & data[i]];
        }
        return out;
    }
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
}
