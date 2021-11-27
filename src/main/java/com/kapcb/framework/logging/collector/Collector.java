package com.kapcb.framework.logging.collector;

/**
 * <a>Title: Collector </a>
 * <a>Author: Kapcb <a>
 * <a>Description: Collector <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/26 22:55
 */
@FunctionalInterface
public interface Collector<T> {

    /**
     * collector to collect log
     *
     * @param logInfo T
     */
    void collect(T logInfo);

}
