package com.avaje.ebeaninternal.api;

import com.avaje.ebean.ExpressionFactory;
import com.avaje.ebeaninternal.server.expression.FilterExprPath;

public interface SpiExpressionFactory extends ExpressionFactory {
  ExpressionFactory createExpressionFactory(FilterExprPath paramFilterExprPath);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\SpiExpressionFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */