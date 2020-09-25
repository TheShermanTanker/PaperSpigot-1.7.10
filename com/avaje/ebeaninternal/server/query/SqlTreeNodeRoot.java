/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.DbReadContext;
/*    */ import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
/*    */ import com.avaje.ebeaninternal.server.deploy.TableJoin;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SqlTreeNodeRoot
/*    */   extends SqlTreeNodeBean
/*    */ {
/*    */   private final TableJoin includeJoin;
/*    */   
/*    */   public SqlTreeNodeRoot(BeanDescriptor<?> desc, SqlTreeProperties props, List<SqlTreeNode> myList, boolean withId, TableJoin includeJoin) {
/* 21 */     super(null, null, desc, props, myList, withId);
/* 22 */     this.includeJoin = includeJoin;
/*    */   }
/*    */   
/*    */   public SqlTreeNodeRoot(BeanDescriptor<?> desc, SqlTreeProperties props, List<SqlTreeNode> myList, boolean withId) {
/* 26 */     super(null, null, desc, props, myList, withId);
/* 27 */     this.includeJoin = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void postLoad(DbReadContext cquery, Object loadedBean, Object id) {
/* 34 */     cquery.setLoadedBean(loadedBean, id);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean appendFromBaseTable(DbSqlContext ctx, boolean forceOuterJoin) {
/* 43 */     ctx.append(this.desc.getBaseTable());
/* 44 */     ctx.append(" ").append(ctx.getTableAlias(null));
/*    */     
/* 46 */     if (this.includeJoin != null) {
/* 47 */       String a1 = ctx.getTableAlias(null);
/* 48 */       String a2 = "int_";
/* 49 */       this.includeJoin.addJoin(forceOuterJoin, a1, a2, ctx);
/*    */     } 
/*    */     
/* 52 */     return forceOuterJoin;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\SqlTreeNodeRoot.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */