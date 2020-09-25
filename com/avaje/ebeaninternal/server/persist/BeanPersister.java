package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import javax.persistence.PersistenceException;

public interface BeanPersister {
  void insert(PersistRequestBean<?> paramPersistRequestBean) throws PersistenceException;
  
  void update(PersistRequestBean<?> paramPersistRequestBean) throws PersistenceException;
  
  void delete(PersistRequestBean<?> paramPersistRequestBean) throws PersistenceException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\BeanPersister.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */