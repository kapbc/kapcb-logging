package com.kapcb.framework.logging.properties;

import com.kapcb.framework.logging.collector.ILogCollector;
import com.kapcb.framework.logging.collector.impl.NothingLogCollector;
import lombok.Data;
import org.springframework.http.HttpHeaders;

/**
 * <a>Title: LogProperties </a>
 * <a>Author: Kapcb <a>
 * <a>Description: LogProperties <a>
 *
 * @author Kapcb
 * @version 1.0
 * @date 2021/11/27 12:28
 * @since 1.0
 */
@Data
public class LogProperties {

    /**
     * 仅当发生异常错误是打印日志
     */
    private Boolean logOnError;

    /**
     * 记录请求headers, 默认记录 content-type user-agent
     */
    private String[] headers;

    /**
     * 入参
     */
    private Boolean args;

    /**
     * 出参
     */
    private Boolean response;

    /**
     * 错误堆栈信息
     */
    private Boolean stackTraceOnError;

    /**
     * 异步收集日志
     */
    private Boolean enableAsync;

    /**
     * 收集器
     */
    private Class<? extends ILogCollector> collector;

    public LogProperties() {
        this.logOnError = false;
        this.headers = new String[]{HttpHeaders.CONTENT_TYPE, HttpHeaders.USER_AGENT};
        this.args = true;
        this.response = true;
        this.stackTraceOnError = true;
        this.enableAsync = true;
        this.collector = NothingLogCollector.class;
    }
}
