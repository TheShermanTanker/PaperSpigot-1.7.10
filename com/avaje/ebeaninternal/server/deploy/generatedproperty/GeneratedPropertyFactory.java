/*    */ package com.avaje.ebeaninternal.server.deploy.generatedproperty;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*    */ import java.math.BigDecimal;
/*    */ import java.util.HashSet;
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
/*    */ public class GeneratedPropertyFactory
/*    */ {
/*    */   CounterFactory counterFactory;
/*    */   InsertTimestampFactory insertFactory;
/*    */   UpdateTimestampFactory updateFactory;
/* 38 */   HashSet<String> numberTypes = new HashSet<String>();
/*    */   
/*    */   public GeneratedPropertyFactory() {
/* 41 */     this.counterFactory = new CounterFactory();
/* 42 */     this.insertFactory = new InsertTimestampFactory();
/* 43 */     this.updateFactory = new UpdateTimestampFactory();
/*    */ 
/*    */     
/* 46 */     this.numberTypes.add(Integer.class.getName());
/* 47 */     this.numberTypes.add(int.class.getName());
/* 48 */     this.numberTypes.add(Long.class.getName());
/* 49 */     this.numberTypes.add(long.class.getName());
/* 50 */     this.numberTypes.add(Short.class.getName());
/* 51 */     this.numberTypes.add(short.class.getName());
/* 52 */     this.numberTypes.add(Double.class.getName());
/* 53 */     this.numberTypes.add(double.class.getName());
/* 54 */     this.numberTypes.add(BigDecimal.class.getName());
/*    */   }
/*    */   
/*    */   private boolean isNumberType(String typeClassName) {
/* 58 */     return this.numberTypes.contains(typeClassName);
/*    */   }
/*    */   
/*    */   public void setVersion(DeployBeanProperty property) {
/* 62 */     if (isNumberType(property.getPropertyType().getName())) {
/* 63 */       setCounter(property);
/*    */     } else {
/* 65 */       setUpdateTimestamp(property);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCounter(DeployBeanProperty property) {
/* 71 */     this.counterFactory.setCounter(property);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInsertTimestamp(DeployBeanProperty property) {
/* 76 */     this.insertFactory.setInsertTimestamp(property);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setUpdateTimestamp(DeployBeanProperty property) {
/* 81 */     this.updateFactory.setUpdateTimestamp(property);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\generatedproperty\GeneratedPropertyFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */