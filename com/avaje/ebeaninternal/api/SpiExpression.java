package com.avaje.ebeaninternal.api;

import com.avaje.ebean.Expression;
import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;

public interface SpiExpression extends Expression {
  void containsMany(BeanDescriptor<?> paramBeanDescriptor, ManyWhereJoins paramManyWhereJoins);
  
  int queryAutoFetchHash();
  
  int queryPlanHash(BeanQueryRequest<?> paramBeanQueryRequest);
  
  int queryBindHash();
  
  void addSql(SpiExpressionRequest paramSpiExpressionRequest);
  
  void addBindValues(SpiExpressionRequest paramSpiExpressionRequest);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\SpiExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */