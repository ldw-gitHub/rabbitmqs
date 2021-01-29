package com.framemark.auth;

import com.framemark.annotation.LoginRequired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description
 * @author: liudawei
 * @date: 2020/12/14 15:13
 */
@Slf4j
public class SourceAccessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("[ 进入拦截器 ]");

        //反射获取方法上的LoginRequred注解
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginRequired loginRequired = handlerMethod.getMethod().getAnnotation(LoginRequired.class);
        if (loginRequired == null) {
            return true;
        }

        //有loginRequired说明需要登入，提示用户登入
        String auth = request.getHeader("authorization");
        if (StringUtils.isEmpty(auth)) {
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().print("你访问的资源需要登录");
            return false;
        }
        //具体认证
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
