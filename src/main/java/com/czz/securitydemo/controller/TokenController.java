package com.czz.securitydemo.controller;

import com.czz.securitydemo.bean.User;
import com.czz.securitydemo.token.Token;
import com.czz.securitydemo.token.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-11-05 10:07:00
 * @description :
 */
@Controller
@RequestMapping("/context")
public class TokenController {
    @Autowired
    private AuthenticationProvider myAuthenticationProvider;
    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping(value = "/authenticate")
    @ResponseBody
    public Token authorize(@RequestParam String username, @RequestParam String password) {
        TokenProvider tokenProvider = new TokenProvider("SuiBianXie",3000);

        //1 创建UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password);
        //2 认证
        Authentication authentication = myAuthenticationProvider.authenticate(token);
        //3 保存认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //4 加载UserDetails
        UserDetails details = userDetailsService.loadUserByUsername(username);
        User user = new User();
        user.setLogin(details.getUsername());
        user.setRole(details.getAuthorities().stream().findFirst().get().toString());
        //5 生成自定义token
        return tokenProvider.createToken(user);
    }
}
