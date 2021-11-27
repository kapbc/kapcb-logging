package com.kapcb.framework.logging.processor;

import com.kapcb.framework.logging.exception.LogOperationException;
import com.kapcb.framework.logging.formatter.LogMessageFormatter;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Objects;

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

    private static final ThreadLocal<ILog> LOG_DATA_THREAD_LOCAL = new ThreadLocal<>();

    private static final ThreadLocal<StringBuilder> STRING_BUILDER_THREAD_LOCAL = new ThreadLocal<>();

    private String serverName;

    private String host;

    private Integer port;

    private String clientIp;

    private String requestUrl;

    private String httpMethod;

    private String processMethod;

    private Object headers;

    private String content;

    private Object args;

    private Object response;

    private Date logDate;

    private Long costTime;

    private String threadName = Thread.currentThread().getName();

    private Long threadId = Thread.currentThread().getId();

    private Boolean success;

    /**
     * get current thread log object
     *
     * @return ILog
     */
    protected static ILog getCurrent() {
        if (Objects.isNull(LOG_DATA_THREAD_LOCAL.get())) {
            ILog log = new LogData();
            log.setLogDate(new Date());
            StringBuilder sb = STRING_BUILDER_THREAD_LOCAL.get();
            if (StringUtils.isNotBlank(sb)) {
                STRING_BUILDER_THREAD_LOCAL.set(new StringBuilder());
            }
            LOG_DATA_THREAD_LOCAL.set(log);
        }
        return LOG_DATA_THREAD_LOCAL.get();
    }

    /**
     * set current thread's log object
     *
     * @param log ILog
     */
    protected static void setCurrent(ILog log) {
        if (StringUtils.isNotBlank(STRING_BUILDER_THREAD_LOCAL.get())) {
            log.setContent(STRING_BUILDER_THREAD_LOCAL.get().toString());
        }
        LOG_DATA_THREAD_LOCAL.set(log);
    }

    /**
     * remove current thread's log and log content
     */
    public static void removeCurrent() {
        STRING_BUILDER_THREAD_LOCAL.remove();
        LOG_DATA_THREAD_LOCAL.remove();
    }

    /**
     * log an request's all step
     *
     * @param step String
     */
    public static void step(String step) {
        if (StringUtils.isBlank(step)) {
            throw new LogOperationException("step can not be null");
        }
        StringBuilder sb = STRING_BUILDER_THREAD_LOCAL.get();
        if (StringUtils.isNotBlank(sb)) {
            sb.append(step).append("\n");
            STRING_BUILDER_THREAD_LOCAL.set(sb);
        }
    }

    /**
     * log all step in a method or request
     *
     * @param stepTemplate String
     * @param args         Object...
     */
    public static void step(String stepTemplate, Object... args) {
        step(LogMessageFormatter.format(stepTemplate, args));
    }

}
