/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebean.config.ScalarTypeConverter;
/*    */ import java.sql.Timestamp;
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
/*    */ public class LongToTimestampConverter
/*    */   implements ScalarTypeConverter<Long, Timestamp>
/*    */ {
/*    */   public Long getNullValue() {
/* 29 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Timestamp unwrapValue(Long beanType) {
/* 34 */     return new Timestamp(beanType.longValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public Long wrapValue(Timestamp scalarType) {
/* 39 */     return Long.valueOf(scalarType.getTime());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\LongToTimestampConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */