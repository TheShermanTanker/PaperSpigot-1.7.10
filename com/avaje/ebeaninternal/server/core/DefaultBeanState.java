/*    */ package com.avaje.ebeaninternal.server.core;
/*    */ 
/*    */ import com.avaje.ebean.BeanState;
/*    */ import com.avaje.ebean.bean.EntityBean;
/*    */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*    */ import java.beans.PropertyChangeListener;
/*    */ import java.util.Collections;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultBeanState
/*    */   implements BeanState
/*    */ {
/*    */   final EntityBean entityBean;
/*    */   final EntityBeanIntercept intercept;
/*    */   
/*    */   public DefaultBeanState(EntityBean entityBean) {
/* 21 */     this.entityBean = entityBean;
/* 22 */     this.intercept = entityBean._ebean_getIntercept();
/*    */   }
/*    */   
/*    */   public boolean isReference() {
/* 26 */     return this.intercept.isReference();
/*    */   }
/*    */   
/*    */   public boolean isNew() {
/* 30 */     return this.intercept.isNew();
/*    */   }
/*    */   
/*    */   public boolean isNewOrDirty() {
/* 34 */     return this.intercept.isNewOrDirty();
/*    */   }
/*    */   
/*    */   public boolean isDirty() {
/* 38 */     return this.intercept.isDirty();
/*    */   }
/*    */   
/*    */   public Set<String> getLoadedProps() {
/* 42 */     Set<String> props = this.intercept.getLoadedProps();
/* 43 */     return (props == null) ? null : Collections.<String>unmodifiableSet(props);
/*    */   }
/*    */   
/*    */   public Set<String> getChangedProps() {
/* 47 */     Set<String> props = this.intercept.getChangedProps();
/* 48 */     return (props == null) ? null : Collections.<String>unmodifiableSet(props);
/*    */   }
/*    */   
/*    */   public boolean isReadOnly() {
/* 52 */     return this.intercept.isReadOnly();
/*    */   }
/*    */   
/*    */   public void setReadOnly(boolean readOnly) {
/* 56 */     this.intercept.setReadOnly(readOnly);
/*    */   }
/*    */   
/*    */   public void addPropertyChangeListener(PropertyChangeListener listener) {
/* 60 */     this.entityBean.addPropertyChangeListener(listener);
/*    */   }
/*    */   
/*    */   public void removePropertyChangeListener(PropertyChangeListener listener) {
/* 64 */     this.entityBean.removePropertyChangeListener(listener);
/*    */   }
/*    */   
/*    */   public void setLoaded(Set<String> loadedProperties) {
/* 68 */     this.intercept.setLoadedProps(loadedProperties);
/* 69 */     this.intercept.setLoaded();
/*    */   }
/*    */   
/*    */   public void setReference() {
/* 73 */     this.intercept.setReference();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\DefaultBeanState.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */