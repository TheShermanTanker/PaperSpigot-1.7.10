/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebean.config.ScalarTypeConverter;
/*    */ import scala.Double;
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
/*    */ public class ScalarTypeScalaDouble
/*    */   extends ScalarTypeWrapper<Object, Double>
/*    */ {
/*    */   public ScalarTypeScalaDouble() {
/* 29 */     super(Object.class, new ScalarTypeDouble(), new Converter());
/*    */   }
/*    */   
/*    */   static class Converter
/*    */     implements ScalarTypeConverter<Object, Double> {
/*    */     public Double getNullValue() {
/* 35 */       return null;
/*    */     }
/*    */     
/*    */     public Object wrapValue(Double scalarType) {
/* 39 */       return scalarType;
/*    */     }
/*    */     
/*    */     public Double unwrapValue(Object beanType) {
/* 43 */       return Double.valueOf(((Double)beanType).toDouble());
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeScalaDouble.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */