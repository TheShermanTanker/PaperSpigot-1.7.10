/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.util.com.google.common.collect.BiMap;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public abstract class Packet
/*    */ {
/* 12 */   private static final Logger a = LogManager.getLogger();
/* 13 */   public final long timestamp = System.currentTimeMillis();
/*    */ 
/*    */ 
/*    */   
/*    */   public static Packet a(BiMap bimap, int i) {
/*    */     try {
/* 19 */       Class<Packet> oclass = (Class)bimap.get(Integer.valueOf(i));
/*    */       
/* 21 */       return (oclass == null) ? null : oclass.newInstance();
/* 22 */     } catch (Exception exception) {
/* 23 */       a.error("Couldn't create packet " + i, exception);
/* 24 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void a(ByteBuf bytebuf, byte[] abyte) {
/* 30 */     if (bytebuf instanceof PacketDataSerializer) {
/*    */       
/* 32 */       PacketDataSerializer packetDataSerializer = (PacketDataSerializer)bytebuf;
/* 33 */       if (packetDataSerializer.version >= 20) {
/* 34 */         packetDataSerializer.b(abyte.length);
/*    */       } else {
/* 36 */         bytebuf.writeShort(abyte.length);
/*    */       } 
/*    */     } else {
/*    */       
/* 40 */       bytebuf.writeShort(abyte.length);
/*    */     } 
/*    */     
/* 43 */     bytebuf.writeBytes(abyte);
/*    */   }
/*    */ 
/*    */   
/*    */   public static byte[] a(ByteBuf bytebuf) throws IOException {
/* 48 */     short short1 = 0;
/* 49 */     if (bytebuf instanceof PacketDataSerializer) {
/*    */       
/* 51 */       PacketDataSerializer packetDataSerializer = (PacketDataSerializer)bytebuf;
/* 52 */       if (packetDataSerializer.version >= 20) {
/* 53 */         short1 = (short)packetDataSerializer.a();
/*    */       } else {
/* 55 */         short1 = bytebuf.readShort();
/*    */       } 
/*    */     } else {
/*    */       
/* 59 */       short1 = bytebuf.readShort();
/*    */     } 
/*    */ 
/*    */     
/* 63 */     if (short1 < 0) {
/* 64 */       throw new IOException("Key was smaller than nothing!  Weird key!");
/*    */     }
/* 66 */     byte[] abyte = new byte[short1];
/*    */     
/* 68 */     bytebuf.readBytes(abyte);
/* 69 */     return abyte;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract void a(PacketDataSerializer paramPacketDataSerializer) throws IOException;
/*    */   
/*    */   public abstract void b(PacketDataSerializer paramPacketDataSerializer) throws IOException;
/*    */   
/*    */   public abstract void handle(PacketListener paramPacketListener);
/*    */   
/*    */   public boolean a() {
/* 80 */     return false;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 84 */     return getClass().getSimpleName();
/*    */   }
/*    */   
/*    */   public String b() {
/* 88 */     return "";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\Packet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */