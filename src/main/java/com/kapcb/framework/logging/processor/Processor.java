package com.kapcb.framework.logging.processor;

import com.kapcb.framework.logging.properties.LogProperties;
import com.kapcb.framework.logging.properties.Properties;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * <a>Title: Processor </a>
 * <a>Author: Kapcb <a>
 * <a>Description: Processor <a>
 *
 * @author Kapcb
 * @version 1.0
 * @date 2021/12/3 8:25
 * @since 1.0
 */
public interface Processor {

    Object proceed(Properties properties, ProceedingJoinPoint proceedingJoinPoint) throws Throwable;

}
