package com.czz.securitydemo.filter;

import com.czz.securitydemo.bean.User;
import com.czz.securitydemo.token.Token;
import com.czz.securitydemo.token.TokenProvider;
import com.czz.securitydemo.util.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-11-06 18:03:00
 * @description :
 */
@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        List<String> authorities = ((UserDetails) authentication.getPrincipal()).getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        User user = new User();
        user.setLogin(username);
        user.setRole(authorities.stream().findFirst().get());
        //登陆成功生成token
        Token token = new TokenProvider("HelloWorld", 10000).createToken(user);
//        ResponseUtil.out(response,);
    }
}
