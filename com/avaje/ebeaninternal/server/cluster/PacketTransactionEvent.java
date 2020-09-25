/*    */ package com.avaje.ebeaninternal.server.cluster;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*    */ import com.avaje.ebeaninternal.api.TransactionEventTable;
/*    */ import com.avaje.ebeaninternal.server.transaction.BeanDelta;
/*    */ import com.avaje.ebeaninternal.server.transaction.BeanPersistIds;
/*    */ import com.avaje.ebeaninternal.server.transaction.IndexEvent;
/*    */ import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
/*    */ import java.io.DataInput;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketTransactionEvent
/*    */   extends Packet
/*    */ {
/*    */   private final SpiEbeanServer server;
/*    */   private final RemoteTransactionEvent event;
/*    */   
/*    */   public static PacketTransactionEvent forWrite(long packetId, long timestamp, String serverName) throws IOException {
/* 47 */     return new PacketTransactionEvent(true, packetId, timestamp, serverName);
/*    */   }
/*    */   
/*    */   private PacketTransactionEvent(boolean write, long packetId, long timestamp, String serverName) throws IOException {
/* 51 */     super(write, (short)2, packetId, timestamp, serverName);
/* 52 */     this.server = null;
/* 53 */     this.event = null;
/*    */   }
/*    */   
/*    */   private PacketTransactionEvent(Packet header, SpiEbeanServer server) throws IOException {
/* 57 */     super(false, (short)2, header.packetId, header.timestamp, header.serverName);
/* 58 */     this.server = server;
/* 59 */     this.event = new RemoteTransactionEvent(server);
/*    */   }
/*    */   
/*    */   public static PacketTransactionEvent forRead(Packet header, SpiEbeanServer server) throws IOException {
/* 63 */     return new PacketTransactionEvent(header, server);
/*    */   }
/*    */   
/*    */   public RemoteTransactionEvent getEvent() {
/* 67 */     return this.event;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void readMessage(DataInput dataInput, int msgType) throws IOException {
/* 72 */     switch (msgType) {
/*    */       case 1:
/* 74 */         this.event.addBeanPersistIds(BeanPersistIds.readBinaryMessage(this.server, dataInput));
/*    */         return;
/*    */       
/*    */       case 2:
/* 78 */         this.event.addTableIUD(TransactionEventTable.TableIUD.readBinaryMessage(dataInput));
/*    */         return;
/*    */       
/*    */       case 3:
/* 82 */         this.event.addBeanDelta(BeanDelta.readBinaryMessage(this.server, dataInput));
/*    */         return;
/*    */       
/*    */       case 7:
/* 86 */         this.event.addIndexEvent(IndexEvent.readBinaryMessage(dataInput));
/*    */         return;
/*    */     } 
/*    */     
/* 90 */     throw new RuntimeException("Invalid Transaction msgType " + msgType);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cluster\PacketTransactionEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */