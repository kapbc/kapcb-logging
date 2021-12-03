package com.kapcb.framework.logging.processor;

import com.kapcb.framework.common.constants.enums.StringPool;
import com.kapcb.framework.common.util.ThrowableUtil;
import com.kapcb.framework.logging.actuator.CollectorActuator;
import com.kapcb.framework.logging.collector.ILogCollector;
import com.kapcb.framework.logging.collector.impl.DefaultEmptyLogCollector;
import com.kapcb.framework.logging.extractor.LogExtractor;
import com.kapcb.framework.logging.properties.LogProperties;
import com.kapcb.framework.logging.support.SpringElSupport;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a>Title: LogProcessor </a>
 * <a>Author: Kapcb <a>
 * <a>Description: LogProcessor <a>
 *
 * @author Kapcb
 * @version 1.0
 * @date 2021/11/27 14:46
 * @since 1.0
 */
@Component
@Scope("singleton")
public class LogProcessor implements ApplicationContextAware, InitializingBean {

    private String serverName;
    private ApplicationContext applicationContext;

    private final ILogCollector logCollector;
    private final CollectorActuator collectorActuator;

    private final Map<Class<? extends ILogCollector>, ILogCollector> collectors = new ConcurrentHashMap<>(4);

    @Autowired
    public LogProcessor(CollectorActuator collectorActuator,
                        ILogCollector logCollector) {
        this.collectorActuator = collectorActuator;
        this.logCollector = logCollector;
    }

    private String getServerName(ApplicationContext applicationContext) {
        Environment environment = applicationContext.getEnvironment();
        String serverName = environment.getProperty(StringPool.SERVER_APPLICATION_NAME.value());
        if (StringUtils.isNotBlank(serverName)) {
            return serverName;
        }
        if (StringUtils.isNotBlank(applicationContext.getId())) {
            return applicationContext.getId();
        }
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (ArrayUtils.isNotEmpty(stackTrace)) {
            StackTraceElement stackTraceElement = Arrays.stream(stackTrace).filter(stack -> StringPool.MAIN.value().equals(stack.getMethodName())).findAny().orElse(null);
            if (Objects.nonNull(stackTraceElement)) {
                return stackTraceElement.getFileName();
            }
        }
        return applicationContext.getApplicationName();
    }

    public Object proceed(LogProperties logProperties, ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            LogData.removeCurrent();
            ILog log = LogData.getCurrent();
            return proceed(logProperties, log, proceedingJoinPoint);
        } finally {
            LogData.removeCurrent();
        }
    }

    public Object proceed(LogProperties logProperties, ILog log, ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        Boolean success = false;

        try {
            result = proceedingJoinPoint.proceed();
            success = true;
            return result;
        } catch (Throwable throwable) {
            if (logProperties.getStackTraceOnError()) {
                String stackTrace = ThrowableUtil.getStackTrace(throwable);
                LogData.step("fail : " + stackTrace);
            }
            throw throwable;
        } finally {
            if (!logProperties.getLogOnError()) {
                log.setServerName(this.serverName);
                log.setCostTime(System.currentTimeMillis() - log.getLogDate().getTime());
                MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
                String tag = SpringElSupport.parse(logProperties.getTag(), signature.getMethod(), proceedingJoinPoint.getArgs(), String.class);
                System.out.println("tag = " + tag);
                log.setProcessMethod(signature.getDeclaringTypeName() + StringPool.SHARP.value() + signature.getName());
                LogExtractor.logHttpRequest(log, logProperties.getHeaders());
                if (logProperties.getArgs()) {
                    log.setArgs(LogExtractor.getArgs(signature.getParameterNames(), proceedingJoinPoint.getArgs()));
                }
                if (logProperties.getResponse()) {
                    log.setResponse(LogExtractor.getResult(result));
                }
                log.setSuccess(success);
                LogData.setCurrent(log);
                if (logProperties.getEnableAsync()) {
                    collectorActuator.asyncExecute(selectLogCollector(logProperties.getCollector()), LogData.getCurrent());
                } else {
                    collectorActuator.execute(selectLogCollector(logProperties.getCollector()), LogData.getCurrent());
                }
            }
        }
    }

    private ILogCollector selectLogCollector(Class<? extends ILogCollector> clazz) {
        if (Objects.isNull(clazz) || Objects.equals(clazz, DefaultEmptyLogCollector.class)) {
            return logCollector;
        } else {
            ILogCollector logCollector;
            try {
                logCollector = applicationContext.getBean(clazz);
            } catch (Exception e) {
                logCollector = collectors.get(clazz);
                if (Objects.isNull(logCollector)) {
                    logCollector = BeanUtils.instantiateClass(clazz);
                    collectors.put(clazz, logCollector);
                }
            }
            return logCollector;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.serverName = getServerName(applicationContext);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (Objects.isNull(this.applicationContext)) {
            this.applicationContext = applicationContext;
        }
    }

}
