package com.kapcb.framework.logging.configuration;

import com.kapcb.framework.logging.collector.Collector;
import com.kapcb.framework.logging.collector.impl.NothingLogCollector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executor;

/**
 * <a>Title: AopLogAutoConfiguration </a>
 * <a>Author: Kapcb <a>
 * <a>Description: AopLogAutoConfiguration <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/26 23:10
 */
@Slf4j
public abstract class AopLogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = {Collector.class})
    public Collector defaultLogCollector() {
        log.info("[ can not found log collector, will use default log collector ]");
        return new NothingLogCollector();
    }

    @Bean
    @ConditionalOnMissingBean(name = "asyncExecutor")
    public abstract Executor asyncExecutor();

}
