/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.lib.util.StringHelper;
/*    */ import javax.persistence.PersistenceException;
/*    */ 
/*    */ public class BeanSqlSelect
/*    */ {
/*    */   public static final String HAVING_PREDICATES = "${HAVING_PREDICATES}";
/*    */   public static final String WHERE_PREDICATES = "${WHERE_PREDICATES}";
/*    */   public static final String AND_PREDICATES = "${AND_PREDICATES}";
/*    */   public static final String ORDER_BY = "${ORDER_BY}";
/*    */   final String sql;
/*    */   final PredicatesType predicatesType;
/*    */   final boolean hasOrderBy;
/*    */   
/*    */   public enum PredicatesType
/*    */   {
/* 18 */     HAVING, WHERE, AND, NONE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BeanSqlSelect(String sql, PredicatesType predicatesType, boolean hasOrderBy) {
/* 28 */     this.sql = sql;
/* 29 */     this.predicatesType = predicatesType;
/* 30 */     this.hasOrderBy = hasOrderBy;
/*    */   }
/*    */   
/*    */   public String getSql() {
/* 34 */     return this.sql;
/*    */   }
/*    */   
/*    */   public PredicatesType getPredicatesType() {
/* 38 */     return this.predicatesType;
/*    */   }
/*    */   
/*    */   public boolean hasOrderBy() {
/* 42 */     return this.hasOrderBy;
/*    */   }
/*    */   
/*    */   public String addPredicates(String query, String predicates) {
/* 46 */     if (predicates == null) {
/* 47 */       switch (this.predicatesType) {
/*    */         case HAVING:
/* 49 */           return StringHelper.replaceString(query, "${HAVING_PREDICATES}", "");
/*    */         case WHERE:
/* 51 */           return StringHelper.replaceString(query, "${WHERE_PREDICATES}", "");
/*    */         case AND:
/* 53 */           return StringHelper.replaceString(query, "${AND_PREDICATES}", "");
/*    */         case NONE:
/* 55 */           return query;
/*    */       } 
/*    */       
/* 58 */       throw new PersistenceException("predicatesType " + this.predicatesType + " not handled");
/*    */     } 
/*    */     
/* 61 */     switch (this.predicatesType) {
/*    */       case HAVING:
/* 63 */         return StringHelper.replaceString(query, "${HAVING_PREDICATES}", " HAVING " + predicates);
/*    */       
/*    */       case WHERE:
/* 66 */         return StringHelper.replaceString(query, "${WHERE_PREDICATES}", " WHERE " + predicates);
/*    */       case AND:
/* 68 */         return StringHelper.replaceString(query, "${AND_PREDICATES}", " AND " + predicates);
/*    */       case NONE:
/* 70 */         return query;
/*    */     } 
/*    */     
/* 73 */     throw new PersistenceException("predicatesType " + this.predicatesType + " not handled");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String addOrderBy(String query, String orderBy) {
/* 79 */     if (!this.hasOrderBy) {
/* 80 */       return query;
/*    */     }
/* 82 */     if (orderBy == null) {
/* 83 */       return StringHelper.replaceString(query, "${ORDER_BY}", "");
/*    */     }
/* 85 */     return StringHelper.replaceString(query, "${ORDER_BY}", " ORDER BY " + orderBy);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanSqlSelect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */