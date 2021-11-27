package com.kapcb.framework.logging.processor;

import com.kapcb.framework.common.constants.enums.StringPool;
import com.kapcb.framework.logging.actuator.CollectorActuator;
import com.kapcb.framework.logging.collector.ILogCollector;
import com.kapcb.framework.logging.properties.LogProperties;
import com.kapcb.framework.logging.support.SpringElSupport;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
public class LogProcessor {

    private final String serverName;

    private final ILogCollector logCollector;
    private final CollectorActuator collectorActuator;
    private final ApplicationContext applicationContext;

    private final SpringElSupport springElSupport = new SpringElSupport();
    private final Map<Class<? extends ILogCollector>, ILogCollector> collectors = new ConcurrentHashMap<>(4);


    @Autowired
    public LogProcessor(ApplicationContext applicationContext,
                        CollectorActuator collectorActuator,
                        ILogCollector logCollector) {
        this.applicationContext = applicationContext;
        this.collectorActuator = collectorActuator;
        this.logCollector = logCollector;
        this.serverName = getServerName(applicationContext);
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

    public String getServerName(){
        return this.serverName;
    }

    public Object proceed(LogProperties logProperties, ProceedingJoinPoint proceedingJoinPoint){

    }

}
