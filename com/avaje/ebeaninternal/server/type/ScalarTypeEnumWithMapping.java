/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ import java.util.Iterator;
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
/*    */ public class ScalarTypeEnumWithMapping
/*    */   extends ScalarTypeEnumStandard.EnumBase
/*    */   implements ScalarType, ScalarTypeEnum
/*    */ {
/*    */   private final EnumToDbValueMap beanDbMap;
/*    */   private final int length;
/*    */   
/*    */   public ScalarTypeEnumWithMapping(EnumToDbValueMap<?> beanDbMap, Class<?> enumType, int length) {
/* 39 */     super(enumType, false, beanDbMap.getDbType());
/* 40 */     this.beanDbMap = beanDbMap;
/* 41 */     this.length = length;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getContraintInValues() {
/* 49 */     StringBuilder sb = new StringBuilder();
/*    */     
/* 51 */     int i = 0;
/*    */     
/* 53 */     sb.append("(");
/*    */     
/* 55 */     Iterator<?> it = this.beanDbMap.dbValues();
/* 56 */     while (it.hasNext()) {
/* 57 */       Object dbValue = it.next();
/* 58 */       if (i++ > 0) {
/* 59 */         sb.append(",");
/*    */       }
/* 61 */       if (!this.beanDbMap.isIntegerType()) {
/* 62 */         sb.append("'");
/*    */       }
/* 64 */       sb.append(dbValue.toString());
/* 65 */       if (!this.beanDbMap.isIntegerType()) {
/* 66 */         sb.append("'");
/*    */       }
/*    */     } 
/*    */     
/* 70 */     sb.append(")");
/*    */     
/* 72 */     return sb.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLength() {
/* 82 */     return this.length;
/*    */   }
/*    */   
/*    */   public void bind(DataBind b, Object value) throws SQLException {
/* 86 */     this.beanDbMap.bind(b, value);
/*    */   }
/*    */   
/*    */   public Object read(DataReader dataReader) throws SQLException {
/* 90 */     return this.beanDbMap.read(dataReader);
/*    */   }
/*    */   
/*    */   public Object toBeanType(Object dbValue) {
/* 94 */     return this.beanDbMap.getBeanValue(dbValue);
/*    */   }
/*    */   
/*    */   public Object toJdbcType(Object beanValue) {
/* 98 */     return this.beanDbMap.getDbValue(beanValue);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeEnumWithMapping.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */