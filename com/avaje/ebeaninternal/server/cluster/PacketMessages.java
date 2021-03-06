/*    */ package com.avaje.ebeaninternal.server.cluster;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.cluster.mcast.Message;
/*    */ import com.avaje.ebeaninternal.server.cluster.mcast.MessageAck;
/*    */ import com.avaje.ebeaninternal.server.cluster.mcast.MessageControl;
/*    */ import com.avaje.ebeaninternal.server.cluster.mcast.MessageResend;
/*    */ import java.io.DataInput;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketMessages
/*    */   extends Packet
/*    */ {
/*    */   private final ArrayList<Message> messages;
/*    */   
/*    */   public static PacketMessages forWrite(long packetId, long timestamp, String serverName) throws IOException {
/* 42 */     return new PacketMessages(true, packetId, timestamp, serverName);
/*    */   }
/*    */   
/*    */   public static PacketMessages forRead(Packet header) throws IOException {
/* 46 */     return new PacketMessages(header);
/*    */   }
/*    */   
/*    */   private PacketMessages(boolean write, long packetId, long timestamp, String serverName) throws IOException {
/* 50 */     super(write, (short)1, packetId, timestamp, serverName);
/* 51 */     this.messages = null;
/*    */   }
/*    */   
/*    */   private PacketMessages(Packet header) throws IOException {
/* 55 */     super(false, (short)1, header.packetId, header.timestamp, header.serverName);
/* 56 */     this.messages = new ArrayList<Message>();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<Message> getMessages() {
/* 63 */     return this.messages;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readMessage(DataInput dataInput, int msgType) throws IOException {
/* 71 */     switch (msgType) {
/*    */       case 0:
/* 73 */         this.messages.add(MessageControl.readBinaryMessage(dataInput));
/*    */         return;
/*    */       
/*    */       case 8:
/* 77 */         this.messages.add(MessageAck.readBinaryMessage(dataInput));
/*    */         return;
/*    */       
/*    */       case 9:
/* 81 */         this.messages.add(MessageResend.readBinaryMessage(dataInput));
/*    */         return;
/*    */     } 
/*    */     
/* 85 */     throw new RuntimeException("Invalid Transaction msgType " + msgType);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cluster\PacketMessages.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */