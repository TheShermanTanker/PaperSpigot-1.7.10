package com.avaje.ebeaninternal.server.el;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public interface ElPropertyDeploy {
  public static final String ROOT_ELPREFIX = "${}";
  
  boolean containsMany();
  
  boolean containsManySince(String paramString);
  
  String getElPrefix();
  
  String getElPlaceholder(boolean paramBoolean);
  
  String getName();
  
  String getElName();
  
  String getDbColumn();
  
  BeanProperty getBeanProperty();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\el\ElPropertyDeploy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */