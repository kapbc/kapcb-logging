package com.kapcb.framework.logging.aspect;

import com.kapcb.framework.logging.annotation.Logging;
import com.kapcb.framework.logging.properties.LogInfoProperties;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Objects;

/**
 * <a>Title: AbstractLogAspect </a>
 * <a>Author: Kapcb <a>
 * <a>Description: AbstractLogAspect <a>
 *
 * @author Kapcb
 * @version 1.0
 * @date 2021/12/10 21:54
 * @since 1.0
 */
public abstract class AbstractLogAspect {



    protected static LogInfoProperties getLogProperties(ProceedingJoinPoint proceedingJoinPoint) {
        LogInfoProperties logProperties = new LogInfoProperties();
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Logging logging = signature.getMethod().getAnnotation(Logging.class);
        if (Objects.isNull(logging)) {
            logging = proceedingJoinPoint.getTarget().getClass().getAnnotation(Logging.class);
        }
        if (Objects.nonNull(logging)) {
            logProperties.setLogOnError(logging.logOnError());
            logProperties.setHeaders(logging.headers());
            logProperties.setArgs(logging.args());
            logProperties.setTag(logging.tags());
            logProperties.setResponse(logging.response());
            logProperties.setStackTraceOnError(logging.stackTraceOnError());
            logProperties.setEnableAsync(logging.enableAsync());
            logProperties.setCollector(logging.collector());
        }
        return logProperties;
    }

}
