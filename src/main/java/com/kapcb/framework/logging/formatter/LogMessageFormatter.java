package com.kapcb.framework.logging.formatter;

import cn.hutool.core.util.StrUtil;
import com.kapcb.framework.logging.exception.LogOperationException;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * <a>Title: LogMessageFormatter </a>
 * <a>Author: Kapcb <a>
 * <a>Description: LogMessageFormatter <a>
 *
 * @author Kapcb
 * @version 1.0
 * @date 2021/11/27 15:17
 * @since 1.0
 */
@UtilityClass
public class LogMessageFormatter {

    private static final String DELIMITER = "{}";
    private static final String ESCAPE_CHAR = "\\";
    private static final String EMPTY = "";

    public static String format(String template, Object... args) {
        if (StringUtils.isBlank(template) || Objects.isNull(args)) {
            throw new LogOperationException("log message formatter's template or args can not be null");
        }
        return StrUtil.format(template, args);
    }

}
