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
/*    */ public class UpdateTimestampFactory
/*    */ {
/* 33 */   final GeneratedUpdateTimestamp timestamp = new GeneratedUpdateTimestamp();
/*    */   
/* 35 */   final GeneratedUpdateDate utilDate = new GeneratedUpdateDate();
/*    */   
/* 37 */   final GeneratedUpdateLong longTime = new GeneratedUpdateLong();
/*    */ 
/*    */   
/*    */   public void setUpdateTimestamp(DeployBeanProperty property) {
/* 41 */     property.setGeneratedProperty(createUpdateTimestamp(property));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private GeneratedProperty createUpdateTimestamp(DeployBeanProperty property) {
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
/* 62 */     String msg = "Generated update Timestamp not supported on " + propType.getName();
/* 63 */     throw new PersistenceException(msg);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\generatedproperty\UpdateTimestampFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */