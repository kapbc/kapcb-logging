package com.kapcb.framework.logging.exception;

/**
 * <a>Title: LogOperationException </a>
 * <a>Author: Kapcb <a>
 * <a>Description: LogOperationException <a>
 *
 * @author Kapcb
 * @version 1.0
 * @date 2021/11/27 15:14
 * @since 1.0
 */
public class LogOperationException extends RuntimeException{

    public LogOperationException() {
    }

    public LogOperationException(String message) {
        super(message);
    }

    public LogOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogOperationException(Throwable cause) {
        super(cause);
    }

    public LogOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
