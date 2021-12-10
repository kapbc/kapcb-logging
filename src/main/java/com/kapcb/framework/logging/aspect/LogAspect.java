package com.kapcb.framework.logging.aspect;

import com.kapcb.framework.logging.annotation.Logging;
import com.kapcb.framework.logging.processor.LogProcessor;
import com.kapcb.framework.logging.properties.LogInfoProperties;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * <a>Title: LogAspect </a>
 * <a>Author: Kapcb <a>
 * <a>Description: LogAspect <a>
 *
 * @author Kapcb
 * @version 1.0
 * @date 2021/11/27 16:02
 * @since 1.0
 */
@Aspect
@Component
@EnableAspectJAutoProxy(exposeProxy = true)
public final class LogAspect extends AbstractLogAspect {

    private final LogProcessor logProcessor;

    @Autowired
    public LogAspect(LogProcessor logProcessor) {
        this.logProcessor = logProcessor;
    }

    @Override
    @Pointcut(value = "@annotation(com.kapcb.framework.logging.annotation.Logging)||@within(com.kapcb.framework.logging.annotation.Logging)")
    public void logPointCut() {
    }

    @Override
    @Around("logPointCut()")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return logProcessor.proceed(getLogProperties(proceedingJoinPoint), proceedingJoinPoint);
    }

}
