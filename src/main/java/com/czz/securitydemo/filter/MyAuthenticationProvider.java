package com.czz.securitydemo.filter;

import com.czz.securitydemo.userservice.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-11-06 10:35:00
 * @description : 用户认证
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails == null){
            throw new BadCredentialsException("用户不存在");
        }

        if (!password.equals(userDetails.getPassword())){
            throw new BadCredentialsException("密码错误");
        }
        //用户名密码认证成功
        return new UsernamePasswordAuthenticationToken(userDetails,password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
