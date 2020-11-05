package com.czz.securitydemo.token;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Payload;
import com.czz.securitydemo.bean.User;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-11-05 10:23:00
 * @description : 生成token 校验token
 */

public class TokenProvider {
    //私钥
    private final String secretKey ;
    //有效期，单位秒
    private final int tokenValidity;

    public TokenProvider(String secretKey, int tokenValidity) {
        this.secretKey = secretKey;
        this.tokenValidity = tokenValidity;
    }

    /**
     * 生成token
     * @param userDetails
     * @return
     */
    public Token createToken(User userDetails){
        long expires = System.currentTimeMillis()+1000L*tokenValidity;
        String token = JWTUtil.createToken(userDetails,expires,secretKey);
        return new Token(token,expires);
    }

    /**
     * 校验token
     * @param authToken
     * @return
     */
    public Boolean verifyToken(String authToken){
        DecodedJWT decodedJWT = JWTUtil.verifyToken(authToken, secretKey);
        if (decodedJWT != null){
            Long exp = decodedJWT.getClaim("exp").asLong();
            long verityTime = System.currentTimeMillis() - exp;
            return tokenValidity > verityTime;
        }
        return false;
    }

    /**
     * 查询token中的用户名
     * @param authToken
     * @return
     */
    public String getUserNameFromToken(String authToken){
        DecodedJWT decodedJWT = JWTUtil.verifyToken(authToken, secretKey);
        if (decodedJWT != null){
            Claim login = decodedJWT.getClaim("LOGIN");
            return login.asString();
        }
        return null;
    }

}
