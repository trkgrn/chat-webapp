package com.trkgrn.chat.api.interceptor;

import com.trkgrn.chat.api.exception.ExpiredJwtExc;
import com.trkgrn.chat.api.exception.NullPointerExc;
import com.trkgrn.chat.api.model.concretes.Token;
import com.trkgrn.chat.api.service.concretes.TokenService;
import com.trkgrn.chat.config.jwt.service.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getHeader("Authorization") != null) {
            String jwt = request.getHeader("Authorization").substring(7);
            String username = "";
            Token tokenObj;

            try {
                username = jwtUtil.extractUsername(jwt);
                tokenObj = tokenService.findTokenByUsername(username);
            } catch (NullPointerException e) {
                throw new NullPointerExc("Oturum süresi sona erdi.");
            } catch (ExpiredJwtException e) {
                throw new ExpiredJwtExc("Oturun süresi sona erdi.");
            }
            System.out.println("Hi");
            if (jwt.equals(tokenObj.getJwt())) { // request headerından gelen token rediste bulunuyor mu?
                if(jwtUtil.tokenExpiredMinutes(jwt)<30){ // token'ın kalan süresi 30 dakikadan az ise
                    //Refresh Token
                    log.info("Token yenile");
                    response.setHeader("Deneme","Tarik");

                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request,response,handler,modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
