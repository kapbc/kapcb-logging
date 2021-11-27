package com.kapcb.framework.logging.formatter;

/**
 * <a>Title: MessageFormatter </a>
 * <a>Author: Kapcb <a>
 * <a>Description: MessageFormatter <a>
 *
 * @author Kapcb
 * @version 1.0
 * @date 2021/11/27 15:17
 * @since 1.0
 */
public interface MessageFormatter {

    String format(String template, Object... args);

}
