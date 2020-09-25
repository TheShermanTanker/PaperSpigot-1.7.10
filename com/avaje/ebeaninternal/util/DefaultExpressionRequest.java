/*    */ package com.avaje.ebeaninternal.util;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.DeployParser;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultExpressionRequest
/*    */   implements SpiExpressionRequest
/*    */ {
/*    */   private final SpiOrmQueryRequest<?> queryRequest;
/*    */   private final BeanDescriptor<?> beanDescriptor;
/* 16 */   private final StringBuilder sb = new StringBuilder();
/*    */   
/* 18 */   private final ArrayList<Object> bindValues = new ArrayList();
/*    */   
/*    */   private final DeployParser deployParser;
/*    */   
/*    */   private int paramIndex;
/*    */   
/*    */   public DefaultExpressionRequest(SpiOrmQueryRequest<?> queryRequest, DeployParser deployParser) {
/* 25 */     this.queryRequest = queryRequest;
/* 26 */     this.beanDescriptor = queryRequest.getBeanDescriptor();
/* 27 */     this.deployParser = deployParser;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DefaultExpressionRequest(BeanDescriptor<?> beanDescriptor) {
/* 39 */     this.beanDescriptor = beanDescriptor;
/* 40 */     this.queryRequest = null;
/* 41 */     this.deployParser = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String parseDeploy(String logicalProp) {
/* 46 */     String s = this.deployParser.getDeployWord(logicalProp);
/* 47 */     return (s == null) ? logicalProp : s;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int nextParameter() {
/* 54 */     return ++this.paramIndex;
/*    */   }
/*    */   
/*    */   public BeanDescriptor<?> getBeanDescriptor() {
/* 58 */     return this.beanDescriptor;
/*    */   }
/*    */   
/*    */   public SpiOrmQueryRequest<?> getQueryRequest() {
/* 62 */     return this.queryRequest;
/*    */   }
/*    */   
/*    */   public SpiExpressionRequest append(String sql) {
/* 66 */     this.sb.append(sql);
/* 67 */     return this;
/*    */   }
/*    */   
/*    */   public void addBindValue(Object bindValue) {
/* 71 */     this.bindValues.add(bindValue);
/*    */   }
/*    */   
/*    */   public boolean includeProperty(String propertyName) {
/* 75 */     return true;
/*    */   }
/*    */   
/*    */   public String getSql() {
/* 79 */     return this.sb.toString();
/*    */   }
/*    */   
/*    */   public ArrayList<Object> getBindValues() {
/* 83 */     return this.bindValues;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninterna\\util\DefaultExpressionRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */