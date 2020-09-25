/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*    */ import com.avaje.ebeaninternal.server.text.json.WriteJsonBuffer;
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
/*    */ public abstract class ScalarTypeBase<T>
/*    */   implements ScalarType<T>
/*    */ {
/*    */   protected final Class<T> type;
/*    */   protected final boolean jdbcNative;
/*    */   protected final int jdbcType;
/*    */   
/*    */   public ScalarTypeBase(Class<T> type, boolean jdbcNative, int jdbcType) {
/* 37 */     this.type = type;
/* 38 */     this.jdbcNative = jdbcNative;
/* 39 */     this.jdbcType = jdbcType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLength() {
/* 46 */     return 0;
/*    */   }
/*    */   
/*    */   public boolean isJdbcNative() {
/* 50 */     return this.jdbcNative;
/*    */   }
/*    */   
/*    */   public int getJdbcType() {
/* 54 */     return this.jdbcType;
/*    */   }
/*    */   
/*    */   public Class<T> getType() {
/* 58 */     return this.type;
/*    */   }
/*    */ 
/*    */   
/*    */   public String format(Object v) {
/* 63 */     return formatValue((T)v);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isDbNull(Object value) {
/* 70 */     return (value == null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getDbNullValue(Object value) {
/* 77 */     return value;
/*    */   }
/*    */   
/*    */   public void loadIgnore(DataReader dataReader) {
/* 81 */     dataReader.incrementPos(1);
/*    */   }
/*    */   
/*    */   public void accumulateScalarTypes(String propName, CtCompoundTypeScalarList list) {
/* 85 */     list.addScalarType(propName, this);
/*    */   }
/*    */   
/*    */   public void jsonWrite(WriteJsonBuffer buffer, T value, JsonValueAdapter ctx) {
/* 89 */     String v = jsonToString(value, ctx);
/* 90 */     buffer.append(v);
/*    */   }
/*    */   
/*    */   public String jsonToString(T value, JsonValueAdapter ctx) {
/* 94 */     return formatValue(value);
/*    */   }
/*    */   
/*    */   public T jsonFromString(String value, JsonValueAdapter ctx) {
/* 98 */     return parse(value);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */