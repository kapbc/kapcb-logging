package com.kapcb.framework.logging.actuator;

import com.kapcb.framework.logging.collector.Collector;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * <a>Title: CollectorActuator </a>
 * <a>Author: Kapcb <a>
 * <a>Description: CollectorActuator <a>
 *
 * @author Kapcb
 * @version 1.0
 * @date 2021/11/27 12:42
 * @since 1.0
 */
@Component
@EnableAsync
public class CollectorActuator {

    /**
     * async execute collector
     *
     * @param collector Collector<D>
     * @param log       D
     * @param <D>       <D>
     */
    @Async
    public <D> void asyncExecute(Collector<D> collector, D log) {
        collector.collect(log);
    }

    /**
     * execute collector
     *
     * @param collector Collector<D>
     * @param log       D
     * @param <D>       <D>
     */
    public <D> void execute(Collector<D> collector, D log) {
        collector.collect(log);
    }
}
