/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ public class ScalarTypeClass
/*    */   extends ScalarTypeBaseVarchar<Class>
/*    */ {
/*    */   public ScalarTypeClass() {
/* 34 */     super(Class.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLength() {
/* 39 */     return 255;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<?> convertFromDbString(String dbValue) {
/* 44 */     return parse(dbValue);
/*    */   }
/*    */ 
/*    */   
/*    */   public String convertToDbString(Class beanValue) {
/* 49 */     return beanValue.getCanonicalName();
/*    */   }
/*    */   
/*    */   public String formatValue(Class v) {
/* 53 */     return v.getCanonicalName();
/*    */   }
/*    */   
/*    */   public Class<?> parse(String value) {
/*    */     try {
/* 58 */       return Class.forName(value);
/* 59 */     } catch (Exception e) {
/* 60 */       String msg = "Unable to find Class " + value;
/* 61 */       throw new PersistenceException(msg, e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeClass.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */