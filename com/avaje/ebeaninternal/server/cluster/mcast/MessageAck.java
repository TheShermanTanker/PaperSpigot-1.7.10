/*    */ package com.avaje.ebeaninternal.server.cluster.mcast;
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
/*    */ public class MessageAck
/*    */   implements Message
/*    */ {
/*    */   private final String toHostPort;
/*    */   private final long gotAllPacketId;
/*    */   
/*    */   public MessageAck(String toHostPort, long gotAllPacketId) {
/* 36 */     this.toHostPort = toHostPort;
/* 37 */     this.gotAllPacketId = gotAllPacketId;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 41 */     return "Ack " + this.toHostPort + " " + this.gotAllPacketId;
/*    */   }
/*    */   
/*    */   public boolean isControlMessage() {
/* 45 */     return false;
/*    */   }
/*    */   
/*    */   public String getToHostPort() {
/* 49 */     return this.toHostPort;
/*    */   }
/*    */   
/*    */   public long getGotAllPacketId() {
/* 53 */     return this.gotAllPacketId;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static MessageAck readBinaryMessage(DataInput dataInput) throws IOException {
/* 59 */     String hostPort = dataInput.readUTF();
/* 60 */     long gotAllPacketId = dataInput.readLong();
/* 61 */     return new MessageAck(hostPort, gotAllPacketId);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeBinaryMessage(BinaryMessageList msgList) throws IOException {
/* 66 */     BinaryMessage m = new BinaryMessage(this.toHostPort.length() * 2 + 20);
/*    */     
/* 68 */     DataOutputStream os = m.getOs();
/* 69 */     os.writeInt(8);
/* 70 */     os.writeUTF(this.toHostPort);
/* 71 */     os.writeLong(this.gotAllPacketId);
/* 72 */     os.flush();
/*    */     
/* 74 */     msgList.add(m);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cluster\mcast\MessageAck.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */