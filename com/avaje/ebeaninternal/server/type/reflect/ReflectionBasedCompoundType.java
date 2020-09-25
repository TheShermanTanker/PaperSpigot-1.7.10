/*    */ package com.avaje.ebeaninternal.server.type.reflect;
/*    */ 
/*    */ import com.avaje.ebean.config.CompoundType;
/*    */ import com.avaje.ebean.config.CompoundTypeProperty;
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.util.Arrays;
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
/*    */ public class ReflectionBasedCompoundType
/*    */   implements CompoundType
/*    */ {
/*    */   private final Constructor<?> constructor;
/*    */   private final ReflectionBasedCompoundTypeProperty[] props;
/*    */   
/*    */   public ReflectionBasedCompoundType(Constructor<?> constructor, ReflectionBasedCompoundTypeProperty[] props) {
/* 36 */     this.constructor = constructor;
/* 37 */     this.props = props;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 41 */     return "ReflectionBasedCompoundType " + this.constructor + " " + Arrays.toString((Object[])this.props);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object create(Object[] propertyValues) {
/*    */     try {
/* 47 */       return this.constructor.newInstance(propertyValues);
/* 48 */     } catch (Exception e) {
/* 49 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public CompoundTypeProperty[] getProperties() {
/* 54 */     return (CompoundTypeProperty[])this.props;
/*    */   }
/*    */   
/*    */   public Class<?> getPropertyType(int i) {
/* 58 */     return this.props[i].getPropertyType();
/*    */   }
/*    */   
/*    */   public Class<?> getCompoundType() {
/* 62 */     return this.constructor.getDeclaringClass();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\reflect\ReflectionBasedCompoundType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */