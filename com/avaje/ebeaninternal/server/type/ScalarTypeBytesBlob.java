/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import java.sql.SQLException;
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
/*    */ public class ScalarTypeBytesBlob
/*    */   extends ScalarTypeBytesBase
/*    */ {
/*    */   public ScalarTypeBytesBlob() {
/* 31 */     super(true, 2004);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] read(DataReader dataReader) throws SQLException {
/* 36 */     return dataReader.getBlobBytes();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeBytesBlob.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */