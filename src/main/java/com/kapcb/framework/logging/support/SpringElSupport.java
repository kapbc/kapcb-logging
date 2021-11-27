package com.kapcb.framework.logging.support;

import com.kapcb.framework.common.constants.enums.IntegerPool;
import com.kapcb.framework.logging.model.ExpressionRootObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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
public class SpringElSupport {

    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
    private final Map<String, Expression> expressions = new HashMap<>(IntegerPool.FOUR.value());

    public Object getByExpression(Method method, Object target, Object[] args, String springElExpression) {
        try {
            if (StringUtils.hasLength(springElExpression)) {
                Expression expression;
                MethodBasedEvaluationContext methodBasedEvaluationContext = new MethodBasedEvaluationContext(new ExpressionRootObject(target, args), method, args, parameterNameDiscoverer);
                if (expressions.containsKey(springElExpression)) {
                    return expressions.get(springElExpression).getValue(methodBasedEvaluationContext);
                } else {
                    expression = spelExpressionParser.parseExpression(springElExpression);
                    Object value = expression.getValue(methodBasedEvaluationContext);
                    expressions.put(springElExpression, expression);
                    return value;
                }
            }
        } catch (Exception e) {
            log.error("get by expression error, spring el expression is : {}, error message is : {}", springElExpression, e.getMessage());
        }
        return springElExpression;
    }

}
