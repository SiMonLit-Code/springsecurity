package com.czz.securitydemo;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.czz.securitydemo.bean.User;
import com.czz.securitydemo.token.JWTUtil;
import com.czz.securitydemo.token.TokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class SecuritydemoApplicationTests {

    @Test
    void contextLoads() {
        User user = new User();
        user.setId(1l);
        user.setLogin("chen");
        String hello = JWTUtil.createToken(user, 100000l, "hello");
        System.out.println(hello);
        DecodedJWT hello1 = JWTUtil.verifyToken(hello, "hello");
        System.out.println(hello1.getPayload());
        System.out.println(hello1);
    }

    @Test
    void contextLoads1() throws JsonProcessingException {
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"hh");
        map.put(2,"gege");
        map.put(3,"qq");
        System.out.println(new ObjectMapper().writeValueAsString(map));
    }

}
