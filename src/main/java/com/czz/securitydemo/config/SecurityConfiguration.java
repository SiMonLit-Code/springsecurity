package com.czz.securitydemo.config;

import com.czz.securitydemo.token.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-11-04 11:35:00
 * @description :
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //判断角色还是权限的区分是前面"ROLE_"
        http
                .authorizeRequests()
                    .antMatchers("/product/**").hasRole("USER")//在添加角色new SimpleGrantedAuthority("ROLE_USER")
                    .antMatchers("/admin/**").hasAnyAuthority("ADMIN")//添加角色new SimpleGrantedAuthority("ADMIN")
                    .antMatchers("/context/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        http.authorizeRequests().anyRequest().authenticated().and().apply(securityConfigurerAdapter());
    }

    /**
     * 没有写登录页面，springsecurity自动提供登陆页面
     */
    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }*/



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth
                .inMemoryAuthentication()
                .withUser("admin1")//管理员，同时具有ADMIN,SUER权限，可以访问所有资源
                    .password("{noop}admin1")
                    .roles("ADMIN","USER")
                .and()
                .withUser("user1")//普通用户，只能访问/product/**
                    .password("{noop}user1")
                    .roles("USER");*/



        //设置自定义的userDetailsService
        auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equals(charSequence.toString());
            }
        });//设置自定义的userDetailsService

    }

    /*@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/
    private MyAuthTokenConfigurer securityConfigurerAdapter(){
        TokenProvider tokenProvider = new TokenProvider("SuiBianXie",3000);
        return new MyAuthTokenConfigurer(tokenProvider,userDetailsService);
    }

}
