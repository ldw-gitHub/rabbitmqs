package com.framemark.aspect;

import com.alibaba.fastjson.JSONObject;
import com.framemark.annotation.MyLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @description
 * @author: liudawei
 * @date: 2020/12/14 14:43
 */

/**
 * 1、表明这是一个切面类
 */

@Aspect
@Component
@Slf4j
public class MyLogAspect {

    /**
     * 2、 pointCut表示这是一个切点，@annotation表示这个切点到一个注解上，后面带该注解得全类名，切面最主要得就是切点，所有的故事都围绕切点发生
     */
    @Pointcut("@annotation(com.framemark.annotation.MyLog)")
    public void logPointCut() {
    }

    /**
     * 3、环绕通知
     */
    @Around("logPointCut()")
    public void Around(ProceedingJoinPoint joinPoint) {
        long l = System.currentTimeMillis();
        String classType = joinPoint.getTarget().getClass().getName();
        log.info("进入日志切面； classType === " + classType);

        //获取请求头数据,获取用户信息
        HttpServletRequest httpServletRequest = currentRequest();
        String authorization = httpServletRequest.getHeader("Authorization");
        log.info("Authorization =========== " + authorization);

        //获取注解参数，缺点操作类型和操作模块
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        MyLog myLog = methodSignature.getMethod().getAnnotation(MyLog.class);
        log.info("请求类型：logType ======== " + myLog.logType());
        log.info("请求模块：logModular ======== " + myLog.logModular());

        // 获取方法名称
        String methodName = joinPoint.getSignature().getName();
        // 获取入参
        Object[] params = joinPoint.getArgs();

        try {
            String[] parameterNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();

            JSONObject param = new JSONObject();
            for (int i = 0; i < parameterNames.length; i++) {
                if (params[i] instanceof MultipartFile) {
                    param.put(parameterNames[i], ((MultipartFile) params[i]).getOriginalFilename());
                } else {
                    param.put(parameterNames[i], params[i]);
                }
            }

            log.info("进入【" + methodName + "]方法，参数为：" + param.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error(methodName + " ======================== 方法执行异常 ==========================");
            log.error(throwable.getMessage());
            throwable.printStackTrace();
        }

        log.info(methodName + " ========================== 方法执行时间： " + (System.currentTimeMillis() - l));
        log.info(methodName + " ========================== 方法执行结束 =============================");

    }

    private HttpServletRequest currentRequest() {
        // Use getRequestAttributes because of its return null if none bound
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Optional.ofNullable(servletRequestAttributes).map(ServletRequestAttributes::getRequest).orElse(null);
    }
}
