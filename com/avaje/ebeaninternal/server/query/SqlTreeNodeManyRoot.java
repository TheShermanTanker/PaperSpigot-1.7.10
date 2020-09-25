/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*    */ import com.avaje.ebeaninternal.server.deploy.DbReadContext;
/*    */ import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
/*    */ import java.sql.SQLException;
/*    */ import java.util.List;
/*    */ 
/*    */ public final class SqlTreeNodeManyRoot
/*    */   extends SqlTreeNodeBean {
/*    */   public SqlTreeNodeManyRoot(String prefix, BeanPropertyAssocMany<?> prop, SqlTreeProperties props, List<SqlTreeNode> myList) {
/* 13 */     super(prefix, (BeanPropertyAssoc<?>)prop, prop.getTargetDescriptor(), props, myList, true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void postLoad(DbReadContext cquery, Object loadedBean, Object id) {
/* 21 */     cquery.setLoadedManyBean(loadedBean);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void load(DbReadContext cquery, Object parentBean) throws SQLException {
/* 29 */     super.load(cquery, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void appendFrom(DbSqlContext ctx, boolean forceOuterJoin) {
/* 37 */     super.appendFrom(ctx, true);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\SqlTreeNodeManyRoot.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */