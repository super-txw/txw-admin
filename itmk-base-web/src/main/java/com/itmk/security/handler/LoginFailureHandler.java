package com.itmk.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.itmk.result.ResultUtils;
import com.itmk.security.exception.ImageCodeException;
import com.itmk.security.exception.TokenException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录认证失败处理器
 */
@Component("loginFailureHandler")
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //1.设置响应编码
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = httpServletResponse.getOutputStream();
        String str = null;
        int code = 500;
        if(e instanceof AccountExpiredException){
            str = "账户过期，登录失败!";
        }else if(e instanceof BadCredentialsException){
            str = "用户名或密码错误，登录失败!";
        }else if(e instanceof CredentialsExpiredException){
            str = "密码过期，登录失败!";
        }else if(e instanceof DisabledException){
            str = "账户被禁用，登录失败!";
        }else if(e instanceof LockedException){
            str = "账户被锁，登录失败!";
        }else if(e instanceof InternalAuthenticationServiceException){
            str = "账户不存在，登录失败!";
        }else if(e instanceof ImageCodeException){
            //验证码异常
            str = e.getMessage();
        } else if(e instanceof TokenException){
            //token异常
            code = 600;
            str = e.getMessage();
        }else{
            str = "登录失败!";
        }
        String res = JSONObject.toJSONString(ResultUtils.error(str,code,null), SerializerFeature.DisableCircularReferenceDetect);
        out.write(res.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}
