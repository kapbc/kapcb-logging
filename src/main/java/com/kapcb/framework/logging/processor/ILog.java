package com.kapcb.framework.logging.processor;

import java.util.Date;

/**
 * <a>Title: ILog </a>
 * <a>Author: Kapcb <a>
 * <a>Description: ILog <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/25 22:30
 */
public interface ILog {

    void setServerName(String serverName);

    void setHost(String host);

    void setPort(Integer port);

    void setClientIp(String clientIp);

    void setRequestUrl(String requestUrl);

    void setHttpMethod(String httpMethod);

    void setProcessMethod(String processMethod);

    void setHeaders(Object headers);

    String getContent();

    void setContent(String content);

    void setArgs(Object args);

    void setResponse(Object response);

    void setLogDate(Date logDate);

    Date getLogDate();

    void setCostTime(Long costTime);

    void setThreadName(String threadName);

    void setThreadId(Long threadId);

    void setSuccess(Boolean success);
}
