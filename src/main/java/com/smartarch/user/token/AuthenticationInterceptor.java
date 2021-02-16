package com.smartarch.user.token;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.smartarch.user.mapper.UserMapper;
import com.smartarch.user.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

	@Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object object) {
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod ();
        //检查是否有@passtoken注解，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation (PassToken.class);
            if (passToken.required ()) {
                return true;
            }
        }
        /*
		 * //检查有没有需要用户权限的注解 if (method.isAnnotationPresent (RequireToken.class)) {
		 * RequireToken requireToken = method.getAnnotation(RequireToken.class);
		 * if (requireToken.required ()) {
		 * 
		 * } }
		 */
        String token = request.getHeader("token");
        // 执行认证
        if(token != null) {
        	String userId = "";
        	try {
        		userId = JWT.decode(token).getAudience().get(0);
        	} catch (JWTDecodeException e) {
        		return sendError(response, e.getMessage());
        	}
        	User user = userMapper.getById(userId);
            if (user != null) {
            	// 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPasswords())).build ();
                jwtVerifier.verify (token);
                return true;
            } else {
            	return sendError(response, "Token with invalid user id.");
            }
        } else {
        	return sendError(response, "No token in header.");
        }
    }
    
    private boolean sendError(HttpServletResponse response, String msg) {
    	log.error(msg);
    	try {
			response.sendError(HttpStatus.UNAUTHORIZED.value(), msg);
		} catch (IOException e) {
			log.error("send error exception:", e);
		}
    	return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception { }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception { }
}
