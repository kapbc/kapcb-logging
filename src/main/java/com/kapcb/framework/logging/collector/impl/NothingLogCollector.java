package com.kapcb.framework.logging.collector.impl;

import com.kapcb.framework.logging.collector.Collector;

/**
 * <a>Title: NothingLogCollector </a>
 * <a>Author: Kapcb <a>
 * <a>Description: NothingLogCollector <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/26 23:15
 */
public class NothingLogCollector implements Collector {

    @Override
    public void collect(Object data) {
        // do nothing
    }

}
