package com.czz.securitydemo.user;

import com.czz.securitydemo.bean.User;
import com.czz.securitydemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-11-04 16:18:00
 * @description :
 */
@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        //1.查询用户
        User userFromDatabase = userRepository.findOneByLogin(login);
        if (userFromDatabase == null){
            //找到不到抛异常
            throw new UsernameNotFoundException("User " + login + " was not found in db.");
        }
        //2.设置角色
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userFromDatabase.getRole());
        grantedAuthorities.add(grantedAuthority);

        //返回springsecurity的User对象
        return new org.springframework.security.core.userdetails.User(
                login,
                userFromDatabase.getPassword(),
                grantedAuthorities);
    }
}
