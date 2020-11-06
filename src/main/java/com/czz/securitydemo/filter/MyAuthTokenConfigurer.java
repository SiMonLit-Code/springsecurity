package com.czz.securitydemo.filter;

import com.czz.securitydemo.filter.MyTokenFilter;
import com.czz.securitydemo.token.TokenProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-11-05 17:28:00
 * @description :
 */
public class MyAuthTokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private TokenProvider tokenProvider;

    private UserDetailsService detailsService;

    public MyAuthTokenConfigurer(TokenProvider tokenProvider, UserDetailsService detailsService) {
        this.tokenProvider = tokenProvider;
        this.detailsService = detailsService;
    }

    @Override
    public void configure(HttpSecurity builder) {
        MyTokenFilter customFilter = new MyTokenFilter(detailsService,tokenProvider);
        builder.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
