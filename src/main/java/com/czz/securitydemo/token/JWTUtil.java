package com.czz.securitydemo.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.czz.securitydemo.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-11-05 14:49:00
 * @description :
 */
public class JWTUtil {
    private static final String EXP = "exp";
    private static final String PAYLOAD = "payload";
    private static Logger logger = LoggerFactory.getLogger(JWTUtil.class);

    /**
     * 加密生成token
     * @param user 载体信息
     * @param maxAge 有效时长
     * @param secret 服务器私钥
     * @param <T>
     * @return
     */
    public static <T> String createToken(User user, long maxAge, String secret){
        try {
            Algorithm signer = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("签发者")
                    .withSubject("用户")
                    .withClaim("USERID",user.getId())
                    .withClaim("LOGIN",user.getLogin())
                    .withClaim("ROLE", user.getRole())
                    .withExpiresAt(new Date(System.currentTimeMillis()+maxAge))
                    .sign(signer);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("生成token异常",e);
        }
        return null;
    }

    /**
     * 解析token
     * @param token 加密后的token
     * @param secret 私钥
     * @return
     */
    public static DecodedJWT verifyToken(String token, String secret){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            System.out.println("检验失败");
        }
        return null;
    }

}
