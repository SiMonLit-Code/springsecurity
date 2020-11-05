package com.czz.securitydemo.token;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-11-05 10:49:00
 * @description :
 */
public class Token {
    private String token;
    private Long expires;

    public Token(String token, Long expires) {
        this.token = token;
        this.expires = expires;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }
}
