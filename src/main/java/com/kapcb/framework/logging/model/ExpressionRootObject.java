package com.kapcb.framework.logging.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <a>Title: ExpressionRootObject </a>
 * <a>Author: Kapcb <a>
 * <a>Description: ExpressionRootObject <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/26 22:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpressionRootObject {

    private Object target;

    private Object[] args;
}
