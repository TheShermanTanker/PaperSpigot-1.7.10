/*    */ package com.avaje.ebeaninternal.server.deploy.generatedproperty;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*    */ import java.sql.Timestamp;
/*    */ import java.util.Date;
/*    */ import javax.persistence.PersistenceException;
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
/*    */ public class InsertTimestampFactory
/*    */ {
/* 33 */   final GeneratedInsertTimestamp timestamp = new GeneratedInsertTimestamp();
/*    */   
/* 35 */   final GeneratedInsertDate utilDate = new GeneratedInsertDate();
/*    */   
/* 37 */   final GeneratedInsertLong longTime = new GeneratedInsertLong();
/*    */ 
/*    */   
/*    */   public void setInsertTimestamp(DeployBeanProperty property) {
/* 41 */     property.setGeneratedProperty(createInsertTimestamp(property));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GeneratedProperty createInsertTimestamp(DeployBeanProperty property) {
/* 49 */     Class<?> propType = property.getPropertyType();
/* 50 */     if (propType.equals(Timestamp.class)) {
/* 51 */       return this.timestamp;
/*    */     }
/* 53 */     if (propType.equals(Date.class)) {
/* 54 */       return this.utilDate;
/*    */     }
/* 56 */     if (propType.equals(Long.class) || propType.equals(long.class)) {
/* 57 */       return this.longTime;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 62 */     String msg = "Generated Insert Timestamp not supported on " + propType.getName();
/* 63 */     throw new PersistenceException(msg);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\generatedproperty\InsertTimestampFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */