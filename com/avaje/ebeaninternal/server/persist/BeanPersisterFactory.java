package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;

public interface BeanPersisterFactory {
  BeanPersister create(BeanDescriptor<?> paramBeanDescriptor);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\BeanPersisterFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */