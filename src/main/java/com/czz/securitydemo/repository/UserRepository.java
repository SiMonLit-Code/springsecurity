package com.czz.securitydemo.repository;

import com.czz.securitydemo.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-11-04 16:23:00
 * @description :
 */
public interface UserRepository extends JpaRepository<User,String> {
    User findOneByLogin(String login);
}
