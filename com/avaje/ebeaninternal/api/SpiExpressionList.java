package com.avaje.ebeaninternal.api;

import com.avaje.ebean.ExpressionFactory;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.ArrayList;
import java.util.List;

public interface SpiExpressionList<T> extends ExpressionList<T> {
  List<SpiExpression> getUnderlyingList();
  
  void trimPath(int paramInt);
  
  void setExpressionFactory(ExpressionFactory paramExpressionFactory);
  
  void containsMany(BeanDescriptor<?> paramBeanDescriptor, ManyWhereJoins paramManyWhereJoins);
  
  boolean isEmpty();
  
  String buildSql(SpiExpressionRequest paramSpiExpressionRequest);
  
  ArrayList<Object> buildBindValues(SpiExpressionRequest paramSpiExpressionRequest);
  
  int queryPlanHash(BeanQueryRequest<?> paramBeanQueryRequest);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\SpiExpressionList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */