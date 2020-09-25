/*    */ package com.avaje.ebeaninternal.server.deploy.parse;
/*    */ 
/*    */ import com.avaje.ebean.annotation.Sql;
/*    */ import com.avaje.ebean.annotation.SqlSelect;
/*    */ import com.avaje.ebeaninternal.server.deploy.DRawSqlMeta;
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
/*    */ public class AnnotationSql
/*    */   extends AnnotationParser
/*    */ {
/*    */   public AnnotationSql(DeployBeanInfo<?> info) {
/* 32 */     super(info);
/*    */   }
/*    */   
/*    */   public void parse() {
/* 36 */     Class<?> cls = this.descriptor.getBeanType();
/* 37 */     Sql sql = cls.<Sql>getAnnotation(Sql.class);
/* 38 */     if (sql != null) {
/* 39 */       setSql(sql);
/*    */     }
/*    */ 
/*    */     
/* 43 */     SqlSelect sqlSelect = cls.<SqlSelect>getAnnotation(SqlSelect.class);
/* 44 */     if (sqlSelect != null) {
/* 45 */       setSqlSelect(sqlSelect);
/*    */     }
/*    */   }
/*    */   
/*    */   private void setSql(Sql sql) {
/* 50 */     SqlSelect[] select = sql.select();
/* 51 */     for (int i = 0; i < select.length; i++) {
/* 52 */       setSqlSelect(select[i]);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   private void setSqlSelect(SqlSelect sqlSelect) {
/* 58 */     DRawSqlMeta rawSqlMeta = new DRawSqlMeta(sqlSelect);
/* 59 */     this.descriptor.add(rawSqlMeta);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\parse\AnnotationSql.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */