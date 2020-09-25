/*    */ package com.avaje.ebeaninternal.server.ldap;
/*    */ 
/*    */ import com.avaje.ebean.EbeanServer;
/*    */ import com.avaje.ebean.ExpressionFactory;
/*    */ import com.avaje.ebeaninternal.server.querydefn.DefaultOrmQuery;
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
/*    */ public class DefaultLdapOrmQuery<T>
/*    */   extends DefaultOrmQuery<T>
/*    */ {
/*    */   private static final long serialVersionUID = -4344629258591773124L;
/*    */   
/*    */   public DefaultLdapOrmQuery(Class<T> beanType, EbeanServer server, ExpressionFactory expressionFactory, String query) {
/* 31 */     super(beanType, server, expressionFactory, query);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ldap\DefaultLdapOrmQuery.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */