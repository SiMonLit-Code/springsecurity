package com.czz.securitydemo.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-11-06 18:16:00
 * @description :
 */
public class ResponseUtil {

    /**
     * 使用response输出Json
     * @param response
     * @param resultMap
     */
    public static void out(HttpServletResponse response, Map<String, Object> resultMap){
        ServletOutputStream out = null;
        try{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            out = response.getOutputStream();
            out.write(new ObjectMapper().writeValueAsString(resultMap).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (out != null){
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
