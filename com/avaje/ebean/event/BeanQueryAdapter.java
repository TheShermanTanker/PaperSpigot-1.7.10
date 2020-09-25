package com.avaje.ebean.event;

public interface BeanQueryAdapter {
  boolean isRegisterFor(Class<?> paramClass);
  
  int getExecutionOrder();
  
  void preQuery(BeanQueryRequest<?> paramBeanQueryRequest);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\event\BeanQueryAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */