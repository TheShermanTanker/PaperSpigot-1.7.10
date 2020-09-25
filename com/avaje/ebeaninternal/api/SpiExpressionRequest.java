package com.avaje.ebeaninternal.api;

import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.ArrayList;

public interface SpiExpressionRequest {
  String parseDeploy(String paramString);
  
  BeanDescriptor<?> getBeanDescriptor();
  
  SpiOrmQueryRequest<?> getQueryRequest();
  
  SpiExpressionRequest append(String paramString);
  
  void addBindValue(Object paramObject);
  
  String getSql();
  
  ArrayList<Object> getBindValues();
  
  int nextParameter();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\SpiExpressionRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */