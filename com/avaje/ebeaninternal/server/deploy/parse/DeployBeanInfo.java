/*    */ package com.avaje.ebeaninternal.server.deploy.parse;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoin;
/*    */ import java.util.HashMap;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DeployBeanInfo<T>
/*    */ {
/* 37 */   private final HashMap<String, DeployTableJoin> tableJoinMap = new HashMap<String, DeployTableJoin>();
/*    */ 
/*    */   
/*    */   private final DeployUtil util;
/*    */ 
/*    */   
/*    */   private final DeployBeanDescriptor<T> descriptor;
/*    */ 
/*    */   
/*    */   public DeployBeanInfo(DeployUtil util, DeployBeanDescriptor<T> descriptor) {
/* 47 */     this.util = util;
/* 48 */     this.descriptor = descriptor;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 52 */     return "" + this.descriptor;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DeployBeanDescriptor<T> getDescriptor() {
/* 59 */     return this.descriptor;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DeployUtil getUtil() {
/* 66 */     return this.util;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DeployTableJoin getTableJoin(String tableName) {
/* 74 */     String key = tableName.toLowerCase();
/*    */     
/* 76 */     DeployTableJoin tableJoin = this.tableJoinMap.get(key);
/* 77 */     if (tableJoin == null) {
/* 78 */       tableJoin = new DeployTableJoin();
/* 79 */       tableJoin.setTable(tableName);
/* 80 */       tableJoin.setType("join");
/* 81 */       this.descriptor.addTableJoin(tableJoin);
/*    */       
/* 83 */       this.tableJoinMap.put(key, tableJoin);
/*    */     } 
/* 85 */     return tableJoin;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setBeanJoinType(DeployBeanPropertyAssocOne<?> beanProp, boolean outerJoin) {
/* 93 */     String joinType = "join";
/* 94 */     if (outerJoin) {
/* 95 */       joinType = "left outer join";
/*    */     }
/*    */     
/* 98 */     DeployTableJoin tableJoin = beanProp.getTableJoin();
/* 99 */     tableJoin.setType(joinType);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\parse\DeployBeanInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */