package com.kapcb.framework.logging.collector.impl;

import com.kapcb.framework.logging.collector.ILogCollector;
import com.kapcb.framework.logging.processor.ILog;

/**
 * <a>Title: NothingLogCollector </a>
 * <a>Author: Kapcb <a>
 * <a>Description: NothingLogCollector <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/26 23:15
 */
public class NothingLogCollector implements ILogCollector {

    @Override
    public void collect(ILog log) {

    }
}
