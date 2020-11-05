package com.czz.securitydemo.filter;

import com.czz.securitydemo.token.TokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-11-05 16:10:00
 * @description :
 */
public class MyTokenFilter extends GenericFilterBean {

    private final Logger log = LoggerFactory.getLogger(MyTokenFilter.class);

    private final static String XAUTH_TOKEN_HEADER_NAME = "my-auth-token";

    private UserDetailsService userDetailsService;

    private TokenProvider tokenProvider;

    public MyTokenFilter(UserDetailsService userDetailsService, TokenProvider tokenProvider) {
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String authToken = httpServletRequest.getHeader(XAUTH_TOKEN_HEADER_NAME);
            if (StringUtils.hasText(authToken)){
                //从自定义tokenProvider中解析用户
                String username = tokenProvider.getUserNameFromToken(authToken);
                if (username == null){
                    throw new RuntimeException("token有误");
                }
                UserDetails details = userDetailsService.loadUserByUsername(username);
                if (tokenProvider.verifyToken(authToken)){
                    log.debug("validateToken is ok ...");
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details,details.getPassword(),details.getAuthorities());
                    //存放认证信息,没有走到这步doFilter会提示登录
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
            //调用后续的Filter，如果上面的代码逻辑非能复原session，SecurityContext中没有信息，后面流程会检测出“需要登录”
            filterChain.doFilter(servletRequest,servletResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
