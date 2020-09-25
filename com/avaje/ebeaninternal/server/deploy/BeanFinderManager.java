package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.event.BeanFinder;
import java.util.List;

public interface BeanFinderManager {
  int getRegisterCount();
  
  int createBeanFinders(List<Class<?>> paramList);
  
  <T> BeanFinder<T> getBeanFinder(Class<T> paramClass);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanFinderManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */