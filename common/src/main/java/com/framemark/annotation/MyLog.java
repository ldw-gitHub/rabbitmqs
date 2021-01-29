package com.framemark.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 日志注解
 * @author: liudawei
 * @date: 2020/12/14 14:41
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyLog {

    String logType() default "noType";

    String logModular() default  "noModular";
}
