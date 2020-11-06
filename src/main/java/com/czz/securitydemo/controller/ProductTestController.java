package com.czz.securitydemo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-11-04 11:47:00
 * @description :
 */
@Controller
@RequestMapping("/product")
public class ProductTestController {

    @RequestMapping("/info")
    @ResponseBody
    @PreAuthorize("hasAuthority('USER')")
    public String productInfo(){
        //获取当前用户信息
        String currentUser = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails){
            currentUser = ((UserDetails)principal).getUsername();
        }else {
            currentUser = principal.toString();
        }
        return " some product info,currentUser is: "+currentUser;
    }
}
