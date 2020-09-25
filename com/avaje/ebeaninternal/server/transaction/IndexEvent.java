/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.cluster.BinaryMessage;
/*    */ import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
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
/*    */ public class IndexEvent
/*    */ {
/*    */   public static final int COMMIT_EVENT = 1;
/*    */   public static final int OPTIMISE_EVENT = 2;
/*    */   private final int eventType;
/*    */   private final String indexName;
/*    */   
/*    */   public IndexEvent(int eventType, String indexName) {
/* 38 */     this.eventType = eventType;
/* 39 */     this.indexName = indexName;
/*    */   }
/*    */   
/*    */   public int getEventType() {
/* 43 */     return this.eventType;
/*    */   }
/*    */   
/*    */   public String getIndexName() {
/* 47 */     return this.indexName;
/*    */   }
/*    */ 
/*    */   
/*    */   public static IndexEvent readBinaryMessage(DataInput dataInput) throws IOException {
/* 52 */     int eventType = dataInput.readInt();
/* 53 */     String indexName = dataInput.readUTF();
/* 54 */     return new IndexEvent(eventType, indexName);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeBinaryMessage(BinaryMessageList msgList) throws IOException {
/* 59 */     BinaryMessage msg = new BinaryMessage(this.indexName.length() + 10);
/* 60 */     DataOutputStream os = msg.getOs();
/* 61 */     os.writeInt(7);
/* 62 */     os.writeInt(this.eventType);
/* 63 */     os.writeUTF(this.indexName);
/*    */     
/* 65 */     msgList.add(msg);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\IndexEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */