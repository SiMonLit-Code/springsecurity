package com.czz.securitydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-11-03 15:21:00
 * @description :
 */
@Controller
public class AppController {
    @RequestMapping("/hello")
    @ResponseBody
    String home() {
        return "Hello ,spring security!";
    }
}
