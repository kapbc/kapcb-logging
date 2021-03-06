package com.kapcb.framework.logging.support;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.kapcb.framework.common.constants.enums.StringPool;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * <a>Title: SpringElSupport </a>
 * <a>Author: Kapcb <a>
 * <a>Description: SpringElSupport <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/26 22:42
 */
@Slf4j
@UtilityClass
public class SpringElSupport {

    private static final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
    private static final ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    public static <T> T parse(String springEl, Method method, Object[] arguments, Class<T> clazz) {
        try {
            String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
            if (ArrayUtil.isNotEmpty(parameterNames)) {
                StandardEvaluationContext context = new StandardEvaluationContext();
                for (int i = 0; i < parameterNames.length; i++) {
                    context.setVariable(parameterNames[i], arguments[i]);
                }
                return parseExpression(springEl).getValue(context, clazz);
            }
        } catch (Exception e) {
            log.error("parse spring expression error, error message is : {}", e.getMessage());
        }
        return null;
    }

    private static Expression parseExpression(String springEl) {
        if (StrUtil.startWith(springEl, StringPool.SHARP.value() + StringPool.OPENING_PARENTHESIS.value()) && StrUtil.endWith(springEl, StringPool.CLOSED_PARENTHESIS.value())) {
            return spelExpressionParser.parseExpression(springEl, new TemplateParserContext());
        }
        return spelExpressionParser.parseExpression(springEl);
    }
}
