package com.kapcb.framework.logging.configuration;

import com.kapcb.framework.common.constants.enums.IntegerPool;
import com.kapcb.framework.logging.collector.Collector;
import com.kapcb.framework.logging.collector.DefaultEmptyLogCollector;
import com.kapcb.framework.logging.processor.ILog;
import com.kapcb.framework.web.configuration.AsyncConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


/**
 * <a>Title: LogAutoConfiguration </a>
 * <a>Author: Kapcb <a>
 * <a>Description: LogAutoConfiguration <a>
 *
 * @author Kapcb
 * @version 1.0
 * @date 2021/11/26 23:10
 * @since 1.0
 */
@Slf4j
@Configuration
public class LogAutoConfiguration {

    private static final String DEFAULT_COLLECTOR_NAME_PREFIX = "defaultCollectorAsyncExecutor-";

    /**
     * If there don't have any log collector in configuration
     * will use default NothingLogCollector as an alternative
     * <p>
     * DefaultEmptyLogCollector {@link DefaultEmptyLogCollector}
     *
     * @return {@link Collector}
     * @since 1.0
     */
    @Bean
    @ConditionalOnMissingBean(value = {Collector.class})
    public Collector<? extends ILog> defaultLogCollector() {
        log.info("[ can not found log collector, will use default log collector ]");
        return new DefaultEmptyLogCollector();
    }

    /**
     * configuration default async collector thread pool executor
     * if spring application context can not find asyncExecutor,
     * log executor will automatic assembly this default collector
     * async executor into spring application context
     *
     * @return {@link Executor}
     * @since 1.0
     * asyncExecutor {@link AsyncConfiguration#asyncExecutor()}
     */
    @Bean
    @ConditionalOnMissingBean(value = {Executor.class})
    public Executor defaultCollectorAsyncExecutor() {
        log.info("[ can not found any customer async executor, will use default collector async executor ]");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(IntegerPool.FIVE.value());
        executor.setMaxPoolSize(IntegerPool.FIVE.value());
        executor.setQueueCapacity(IntegerPool.TWO_HUNDRED_FIFTY_SIX.value());
        executor.setKeepAliveSeconds(IntegerPool.THIRTY.value());
        executor.setThreadNamePrefix(DEFAULT_COLLECTOR_NAME_PREFIX);
        executor.setRejectedExecutionHandler((r, e) -> log.error("default collector async executor's thread queue is full, current queue capacity is :[{}], active count is :[{}]", e.getQueue(), e.getActiveCount()));
        executor.initialize();
        return executor;
    }

}
