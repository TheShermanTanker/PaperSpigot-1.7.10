package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public interface BeanVisitor {
  void visitBegin();
  
  boolean visitBean(BeanDescriptor<?> paramBeanDescriptor);
  
  PropertyVisitor visitProperty(BeanProperty paramBeanProperty);
  
  void visitBeanEnd(BeanDescriptor<?> paramBeanDescriptor);
  
  void visitEnd();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ddl\BeanVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */