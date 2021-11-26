package com.kapcb.framework.logging.model;

import lombok.Data;

import java.util.Date;

/**
 * <a>Title: LogData </a>
 * <a>Author: Kapcb <a>
 * <a>Description: LogData <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/26 23:02
 */
@Data
public class LogData implements ILog {

    private LogData() {
    }

    private static final ThreadLocal<LogData> LOG_DATA_THREAD_LOCAL = new ThreadLocal<>();

    private static final ThreadLocal<StringBuilder> STRING_BUILDER_THREAD_LOCAL = new ThreadLocal<>();

    private String serverName;

    private String host;

    private Integer port;

    private String clientIp;

    private String requestUrl;

    private String httpMethod;

    private Object headers;

    private String content;

    private Object responseBody;

    private Date logDate;

    private Long costTime;

    private String threadName = Thread.currentThread().getName();

    private Long threadId = Thread.currentThread().getId();

    private Boolean success;

}
