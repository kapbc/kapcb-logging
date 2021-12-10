package com.kapcb.framework.logging.collector;

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
public class DefaultEmptyLogCollector implements ILogCollector {

    @Override
    public void collect(ILog log) {
        // empty poll  do nothing
        // by order of awesome kapcb
    }

}
