/*    */ package com.mysql.jdbc;
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
/*    */ public class PacketTooBigException
/*    */   extends SQLException
/*    */ {
/*    */   public PacketTooBigException(long packetSize, long maximumPacketSize) {
/* 48 */     super(Messages.getString("PacketTooBigException.0") + packetSize + Messages.getString("PacketTooBigException.1") + maximumPacketSize + Messages.getString("PacketTooBigException.2") + Messages.getString("PacketTooBigException.3") + Messages.getString("PacketTooBigException.4"), "S1000");
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\PacketTooBigException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */