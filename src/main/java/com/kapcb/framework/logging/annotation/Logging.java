package com.kapcb.framework.logging.annotation;

import com.kapcb.framework.logging.collector.ILogCollector;
import com.kapcb.framework.logging.collector.impl.NothingLogCollector;
import org.springframework.http.HttpHeaders;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <a>Title: Logging </a>
 * <a>Author: Kapcb <a>
 * <a>Description: Logging <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/26 23:17
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Logging {

    boolean logOnError() default false;

    String tags() default "undefined";

    String[] headers() default {HttpHeaders.USER_AGENT, HttpHeaders.CONTENT_TYPE};

    boolean args() default true;

    boolean response() default true;

    boolean stackTraceOnError() default false;

    boolean enableAsync() default true;

    Class<? extends ILogCollector> collector() default NothingLogCollector.class;

}
