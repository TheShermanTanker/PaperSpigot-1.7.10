/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebean.config.ScalarTypeConverter;
/*    */ import scala.None$;
/*    */ import scala.Option;
/*    */ import scala.Some;
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
/*    */ public class ScalaOptionTypeConverter<S>
/*    */   implements ScalarTypeConverter<Option<S>, S>
/*    */ {
/*    */   public Option<S> getNullValue() {
/* 37 */     return (Option<S>)None$.MODULE$;
/*    */   }
/*    */ 
/*    */   
/*    */   public S unwrapValue(Option<S> beanType) {
/* 42 */     if (beanType.isEmpty()) {
/* 43 */       return null;
/*    */     }
/* 45 */     return (S)beanType.get();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Option<S> wrapValue(S scalarType) {
/* 51 */     if (scalarType == null) {
/* 52 */       return (Option<S>)None$.MODULE$;
/*    */     }
/* 54 */     if (scalarType instanceof Some) {
/* 55 */       return (Option<S>)scalarType;
/*    */     }
/* 57 */     return (Option<S>)new Some(scalarType);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalaOptionTypeConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */