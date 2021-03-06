package com.czz.securitydemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-11-06 16:42:00
 * @description :
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();

        registry.and()
        //表单登录方式
                .formLogin().successForwardUrl("/product/info")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .authorizeRequests()
        //任何请求
                .anyRequest()
        //需要身份认证
                .authenticated()
                .and()
        //关闭跨站请求防护
                .csrf().disable()
        //前后端分离采用JWT 不需要session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}