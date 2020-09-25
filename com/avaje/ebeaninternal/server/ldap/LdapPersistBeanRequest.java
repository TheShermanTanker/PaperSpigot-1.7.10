/*    */ package com.avaje.ebeaninternal.server.ldap;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*    */ import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
/*    */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanManager;
/*    */ import java.util.Set;
/*    */ import javax.naming.ldap.LdapName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LdapPersistBeanRequest<T>
/*    */   extends PersistRequestBean<T>
/*    */ {
/*    */   private final DefaultLdapPersister persister;
/*    */   
/*    */   public LdapPersistBeanRequest(SpiEbeanServer server, T bean, Object parentBean, BeanManager<T> mgr, DefaultLdapPersister persister) {
/* 37 */     super(server, bean, parentBean, mgr, null, null);
/* 38 */     this.persister = persister;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public LdapPersistBeanRequest(SpiEbeanServer server, T bean, Object parentBean, BeanManager<T> mgr, DefaultLdapPersister persister, Set<String> updateProps, ConcurrencyMode concurrencyMode) {
/* 44 */     super(server, bean, parentBean, mgr, null, null, updateProps, concurrencyMode);
/* 45 */     this.persister = persister;
/*    */   }
/*    */   
/*    */   public LdapName createLdapName() {
/* 49 */     return this.beanDescriptor.createLdapName(this.bean);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int executeNow() {
/* 55 */     return this.persister.persist(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int executeOrQueue() {
/* 60 */     return executeNow();
/*    */   }
/*    */   
/*    */   public void initTransIfRequired() {}
/*    */   
/*    */   public void commitTransIfRequired() {}
/*    */   
/*    */   public void rollbackTransIfRequired() {}
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ldap\LdapPersistBeanRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */