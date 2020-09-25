/*    */ package com.avaje.ebeaninternal.server.cluster.mcast;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.cluster.BinaryMessage;
/*    */ import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class MessageResend
/*    */   implements Message
/*    */ {
/*    */   private final String toHostPort;
/*    */   private final List<Long> resendPacketIds;
/*    */   
/*    */   public MessageResend(String toHostPort, List<Long> resendPacketIds) {
/* 38 */     this.toHostPort = toHostPort;
/* 39 */     this.resendPacketIds = resendPacketIds;
/*    */   }
/*    */   
/*    */   public MessageResend(String toHostPort) {
/* 43 */     this(toHostPort, new ArrayList<Long>(4));
/*    */   }
/*    */   
/*    */   public String toString() {
/* 47 */     return "Resend " + this.toHostPort + " " + this.resendPacketIds;
/*    */   }
/*    */   
/*    */   public boolean isControlMessage() {
/* 51 */     return false;
/*    */   }
/*    */   
/*    */   public String getToHostPort() {
/* 55 */     return this.toHostPort;
/*    */   }
/*    */   
/*    */   public void add(long packetId) {
/* 59 */     this.resendPacketIds.add(Long.valueOf(packetId));
/*    */   }
/*    */   
/*    */   public List<Long> getResendPacketIds() {
/* 63 */     return this.resendPacketIds;
/*    */   }
/*    */ 
/*    */   
/*    */   public static MessageResend readBinaryMessage(DataInput dataInput) throws IOException {
/* 68 */     String hostPort = dataInput.readUTF();
/*    */     
/* 70 */     MessageResend msg = new MessageResend(hostPort);
/*    */     
/* 72 */     int numberOfPacketIds = dataInput.readInt();
/* 73 */     for (int i = 0; i < numberOfPacketIds; i++) {
/* 74 */       long packetId = dataInput.readLong();
/* 75 */       msg.add(packetId);
/*    */     } 
/*    */     
/* 78 */     return msg;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeBinaryMessage(BinaryMessageList msgList) throws IOException {
/* 83 */     BinaryMessage m = new BinaryMessage(this.toHostPort.length() * 2 + 20);
/*    */     
/* 85 */     DataOutputStream os = m.getOs();
/* 86 */     os.writeInt(9);
/* 87 */     os.writeUTF(this.toHostPort);
/* 88 */     os.writeInt(this.resendPacketIds.size());
/* 89 */     for (int i = 0; i < this.resendPacketIds.size(); i++) {
/* 90 */       Long packetId = this.resendPacketIds.get(i);
/* 91 */       os.writeLong(packetId.longValue());
/*    */     } 
/* 93 */     os.flush();
/* 94 */     msgList.add(m);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cluster\mcast\MessageResend.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */