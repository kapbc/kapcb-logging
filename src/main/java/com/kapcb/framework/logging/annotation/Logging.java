package com.kapcb.framework.logging.annotation;

import com.kapcb.framework.logging.collector.Collector;
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

    String tag() default "undefined";

    String[] headers() default {HttpHeaders.USER_AGENT, HttpHeaders.CONTENT_TYPE};

    boolean args() default true;

    boolean responseBody() default true;

    boolean stackTraceOnError() default false;

    boolean enableAsync() default true;

    Class<? extends Collector> collector() default NothingLogCollector.class;
}
