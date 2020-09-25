/*    */ package com.avaje.ebeaninternal.server.type.reflect;
/*    */ 
/*    */ import com.avaje.ebean.config.CompoundTypeProperty;
/*    */ import java.lang.reflect.Method;
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
/*    */ public class ReflectionBasedCompoundTypeProperty
/*    */   implements CompoundTypeProperty
/*    */ {
/* 29 */   private static final Object[] NO_ARGS = new Object[0];
/*    */   
/*    */   private final Method reader;
/*    */   
/*    */   private final String name;
/*    */   
/*    */   private final Class<?> propertyType;
/*    */   
/*    */   public ReflectionBasedCompoundTypeProperty(String name, Method reader, Class<?> propertyType) {
/* 38 */     this.name = name;
/* 39 */     this.reader = reader;
/* 40 */     this.propertyType = propertyType;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 44 */     return this.name;
/*    */   }
/*    */   
/*    */   public int getDbType() {
/* 48 */     return 0;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 52 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getValue(Object valueObject) {
/*    */     try {
/* 58 */       return this.reader.invoke(valueObject, NO_ARGS);
/* 59 */     } catch (Exception e) {
/* 60 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Class<?> getPropertyType() {
/* 65 */     return this.propertyType;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\reflect\ReflectionBasedCompoundTypeProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */