package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;

public interface PropertyVisitor {
  void visitMany(BeanPropertyAssocMany<?> paramBeanPropertyAssocMany);
  
  void visitOneImported(BeanPropertyAssocOne<?> paramBeanPropertyAssocOne);
  
  void visitOneExported(BeanPropertyAssocOne<?> paramBeanPropertyAssocOne);
  
  void visitEmbedded(BeanPropertyAssocOne<?> paramBeanPropertyAssocOne);
  
  void visitEmbeddedScalar(BeanProperty paramBeanProperty, BeanPropertyAssocOne<?> paramBeanPropertyAssocOne);
  
  void visitScalar(BeanProperty paramBeanProperty);
  
  void visitCompound(BeanPropertyCompound paramBeanPropertyCompound);
  
  void visitCompoundScalar(BeanPropertyCompound paramBeanPropertyCompound, BeanProperty paramBeanProperty);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ddl\PropertyVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */