/*    */ package com.avaje.ebeaninternal.server.deploy.generatedproperty;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
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
/*    */ public class GeneratedCounter
/*    */   implements GeneratedProperty
/*    */ {
/*    */   final int numberType;
/*    */   
/*    */   public GeneratedCounter(int numberType) {
/* 33 */     this.numberType = numberType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getInsertValue(BeanProperty prop, Object bean) {
/* 40 */     Integer i = Integer.valueOf(1);
/* 41 */     return BasicTypeConverter.convert(i, this.numberType);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getUpdateValue(BeanProperty prop, Object bean) {
/* 48 */     Number currVal = (Number)prop.getValue(bean);
/* 49 */     Integer nextVal = Integer.valueOf(currVal.intValue() + 1);
/* 50 */     return BasicTypeConverter.convert(nextVal, this.numberType);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean includeInUpdate() {
/* 57 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean includeInInsert() {
/* 64 */     return true;
/*    */   }
/*    */   
/*    */   public boolean isDDLNotNullable() {
/* 68 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\generatedproperty\GeneratedCounter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */