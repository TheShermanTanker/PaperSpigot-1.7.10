/*    */ package com.avaje.ebeaninternal.server.ldap;
/*    */ 
/*    */ import com.avaje.ebean.config.ldap.LdapContextFactory;
/*    */ import java.util.List;
/*    */ import javax.naming.directory.DirContext;
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
/*    */ 
/*    */ public class LdapOrmQueryEngine
/*    */ {
/*    */   private final boolean defaultVanillaMode;
/*    */   private final LdapContextFactory contextFactory;
/*    */   
/*    */   public LdapOrmQueryEngine(boolean defaultVanillaMode, LdapContextFactory contextFactory) {
/* 35 */     this.defaultVanillaMode = defaultVanillaMode;
/* 36 */     this.contextFactory = contextFactory;
/*    */   }
/*    */   
/*    */   public <T> T findId(LdapOrmQueryRequest<T> request) {
/* 40 */     DirContext dc = this.contextFactory.createContext();
/* 41 */     LdapOrmQueryExecute<T> exe = new LdapOrmQueryExecute<T>(request, this.defaultVanillaMode, dc);
/*    */     
/* 43 */     return exe.findId();
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> List<T> findList(LdapOrmQueryRequest<T> request) {
/* 48 */     DirContext dc = this.contextFactory.createContext();
/*    */     
/* 50 */     LdapOrmQueryExecute<T> exe = new LdapOrmQueryExecute<T>(request, this.defaultVanillaMode, dc);
/*    */     
/* 52 */     return exe.findList();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ldap\LdapOrmQueryEngine.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */